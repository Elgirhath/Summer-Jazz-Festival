package com.example.festivalapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    public MapFragment() {
        // Required empty public constructor
    }

    int[] images = {R.drawable.ic_music_note_black_24dp, R.drawable.ic_music_note_black_24dp, R.drawable.ic_music_note_black_24dp, R.drawable.ic_music_note_black_24dp, R.drawable.ic_music_note_black_24dp, R.drawable.ic_music_note_black_24dp, R.drawable.ic_music_note_black_24dp, R.drawable.ic_music_note_black_24dp, R.drawable.ic_music_note_black_24dp};
    String[] version = {"Dziedziniec Pałacu pod Baranami", "Piwnica pod Baranami", "Harris Piano Jazz Bar", "Piec Art Acoustic Jazz Club", "Klub U Muniaka", "Alchemia", "Muzeum Manggha", "Kijów Centrum", "ICE Krakow Congress Center"};

    String[] versionNumber = {"Rynek Główny 27", "Rynek Główny 27", "Rynek Główny 28", "Szewska 12", "Floriańska 3", "Estery 5", "Marii Konopnickiej 26", "aleja Zygmunta Krasińskiego 34", "Marii Konopnickiej 17"};

    String[] links = {"https://goo.gl/maps/zHdHYGU3aaNHqVY29", "https://g.page/Piwnica-Pod-Baranami-Klub?share", "https://goo.gl/maps/6DbYBhKfvQNykmem6", "https://goo.gl/maps/1BfVZUHKtgzyqKV5A", "https://goo.gl/maps/FNtokqSc5JnhdrHj6", "https://g.page/AlchemiaOdKuchni?share", "https://g.page/Manggha?share", "https://goo.gl/maps/GH5DwbPYRSUiBFGp9", "https://goo.gl/maps/btx7Rpfqoxb1Ydob9"};
    ListAdapter lAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        ListView list = v.findViewById(R.id.list);

        lAdapter = new ListAdapter(getActivity(), version, versionNumber, images);

        list.setAdapter(lAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Toast.makeText(getActivity(), versionNumber[i], Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse(links[i]);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        return v;
    }
}

