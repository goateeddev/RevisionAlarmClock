package com.example.goateeddev.revisionalarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TimePicker;
import android.widget.ToggleButton;
import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    AlarmManager alarmManager;
    TimePicker tp_alarm;
    PendingIntent pending_intent;
    Calendar calendar;
    Intent receiver_intent;
    ToggleButton toggle;
    Preferences prefs;
    int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        receiver_intent = new Intent(AlarmActivity.this, AlarmReceiver.class);

        calendar = Calendar.getInstance();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        tp_alarm = (TimePicker) findViewById(R.id.tp_alarm);
        toggle = (ToggleButton) findViewById(R.id.tb_alarm);

        prefs = new Preferences(this);
        boolean state = prefs.getValue("toggle", false);
        final String onoff = state ? "OFF" : "ON";
        toggle.setChecked(state);

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if(Build.VERSION.SDK_INT < 23){
                        hour = tp_alarm.getCurrentHour();
                        minute = tp_alarm.getCurrentMinute();
                    } else{
                        hour = tp_alarm.getHour();
                        minute = tp_alarm.getMinute();
                    }
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);

                    prefs.setValue("toggle", isChecked);
                    String _minute = minute < 10 ? "0" + minute : minute + "";
                    prefs.setValue("alarm1_time", hour + ":" + _minute);
                    prefs.setValue("alarm1_state", onoff);

                    pending_intent = PendingIntent.getBroadcast(AlarmActivity.this, 0, receiver_intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);
                } else {
                    prefs.setValue("toggle", isChecked);
                    prefs.setValue("alarm1_time", "00:00");
                    prefs.setValue("alarm1_state", onoff);

                    alarmManager.cancel(pending_intent);
                    sendBroadcast(receiver_intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        finish();
    }
}
