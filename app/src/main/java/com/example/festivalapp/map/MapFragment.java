package com.example.festivalapp.map;

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

import com.example.festivalapp.R;
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

import java.util.List;
import java.util.stream.Collectors;

public class MapFragment extends Fragment {

    private MapView mapView;
    private Spinner spinner;
    private MapboxMap map;
    private Button btn;
    LocationRepository repository;

    public MapFragment(){
        repository = new LocationRepository();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Mapbox.getInstance(requireActivity(), "pk.eyJ1IjoiYWthd2FsZWMiLCJhIjoiY2tibnNheDczMTI4bTJ4cDkwdmlyNjhhNCJ9.ZEzMbrPFlzi4Nnl7vX8_mw");
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        spinner = view.findViewById(R.id.spinner);
        btn = view.findViewById(R.id.buttonik);

        mapView = view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {

                map = mapboxMap;

                mapboxMap.setStyle(Style.DARK, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        String[] locationNames = repository.getLocationNames().toArray(new String[0]);
                        ArrayAdapter<CharSequence> mSortAdapter = new ArrayAdapter<>(getActivity(), R.layout.customspinner, locationNames);
                        mSortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(mSortAdapter);

                        initMarkers(style);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view,
                                                       final int position, long id) {
                                Object item = adapterView.getItemAtPosition(position);
                                if (item != null) {
                                    btn.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v)
                                        {
                                            Uri uri = Uri.parse(repository.getLocationList().get(position).url);

                                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                            startActivity(intent);
                                        }
                                    });
                                }



                                update_marker(repository.getLocationList().get(position).coordinates);

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
                        PropertyFactory.iconOffset(new Float[]{0f, -100f}),
                        PropertyFactory.iconSize(0.2f),
                        PropertyFactory.iconIgnorePlacement(true),
                        PropertyFactory.iconAllowOverlap(true)
                ));
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
