package ag.example1.festivalapp;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import ag.example1.festivalapp.database.DBmanager;
import ag.example1.festivalapp.database.entity.ConcertEntity;
import ag.example1.festivalapp.schedule.NotificationService;
import ag.example1.festivalapp.schedule.ScheduleReader;

import java.io.IOException;

public class OnInstallEventManager {
    private boolean isFirstRun;
    private SharedPreferences preferences;
    private DBmanager dbManager;

    public OnInstallEventManager() {
        preferences = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        isFirstRun = preferences.getBoolean("PREFERENCE_FIRST_RUN", true);
        preferences.edit().putBoolean("PREFERENCE_FIRST_RUN", false).apply();
        Log.d("OnInstallEventManager", "OnInstallEventManager " + isFirstRun);

        dbManager = new DBmanager(App.getContext());
    }

    public void install() {
        if (isFirstRun()) {
            installDatabase(dbManager);
        }
        if (!isNotificationServiceRunning()) {
            installNotificationService();
        }
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

    public boolean isNotificationServiceRunning() {
        ActivityManager manager = (ActivityManager) App.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (NotificationService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
