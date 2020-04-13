package com.example.lee.alarm_application;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;

/**
 * alarm_application
 * Class: AudioService.
 * Created by leedonghun.
 * Created On 2018-09-05.
 * Description:
 */
public class AudioService extends Service {

    private ArrayList<Long>mAudioIds=new ArrayList<>();
    private final IBinder servicebinder = (IBinder) new AudioServiceBinder();
    private MediaPlayer mediaPlayer;
    private  boolean isprepared;
    private int mCurrentPosition;
    private MusicAdapter.AudioItem mAudioItem;





    // 서비스 바인더란?
    //startService가 아닌   bindsevice를 통해 시작되는 서비스를 서비스 바인딩이라고함.
    //onBind()를 통해 바인딩 객체를 생성.
    //서비스와 클라이언터 사이의 인터페이스 역활.
    //연결된 엑티비티가 사라지면 서비스도 소멸됨 다른 경우에는 께속 사라있음
    public class AudioServiceBinder extends Binder {
    AudioService getService() {


        return AudioService.this;
    }

}
   public void onCreate(){
        super.onCreate();
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                isprepared=true;
                mediaPlayer.start();
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                isprepared=false;
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {

                isprepared=false;
                return false;
            }
        });

         mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
             @Override
             public void onSeekComplete(MediaPlayer mediaPlayer) {

             }
         });

   }


    @Override
    public IBinder onBind(Intent intent) {
        return servicebinder;
    }
    public void onDestroy(){
        super.onDestroy();
        if(mediaPlayer !=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }

    }



    //AUDIO ITEM을 기준으로 사용자로부터 재생  목록의 POSITION을 불러와 재생할 음악의 대한 정보를 불어와
    //audioitem에 저장함.

    private  void queryAudioItem(int position){
        mCurrentPosition=position;
        long audioId=mAudioIds.get(position);
        Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection= new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.DATA

        };

        String selection=MediaStore.Audio.Media._ID+"=?";
        String[] selectionArgs= { String.valueOf(audioId)};
        Cursor cursor=getContentResolver().query(uri,projection,selection,selectionArgs,null);
     if(cursor != null){
         if(cursor.getCount()>0){
             cursor.moveToFirst();
             mAudioItem= MusicAdapter.AudioItem.bindCursor(cursor);
         }
         cursor.close();
     }

    }

    public MusicAdapter.AudioItem getmAudioItem() {



        return mAudioItem;
    }

    private  void  prepare(){
        try{
            mediaPlayer.setDataSource(mAudioItem.mDataPath);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

   }

   public void stop(){

        mediaPlayer.stop();
        mediaPlayer.reset();


    }

    public  void setPlayList(ArrayList<Long> audiolds){
        if (mAudioIds.size() != audiolds.size()) {
        if(!mAudioIds.equals(audiolds)) {
            mAudioIds.clear();
            mAudioIds.addAll(audiolds);

        }
        }
    }
    public void play(int position){
        queryAudioItem(position);
        stop();
        prepare();


    }
    public void play(){

        if (isprepared) {
            mediaPlayer.start();
        }
    }


    public void pause() {
        if (isprepared) {
           mediaPlayer.pause();
        }
    }

    public void forward() {
        if (mAudioIds.size() - 1 > mCurrentPosition) {
            mCurrentPosition++; // 다음 포지션으로 이동.
        } else {
            mCurrentPosition = 0; // 처음 포지션으로 이동.
        }
        play(mCurrentPosition);
    }

    public void rewind() {
        if (mCurrentPosition > 0) {
            mCurrentPosition--; // 이전 포지션으로 이동.
        } else {
            mCurrentPosition = mAudioIds.size() - 1; // 마지막 포지션으로 이동.
        }
        play(mCurrentPosition);
    }

}