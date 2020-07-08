package ag.example1.festivalapp.schedule;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import ag.example1.festivalapp.database.DBmanager;
import ag.example1.festivalapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProgramFragment extends Fragment {
    View v;
    private RecyclerView recyclerView;
    private DBmanager dBmanager;
    private ScheduleAdapter adapter;

    public ProgramFragment(DBmanager dBmanager) {
        this.dBmanager = dBmanager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_program, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button searchButton = getView().findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchInput = getView().findViewById(R.id.searchInput);
                adapter.applyQuery(searchInput.getText().toString());
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(30);
                recyclerView.scrollToPosition(0);
            }
        });

        final EditText searchInput = getView().findViewById(R.id.searchInput);
        final RelativeLayout clearInput = getView().findViewById(R.id.clearInput);
        clearInput.setVisibility(View.INVISIBLE);

        clearInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchInput.setText("");
                adapter.applyQuery("");
                recyclerView.scrollToPosition(0);
            }
        });

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) {
                    clearInput.setVisibility(View.INVISIBLE);
                }
                else {
                    clearInput.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        searchInput.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                searchButton.callOnClick();
                return true;
            }
            return false;
        });

        final RelativeLayout searchBar = getView().findViewById(R.id.searchBar);
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchInput.setFocusableInTouchMode(true);
                searchInput.requestFocus();
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                inputMethodManager.toggleSoftInputFromWindow(
                        getView().getApplicationWindowToken(),
                        InputMethodManager.SHOW_FORCED, 0);

            }
        });

        recyclerView = getView().findViewById(R.id.recycler);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ScheduleAdapter(getContext(), dBmanager);
        recyclerView.setAdapter(adapter);
    }
}
