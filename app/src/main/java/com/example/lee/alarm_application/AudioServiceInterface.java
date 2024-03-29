package com.example.lee.alarm_application;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import java.util.ArrayList;

/**
 * alarm_application
 * Class: AudioServiceInterface.
 * Created by leedonghun.
 * Created On 2018-09-05.
 * Description:
 */
public class AudioServiceInterface {
    private ServiceConnection mServiceConnection;
    private AudioService mService;

    public  AudioServiceInterface(Context context){
        mServiceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder service) {
                mService=((AudioService.AudioServiceBinder)service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            mServiceConnection=null;
            mService=null;
            }
        };
        context.bindService(new Intent(context,AudioService.class)
       .setPackage(context.getPackageName()),mServiceConnection,Context.BIND_AUTO_CREATE);
    }
  public  void setPlayList(ArrayList<Long>audioIds){
        if(mService !=null){
            mService.setPlayList(audioIds);

        }

  }
public  void stop(){
        if(mService !=null){
            mService.stop();
        }
}
  public void play(int position){
        if(mService != null){
            mService.play(position);
        }
  }


    public void play() {
        if (mService != null) {
            mService.play();
        }
    }


    public void pause() {
        if (mService != null) {
            mService.play();
        }
    }

    public void forward() {
        if (mService != null) {
            mService.forward();
        }
    }

    public void rewind() {
        if (mService != null) {
            mService.rewind();
        }
    }

    public MusicAdapter.AudioItem getAudioItem() {
        if (mService != null) {
            return mService.getmAudioItem();
        }
        return null;
    }

}
