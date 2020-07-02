package com.example.festivalapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.festivalapp.database.DBmanager;
import com.example.festivalapp.database.entity.ConcertEntity;
import com.example.festivalapp.schedule.NotificationService;
import com.example.festivalapp.schedule.ScheduleReader;

import java.io.IOException;

public class OnInstallEventManager {
    private boolean isFirstRun;
    private SharedPreferences preferences;

    public OnInstallEventManager() {
        preferences = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        isFirstRun = preferences.getBoolean("PREFERENCE_FIRST_RUN", true);
        preferences.edit().putBoolean("PREFERENCE_FIRST_RUN", false).apply();
    }

    public void installDatabase(DBmanager dbManager) {
        dbManager.initialize();
        try {
            ConcertEntity[] concertEntities = new ScheduleReader().read(App.getContext().getAssets().open("concerts1.csv"));
            for (ConcertEntity concertEntity : concertEntities) {
                dbManager.insert(concertEntity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void installNotificationService() {
        App.getContext().startService(new Intent(App.getContext(), NotificationService.class));
    }

    public boolean isFirstRun() {
        return isFirstRun;
    }
}
