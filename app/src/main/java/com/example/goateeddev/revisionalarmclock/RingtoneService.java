package com.example.goateeddev.revisionalarmclock;

import android.support.annotation.Nullable;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class RingtoneService extends Service {

    MediaPlayer song;
    NotificationManager notify_manager;
    Notification notification_popup;
    boolean playing = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent alarm_activity = new Intent(this.getApplicationContext(), AlarmActivity.class);
        PendingIntent pending_intent_alarm_activity = PendingIntent.getActivity(this, 0, alarm_activity, 0);

        notify_manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification_popup = new Notification.Builder(this)
                .setContentTitle("Revision Alarm Clock")
                .setContentText("Time to revise!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pending_intent_alarm_activity)
                .setAutoCancel(true)
                .build();

        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean state = preferences.getBoolean("toggle", true);

        if (!playing && state) {
            Log.e("no music", "switch turned on");
            notify_manager.notify(0, notification_popup);
            song = MediaPlayer.create(this, R.raw.fright);
            song.start();
            playing = true;
        }
        else if (playing && !state) {
            Log.e("is music", "switch turned off");
            song.stop();
            song.reset();
            playing = false;
        }
        else if (playing && state) {
            Log.e("is music ", "switch turned on");
            playing = true;
        }
        else if (!playing && !state) {
            Log.e("no music ", "switch turned off");
            playing = false;
        }
        else {
            Log.e("Unexpected Result", "Unexpected occurrence");
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        playing = false;
    }
}
