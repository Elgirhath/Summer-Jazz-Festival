package com.example.festivalapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.festivalapp.database.DBmanager;
import com.example.festivalapp.database.entity.ConcertEntity;
import com.example.festivalapp.schedule.ScheduleReader;

import java.io.IOException;

public class OnInstallEventManager {
    private boolean isFirstRun;
    private Context context;
    private SharedPreferences preferences;

    public OnInstallEventManager(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        isFirstRun = preferences.getBoolean("PREFERENCE_FIRST_RUN", true);
        preferences.edit().putBoolean("PREFERENCE_FIRST_RUN", false).apply();
    }

    public void installDatabase(DBmanager dbManager) {
        dbManager.initialize();
        try {
            ConcertEntity[] concertEntities = new ScheduleReader().read(context.getAssets().open("concerts.csv"));
            for (ConcertEntity concertEntity : concertEntities) {
                dbManager.insert(concertEntity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isFirstRun() {
        return isFirstRun;
    }
}
