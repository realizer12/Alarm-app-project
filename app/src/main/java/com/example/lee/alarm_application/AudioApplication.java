package com.example.lee.alarm_application;

import android.app.Application;

/**
 * alarm_application
 * Class: AudioApplication.
 * Created by leedonghun.
 * Created On 2018-09-05.
 * Description:
 */
public class AudioApplication  extends Application{
 private static AudioApplication mInstance;
 private AudioServiceInterface mInterface;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        mInterface=new AudioServiceInterface(getApplicationContext());

    }
    public static AudioApplication getmInstance(){

        return mInstance;
    }

    public  AudioServiceInterface getServiceInterface(){


        return mInterface;
    }


}

