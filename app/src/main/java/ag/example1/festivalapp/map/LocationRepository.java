package ag.example1.festivalapp.map;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

public class LocationRepository {
    private List<Location> locations = new ArrayList<Location>();

    public LocationRepository() {
        locations.add(
            new Location(
                "Piwnica pod Baranami",
                "https://www.google.com/maps/place/Piwnica+Pod+Baranami/@50.0615985,19.9354713,15z/data=!4m2!3m1!1s0x0:0x4e9ee563e7699ca8?sa=X&ved=2ahUKEwj_zJ2p0pHqAhW-BhAIHTjaD-AQ_BIwHHoECBYQCA",
                new LatLng(50.0615985, 19.9332826)
            )
        );

        locations.add(
            new Location(
                "Harris Piano Jazz Bar",
                "https://goo.gl/maps/6DbYBhKfvQNykmem6",
                new LatLng(50.0617, 19.9333793)
            )
        );
        locations.add(
            new Location(
                "Dziedziniec Pałacu pod Baranami",
                "https://goo.gl/maps/zHdHYGU3aaNHqVY29",
                new LatLng(50.061587, 19.9330223)
            )
        );
        locations.add(
            new Location(
                "Piec Art Acoustic Jazz Club",
                "https://goo.gl/maps/1BfVZUHKtgzyqKV5A",
                new LatLng(50.0616452, 19.9360246)
            )
        );
        locations.add(
            new Location(
                "Klub U Muniaka",
                "https://goo.gl/maps/FNtokqSc5JnhdrHj6",
                new LatLng(50.0619822, 19.9382254)
            )
        );
        locations.add(
            new Location(
                "Alchemia",
                "https://www.google.com/maps/place/Alchemia/@50.0523381,19.9427216,17z/data=!3m1!4b1!4m5!3m4!1s0x47165b6c64662dab:0x5daf119c1937989d!8m2!3d50.0523381!4d19.9449156",
                new LatLng(50.052196, 19.9427967)
            )
        );
        locations.add(
            new Location(
                "Muzeum Manggha",
                "https://www.google.com/maps/place/Manggha+Centre/@50.0507577,19.9314921,15z/data=!4m2!3m1!1s0x0:0x9076d27e065dbd87?sa=X&ved=2ahUKEwj4urbH0pHqAhXKAhAIHcn6Al4Q_BIwDXoECBIQCA",
                new LatLng(50.0507577, 19.9293034)
            )
        );
        locations.add(
            new Location(
                "Kijów Centrum",
                "https://goo.gl/maps/GH5DwbPYRSUiBFGp9",
                new LatLng(50.0582622, 19.9227563)
            )
        );
        locations.add(
                new Location(
                "ICE Krakow Congress Centre",
                "https://goo.gl/maps/btx7Rpfqoxb1Ydob9",
                new LatLng(50.0477778, 19.9292002)
            )
        );
    }

    public List<Location> getLocationList() {
        return locations;
    }

    public List<String> getLocationNames() {
        List<String> names = new ArrayList<>();
        for (Location location : locations) {
            names.add(location.name);
        }
        return names;
    }
}
