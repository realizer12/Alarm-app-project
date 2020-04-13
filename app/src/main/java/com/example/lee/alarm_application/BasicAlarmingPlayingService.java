package com.example.lee.alarm_application;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;

import static com.example.lee.alarm_application.basic_alarm_screen.intentArrayList;

/**
 * alarm_application
 * Class: BasicAlarmingPlayingService.
 * Created by leedonghun.
 * Created On 2018-09-15.
 * Description:
 */
public class BasicAlarmingPlayingService extends Service {

    Button finish;
    private View mView;
    private WindowManager.LayoutParams mParams;
    private WindowManager mWindowManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = mInflater.inflate(R.layout.alarming, null);
        mView.setOnTouchListener(onTouchListener);
        mParams=new WindowManager.LayoutParams(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED,
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        mWindowManager=(WindowManager)getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mView,mParams);

        finish=new Button(this);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),//현재제어권자
                        SettingBasicAlarm.class);



                cancelAlarm();
                stopService(intent);
            }
        });
    }


    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;

                case MotionEvent.ACTION_MOVE:
                    break;

                case MotionEvent.ACTION_UP:
                    break;
            }

            return false;
        }
    };
    public void cancelAlarm(){
        AlarmManager alarmManager=(AlarmManager)getApplicationContext().getSystemService(ALARM_SERVICE);
        intentArrayList=new ArrayList<PendingIntent>();

        for(int i=0; i<intentArrayList.size(); i++){

            PendingIntent pendingIntent=intentArrayList.get(i);
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();


        }

    }

}