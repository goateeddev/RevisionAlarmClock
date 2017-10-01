package com.example.goateeddev.revisionalarmclock;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    enum Alarms{
        Alarm1, Alarm2
    }
    Alarms alarm;
    int bkcount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Preferences prefs = new Preferences(this);

        String a1_time = prefs.getValue("alarm1_time", "00:00");
        String a2_time = prefs.getValue("alarm2_time", "00:00");
        String a1_state = prefs.getValue("alarm1_state", "OFF");
        String a2_state = prefs.getValue("alarm2_state", "OFF");

        TextView tv_alarm1 = (TextView) findViewById(R.id.tv_alarm1);
        tv_alarm1.setText(a1_time);
        TextView tv_alarm2 = (TextView) findViewById(R.id.tv_alarm2);
        tv_alarm2.setText(a2_time);

        TextView alarm1_state = (TextView) findViewById(R.id.alarm1_state);
        alarm1_state.setText(a1_state);
        TextView alarm2_state = (TextView) findViewById(R.id.alarm2_state);
        alarm2_state.setText(a2_state);

        LinearLayout alarm1 = (LinearLayout) findViewById(R.id.alarm1);
        alarm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarm = Alarms.Alarm1;
                startActivity(new Intent(HomeActivity.this, AlarmActivity.class));
            }
        });

        LinearLayout alarm2 = (LinearLayout) findViewById(R.id.alarm2);
        alarm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarm = Alarms.Alarm2;
                startActivity(new Intent(HomeActivity.this, AlarmActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed(){
        if (bkcount > 0) {
            finish();
        } else {
            bkcount++;
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            new CountDownTimer(2000, 1000){
                public void onTick(long millisUntilFinished){
                }
                public void onFinish(){
                    bkcount = 0;
                }
            }.start();
        }
    }
}
