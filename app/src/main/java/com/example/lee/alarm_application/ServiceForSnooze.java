package com.example.lee.alarm_application;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.Calendar;

import static com.example.lee.alarm_application.AlarmReceiver.vibration;
import static com.example.lee.alarm_application.AlarmReceiver.vibrator;

/**
 * alarm_application
 * Class: ServiceForSnooze.
 * Created by leedonghun.
 * Created On 2018-09-21.
 * Description:
 */
public class ServiceForSnooze extends Service {




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {








        return null;
    }






    }






