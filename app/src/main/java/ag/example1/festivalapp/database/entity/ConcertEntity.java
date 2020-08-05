package ag.example1.festivalapp.database.entity;

import ag.example1.festivalapp.database.orm.Id;

import java.io.Serializable;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ConcertEntity implements Serializable {
    @Id
    public int _ID;
    public String ARTIST;
    public String DATE;
    public String HOUR;
    public String PLACE;
    public String LINK;

    public ConcertEntity(String artist, String date, String hour, String place, String link) {
        this.ARTIST = artist;
        this.DATE = date;
        this.HOUR = hour;
        this.PLACE = place;
        this.LINK = link;
    }
}