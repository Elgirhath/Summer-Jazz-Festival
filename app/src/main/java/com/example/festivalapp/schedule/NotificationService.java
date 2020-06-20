package com.example.festivalapp.schedule;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.festivalapp.App;
import com.example.festivalapp.R;
import com.example.festivalapp.database.DBmanager;
import com.example.festivalapp.database.entity.ConcertEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {
    List<Timer> timers = new ArrayList<>();
    private DBmanager dbManager;

    String notificationChannelId = "schedule_notif_channel_1";
    NotificationManager notificationManager;

    private List<ConcertEntity> concertEntities;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        dbManager = new DBmanager(App.getContext());

        fetchConcertData();
        createNotificationChannel();
        setupNotificationTimers();

        return START_STICKY;
    }

    private void fetchConcertData() {
        concertEntities = Arrays.asList(dbManager.fetch());
    }

    @Override
    public void onDestroy() {
        stoptimertask();
        super.onDestroy();
    }

    final Handler handler = new Handler();

    private Date getNotificationTime(ConcertEntity concertEntity) {
        try {
            SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateParser.parse(concertEntity.DATE);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, -1);

            cal.set(Calendar.HOUR_OF_DAY, 19);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);

            Date notificationDate = cal.getTime();

            return notificationDate;
        }
        catch (ParseException e) {
            Log.e("Notification", e.getMessage());
            return null;
        }
    }

    public void setupNotificationTimers() {
        for (ConcertEntity concert : concertEntities) {
            Timer timer = new Timer();

            Date currentTime = Calendar.getInstance().getTime();
            Date notificationTime = getNotificationTime(concert);

            long delay = notificationTime.getTime() - currentTime.getTime();

            TimerTask timerTask = new TimerTask() {
                public void run() {
                    handler.post(() -> postNotification(concert));
                }
            };

            timer.schedule(timerTask, delay);
            timers.add(timer);
        }
    }

    public void stoptimertask() {
        for (int i = 0; i < timers.size(); i++) {
            Timer timer = timers.get(i);
            if (timer != null) {
                timer.cancel();
                timers.set(i, null);
            }
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(notificationChannelId, notificationChannelId, importance);
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void postNotification(ConcertEntity concert) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, notificationChannelId)
                .setSmallIcon(R.drawable.ic_note)
                .setContentTitle(concert.ARTIST)
                .setContentText("Jutro o " + concert.HOUR + " - " + concert.PLACE)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(concertEntities.indexOf(concert), builder.build());
    }
}
