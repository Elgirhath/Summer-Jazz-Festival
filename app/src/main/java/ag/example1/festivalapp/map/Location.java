package ag.example1.festivalapp.map;

import com.mapbox.mapboxsdk.geometry.LatLng;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    String name;
    String url;
    LatLng coordinates;
}