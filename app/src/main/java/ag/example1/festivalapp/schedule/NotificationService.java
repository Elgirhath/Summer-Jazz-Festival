package ag.example1.festivalapp.schedule;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import ag.example1.festivalapp.App;
import ag.example1.festivalapp.MainActivity;
import ag.example1.festivalapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {
    List<Timer> timers = new ArrayList<>();

    String notificationChannelId = "schedule_notif_channel_1";
    NotificationManager notificationManager;

    private String[] notificationDateFrame = new String[]{"2020-07-05", "2020-08-10"}; // [inclusive, exclusive)
    private String additionalNotificationDate = "2020-08-15";

    private String notificationTime = "13:00";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Log.d("NotificationService", "onStartCommand");

        createNotificationChannel();
        setupNotificationTimers();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("NotificationService", "Notification service destroyed");
        stoptimertask();
        super.onDestroy();
    }

    final Handler handler = new Handler();

    private List<Date> getNotificationTimes() throws ParseException {
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = dateParser.parse(notificationDateFrame[0]);
        Date endDate = dateParser.parse(notificationDateFrame[1]);

        List<Date> dates = new ArrayList<>();

        Date currentDate = (Date) startDate.clone();

        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);

        while (!currentDate.after(endDate)) {
            dates.add(getNotificationTime(currentDate));

            cal.add(Calendar.DATE, 7);
            currentDate = cal.getTime();
        }

        return dates;
    }

    private Date getNotificationTime(Date date) {
        String[] timeSplit = notificationTime.split(":");
        int hour = Integer.parseInt(timeSplit[0]);
        int minute = Integer.parseInt(timeSplit[1]);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);

        return cal.getTime();
    }

    private Date getAdditionalNotificationTime() throws ParseException {
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");
        Date additionalDate = dateParser.parse(additionalNotificationDate);
        return getNotificationTime(additionalDate);
    }

    public void setupNotificationTimers() {
        try {
            List<Date> notificationDates = getNotificationTimes();
            for (Date date : notificationDates) {
                scheduleNotification(
                        date,
                        notificationDates.indexOf(date) + 1,
                        getString(R.string.notification_title),
                        getString(R.string.notification_desc)
                );
            }

            scheduleNotification(
                    getAdditionalNotificationTime(),
                    0,
                    getString(R.string.additional_notification_title),
                    getString(R.string.additional_notification_desc)
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void scheduleNotification(Date date, int channel, String title, String content) {
        Timer timer = new Timer();

        Date currentTime = Calendar.getInstance().getTime();

        long delay = date.getTime() - currentTime.getTime();

        TimerTask timerTask = new TimerTask() {
            public void run() {
                handler.post(() -> postNotification(channel, title, content));
            }
        };

        try {
            timer.schedule(timerTask, delay);
            timers.add(timer);
        }
        catch (Exception ex) {
            ex.printStackTrace();
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

    private void postNotification(int channelIndex, String title, String content) {
        Log.d("NotificationService", "Posting notification...");
        Intent notificationIntent = new Intent(App.getContext(), MainActivity.class);

        PendingIntent intent = PendingIntent.getActivity(App.getContext(), 0,
                notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, notificationChannelId)
                .setSmallIcon(R.drawable.ic_note)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(intent);

        notificationManager.notify(channelIndex, builder.build());
    }
}
