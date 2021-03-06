package ag.example1.festivalapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ag.example1.festivalapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment {

    public MusicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_music, container, false);
        ImageView imageView = v.findViewById(R.id.spotify);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Uri uri = Uri.parse("https://open.spotify.com/playlist/0KDwAB1j12Jzr2qEXdfDq0?si=60TW8TaOQJmQUdNaogmtzA");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        return v;
    };

}
