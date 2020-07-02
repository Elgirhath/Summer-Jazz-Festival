package com.example.festivalapp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mapbox.geojson.Feature;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;

import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MapFragment extends Fragment {

    private MapView mapView;
    private Spinner spinner;
    private String[] test = { "Piwnica pod Baranami", "Harris Piano Jazz Bar", "Dziedziniec Pałacu pod Baranami", "Piec Art Acoustic Jazz Club", "Klub U Muniaka", "Alchemia", "Muzeum Manggha", "Kijów Centrum", "ICE Krakow Congress Centre" };
    private String chosen = "Piwnica pod Baranami";
    private MapboxMap map;
    private Button btn;

    public MapFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Mapbox.getInstance(requireActivity(), "pk.eyJ1IjoiYWthd2FsZWMiLCJhIjoiY2tibnNheDczMTI4bTJ4cDkwdmlyNjhhNCJ9.ZEzMbrPFlzi4Nnl7vX8_mw");
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        spinner = (Spinner) view.findViewById(R.id.spinner);
        btn = (Button) view.findViewById(R.id.buttonik);

        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {

                map = mapboxMap;

                mapboxMap.setStyle(Style.DARK, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        ArrayAdapter<CharSequence> mSortAdapter = new ArrayAdapter<CharSequence>(getActivity(), R.layout.customspinner, test);
                        mSortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(mSortAdapter);
                        spinner.getBackground().setColorFilter(Color.parseColor("#494949"), PorterDuff.Mode.SRC_ATOP);

                        initMarkers(style);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view,
                                                       final int position, long id) {
                                Object item = adapterView.getItemAtPosition(position);
                                if (item != null) {
                                    /*Toast.makeText(getContext(), item.toString(),
                                            Toast.LENGTH_SHORT).show();*/

                                    btn.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v)
                                        {
                                            Uri uri = Uri.parse("");
                                            switch (position){
                                                case 0:
                                                    uri = Uri.parse("https://www.google.com/maps/place/Piwnica+Pod+Baranami/@50.0615985,19.9354713,15z/data=!4m2!3m1!1s0x0:0x4e9ee563e7699ca8?sa=X&ved=2ahUKEwj_zJ2p0pHqAhW-BhAIHTjaD-AQ_BIwHHoECBYQCA");
                                                    break;
                                                case 1:
                                                    uri = Uri.parse("https://goo.gl/maps/6DbYBhKfvQNykmem6");
                                                    break;
                                                case 2:
                                                    uri = Uri.parse("https://goo.gl/maps/zHdHYGU3aaNHqVY29");
                                                    break;
                                                case 3:
                                                    uri = Uri.parse("https://goo.gl/maps/1BfVZUHKtgzyqKV5A");
                                                    break;
                                                case 4:
                                                    uri = Uri.parse("https://goo.gl/maps/FNtokqSc5JnhdrHj6");
                                                    break;
                                                case 5:
                                                    uri = Uri.parse("https://www.google.com/maps/place/Piwnica+Pod+Baranami/@50.0615985,19.9354713,15z/data=!4m2!3m1!1s0x0:0x4e9ee563e7699ca8?sa=X&ved=2ahUKEwj_zJ2p0pHqAhW-BhAIHTjaD-AQ_BIwHHoECBYQCA");
                                                    break;
                                                case 6:
                                                    uri = Uri.parse("https://www.google.com/maps/place/Manggha+Centre/@50.0507577,19.9314921,15z/data=!4m2!3m1!1s0x0:0x9076d27e065dbd87?sa=X&ved=2ahUKEwj4urbH0pHqAhXKAhAIHcn6Al4Q_BIwDXoECBIQCA");
                                                    break;
                                                case 7:
                                                    uri = Uri.parse("https://goo.gl/maps/GH5DwbPYRSUiBFGp9");
                                                    break;
                                                case 8:
                                                    uri = Uri.parse("https://goo.gl/maps/btx7Rpfqoxb1Ydob9");
                                                    break;
                                                default:
                                                    uri = Uri.parse("https://www.google.com/maps/place/Piwnica+Pod+Baranami/@50.0615985,19.9354713,15z/data=!4m5!3m4!1s0x0:0x4e9ee563e7699ca8!8m2!3d50.0615985!4d19.9354713");
                                            }

                                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                            startActivity(intent);
                                        }
                                    });
                                }


                                listen_to_scroll(position, view);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                // TODO Auto-generated method stub

                            }
                        });
                    }
                });
            }
        });
        return view;
    }

    private void initMarkers(@NonNull Style style) {
        style.addImage(("marker_icon"), BitmapFactory.decodeResource(
                getResources(), R.drawable.custom_marker));

        style.addSource(new GeoJsonSource("source-id"));

        style.addLayer(new SymbolLayer("layer-id", "source-id")
                .withProperties(
                        PropertyFactory.iconImage("marker_icon"),
                        PropertyFactory.iconIgnorePlacement(true),
                        PropertyFactory.iconAllowOverlap(true)
                ));
    }

    private void listen_to_scroll(int pos, View view){
        LatLng position_piwnica = new LatLng(50.0615985, 19.9332826);
        LatLng position_harris = new LatLng(50.0617, 19.9333793);
        LatLng dziedziniec = new LatLng(50.061587, 19.9330223);
        LatLng piec = new LatLng(50.0616452, 19.9360246);
        LatLng muniak = new LatLng(50.0619822, 19.9382254);
        LatLng alchemia = new LatLng(50.052196, 19.9427967);
        LatLng manga = new LatLng(50.0507577, 19.9293034);
        LatLng ice = new LatLng(50.0477778, 19.9292002);
        LatLng kijow = new LatLng(50.0582622, 19.9227563);

        switch(pos) {
            case 0:
                update_marker(position_piwnica);
                break;
            case 1:
                update_marker(position_harris);
                break;
            case 2:
                update_marker(dziedziniec);
                break;
            case 3:
                update_marker(piec);
                break;
            case 4:
                update_marker(muniak);
                break;
            case 5:
                update_marker(alchemia);
                break;
            case 6:
                update_marker(manga);
                break;
            case 7:
                update_marker(kijow);
                break;
            case 8:
                update_marker(ice);
                break;
            default:
                update_marker(position_piwnica);
        }


    }

    private void update_marker(LatLng position) {
        if (map.getStyle() != null) {
            GeoJsonSource current_loc = map.getStyle().getSourceAs("source-id");
            if (current_loc != null) {
                Point actual = Point.fromLngLat(position.getLongitude(), position.getLatitude());
                current_loc.setGeoJson(FeatureCollection.fromFeature(
                        Feature.fromGeometry(actual)
                ));
            }
        }

        map.animateCamera(CameraUpdateFactory.newLatLng(position));
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}
