package com.example.festivalapp.schedule;

import com.example.festivalapp.database.entity.ConcertEntity;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ScheduleReader {
    public ConcertEntity[] read(InputStream file) {
        try {
            List<ConcertEntity> concertEntities = new ArrayList<>();
            CSVReader reader = new CSVReader(new InputStreamReader(file));
            String[] line = reader.readNext();
            while (line != null) {
                concertEntities.add(new ConcertEntity(line[1], line[0], line[3], line[2], line[4]));
                line = reader.readNext();
            }
            return concertEntities.toArray(new ConcertEntity[0]);
        }
        catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
