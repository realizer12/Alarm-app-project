package com.example.lee.alarm_application;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.WindowManager;

import com.google.android.gms.maps.model.LatLng;

import static android.content.Context.AUDIO_SERVICE;
import static android.content.Context.POWER_SERVICE;
import static com.example.lee.alarm_application.basic_alarm_screen.intentArrayList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * alarm_application
 * Class: AlarmReceiver.
 * Created by leedonghun.
 * Created On 2018-09-09.
 * Description:
 */
public class AlarmReceiver extends BroadcastReceiver {
    AudioService mService;
    static public  Vibrator vibrator;
    Context context;
    String datasource;
    static public MediaPlayer  mediaPlayer;
    static public MediaPlayer mediaPlayer1;
    static public  MediaPlayer mediaPlayer2;
    public String alarm_sound_kind;
    public static boolean vibration;
    public int levelmath=0;
    public String wayforkillalamr;
    public  String mymusic_path;
    int week;



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;


    //알람의 해제 방법이다.
    wayforkillalamr=intent.getStringExtra("알람해제종류");
        //특정날짜를 선택했는지 여부를 판별하여, 선택하였을경우에는 바로 알람 진행을 하도록 하고,
        //특정날짜가 아닌경우에는 요일을 판별하여 진행하도록 한다.
    boolean usingspecificdate=intent.getBooleanExtra("요일여부",false);
    //요일에대한  선택여부 이다.
    boolean sunday=intent.getBooleanExtra("일요일여부",false);
    boolean monday=intent.getBooleanExtra("월요일여부",false);
    boolean tuseday=intent.getBooleanExtra("화요일여부",false);
    boolean wednesday=intent.getBooleanExtra("수요일여부",false);
    boolean thursday=intent.getBooleanExtra("목요일여부", false);
    boolean friday=intent.getBooleanExtra("금요일여부",false);
    boolean saturday=intent.getBooleanExtra("토요일여부",false);
    mymusic_path=intent.getStringExtra("벨소리위치");
    vibration=intent.getBooleanExtra("진동여부",false);
    boolean alarmpush=intent.getBooleanExtra("알람미루기",false);
    String memo=intent.getStringExtra("알람메모");
    alarm_sound_kind=intent.getStringExtra("알람소리종류");
    int alarmposition=intent.getIntExtra("알람날짜미루기용",-1);
    String barcodenumber=intent.getStringExtra("알람바코드번호");
    String barcodephoto=intent.getStringExtra("알람바코드사진");
    double latitude=intent.getDoubleExtra("알람맵해제용위도",0.0);
    double longtitude=intent.getDoubleExtra("알람맵해제용경도",0.0);
    week=Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

    long alarm_time=intent.getLongExtra("알람시간", 1L);
     int i=intent.getIntExtra("알람리퀘", -1);

    if(wayforkillalamr==null||wayforkillalamr.equals("")){
            Log.v("수학문제","문제값없음");

     }else  if(wayforkillalamr.equals("수학문제   쉬움")){
         levelmath=1;
     }else if(wayforkillalamr.equals("수학문제   중간")){
         levelmath=2;
     }else if(wayforkillalamr.equals("수학문제   어려움")){
         levelmath=3;
     }

        if(usingspecificdate==true){

            if(wayforkillalamr.equals("기본알람")){
                Intent intent1 = new Intent(context, alarm.class);
                intent1.putExtra("알람날짜미루기용1",alarmposition);
                intent1.putExtra("알람소리종류1",alarm_sound_kind);
                intent1.putExtra("요일여부1",usingspecificdate);
                intent1.putExtra("알람미루기1",alarmpush);
                intent1.putExtra("알람메모임",memo);
                //알람 음악 및 진동 시작됨.
                alarmmusic();

                context.startActivity(intent1);
            }else if(wayforkillalamr.equals("수학문제   쉬움")||wayforkillalamr.equals("수학문제   중간")||wayforkillalamr.equals("수학문제   어려움")) {
                Intent intent1 = new Intent(context, alarming_math.class);
                intent1.putExtra("수학난이도구분", levelmath);
                intent1.putExtra("알람날짜미루기용2", alarmposition);
                intent1.putExtra("알람소리종류2", alarm_sound_kind);
                intent1.putExtra("요일여부2", usingspecificdate);
                intent1.putExtra("알람미루기2", alarmpush);
                intent1.putExtra("알람메모임2", memo);
                //알람 음악 및 진동 시작됨.
                alarmmusic();

                context.startActivity(intent1);
            }else if(wayforkillalamr.equals("바코드 찍기")){
                Intent intent1 = new Intent(context, alarming_barcode.class);
                intent1.putExtra("알람날짜미루기용3",alarmposition);
                intent1.putExtra("알람소리종류3",alarm_sound_kind);
                intent1.putExtra("요일여부3",usingspecificdate);
                intent1.putExtra("알람미루기3",alarmpush);
                intent1.putExtra("알람메모임3",memo);
                intent1.putExtra("바코드번호임",barcodenumber);
                intent1.putExtra("바코드사진임",barcodephoto);

                //알람 음악 및 진동 시작됨.
                alarmmusic();


                context.startActivity(intent1);
            }else if(wayforkillalamr.equals("위치 알람")){
              //  LatLng from=alarm_map_latlng_reciever.getParcelable("latlng");
                Intent intent1 = new Intent(context, alarmingmap.class);
                intent1.putExtra("알람위도",latitude);
                intent1.putExtra("알람경도",longtitude);
                intent1.putExtra("알람날짜미루기용4",alarmposition);
                intent1.putExtra("알람소리종류4",alarm_sound_kind);
                intent1.putExtra("요일여부4",usingspecificdate);
                intent1.putExtra("알람미루기4",alarmpush);
                intent1.putExtra("알람메모임4",memo);


                //알람 음악 및 진동 시작됨.
                alarmmusic();
                context.startActivity(intent1);
            }



    }//특정날짜를 정한 경우
        else if(usingspecificdate==false){

        if(week==Calendar.SUNDAY){
            if(sunday==true){
                if(wayforkillalamr.equals("기본알람")){
                    Intent intent1 = new Intent(context, alarm.class);
                    intent1.putExtra("알람날짜미루기용1",alarmposition);
                    intent1.putExtra("알람소리종류1",alarm_sound_kind);
                    intent1.putExtra("요일여부1",usingspecificdate);
                    intent1.putExtra("알람미루기1",alarmpush);
                    intent1.putExtra("알람메모임",memo);
                    //알람 음악 및 진동 시작됨.
                    alarmmusic();

                    context.startActivity(intent1);
                }else if(wayforkillalamr.equals("수학문제   쉬움")||wayforkillalamr.equals("수학문제   중간")||wayforkillalamr.equals("수학문제   어려움")){
                    Intent intent1 = new Intent(context, alarming_math.class);
                    intent1.putExtra("수학난이도구분",levelmath);
                    intent1.putExtra("알람날짜미루기용2",alarmposition);
                    intent1.putExtra("알람소리종류2",alarm_sound_kind);
                    intent1.putExtra("요일여부2",usingspecificdate);
                    intent1.putExtra("알람미루기2",alarmpush);
                    intent1.putExtra("알람메모임2",memo);
                    //알람 음악 및 진동 시작됨.
                    alarmmusic();

                    context.startActivity(intent1);
                }else if(wayforkillalamr.equals("바코드 찍기")){
                    Intent intent1 = new Intent(context, alarming_barcode.class);
                    intent1.putExtra("알람날짜미루기용3",alarmposition);
                    intent1.putExtra("알람소리종류3",alarm_sound_kind);
                    intent1.putExtra("요일여부3",usingspecificdate);
                    intent1.putExtra("알람미루기3",alarmpush);
                    intent1.putExtra("알람메모임3",memo);
                    intent1.putExtra("바코드번호임",barcodenumber);
                    intent1.putExtra("바코드사진임",barcodephoto);

                    //알람 음악 및 진동 시작됨.
                    alarmmusic();


                    context.startActivity(intent1);
                }else if(wayforkillalamr.equals("위치 알람")){
                    //  LatLng from=alarm_map_latlng_reciever.getParcelable("latlng");
                    Intent intent1 = new Intent(context, alarmingmap.class);
                    intent1.putExtra("알람위도",latitude);
                    intent1.putExtra("알람경도",longtitude);
                    intent1.putExtra("알람날짜미루기용4",alarmposition);
                    intent1.putExtra("알람소리종류4",alarm_sound_kind);
                    intent1.putExtra("요일여부4",usingspecificdate);
                    intent1.putExtra("알람미루기4",alarmpush);
                    intent1.putExtra("알람메모임4",memo);


                    //알람 음악 및 진동 시작됨.
                    alarmmusic();
                    context.startActivity(intent1);
                }


                //요일 반복으로 다시  알람등록해주는 메소드
                //set_repeat_day_alarm(i,intent,alarm_time);

            }//일요일 고른게 맞는경우
        }//오늘이 일요일인  경우
        else if(week==Calendar.MONDAY){
            if(monday==true){
                if(wayforkillalamr.equals("기본알람")){
                    Intent intent1 = new Intent(context, alarm.class);
                    intent1.putExtra("알람날짜미루기용1",alarmposition);
                    intent1.putExtra("알람소리종류1",alarm_sound_kind);
                    intent1.putExtra("요일여부1",usingspecificdate);
                    intent1.putExtra("알람미루기1",alarmpush);
                    intent1.putExtra("알람메모임",memo);
                    //알람 음악 및 진동 시작됨.
                    alarmmusic();

                    context.startActivity(intent1);
                }else if(wayforkillalamr.equals("수학문제   쉬움")||wayforkillalamr.equals("수학문제   중간")||wayforkillalamr.equals("수학문제   어려움")){
                    Intent intent1 = new Intent(context, alarming_math.class);
                    intent1.putExtra("수학난이도구분",levelmath);
                    intent1.putExtra("알람날짜미루기용2",alarmposition);
                    intent1.putExtra("알람소리종류2",alarm_sound_kind);
                    intent1.putExtra("요일여부2",usingspecificdate);
                    intent1.putExtra("알람미루기2",alarmpush);
                    intent1.putExtra("알람메모임2",memo);
                    //알람 음악 및 진동 시작됨.
                    alarmmusic();

                    context.startActivity(intent1);
                }else if(wayforkillalamr.equals("바코드 찍기")){
                    Intent intent1 = new Intent(context, alarming_barcode.class);
                    intent1.putExtra("알람날짜미루기용3",alarmposition);
                    intent1.putExtra("알람소리종류3",alarm_sound_kind);
                    intent1.putExtra("요일여부3",usingspecificdate);
                    intent1.putExtra("알람미루기3",alarmpush);
                    intent1.putExtra("알람메모임3",memo);
                    intent1.putExtra("바코드번호임",barcodenumber);
                    intent1.putExtra("바코드사진임",barcodephoto);

                    //알람 음악 및 진동 시작됨.
                    alarmmusic();


                    context.startActivity(intent1);
                }else if(wayforkillalamr.equals("위치 알람")){
                    //  LatLng from=alarm_map_latlng_reciever.getParcelable("latlng");
                    Intent intent1 = new Intent(context, alarmingmap.class);
                    intent1.putExtra("알람위도",latitude);
                    intent1.putExtra("알람경도",longtitude);
                    intent1.putExtra("알람날짜미루기용4",alarmposition);
                    intent1.putExtra("알람소리종류4",alarm_sound_kind);
                    intent1.putExtra("요일여부4",usingspecificdate);
                    intent1.putExtra("알람미루기4",alarmpush);
                    intent1.putExtra("알람메모임4",memo);


                    //알람 음악 및 진동 시작됨.
                    alarmmusic();
                    context.startActivity(intent1);
                }

                //요일 반복으로 다시  알람등록해주는 메소드
                //set_repeat_day_alarm(i,intent,alarm_time);

            }
        } else if(week==Calendar.TUESDAY){
            if(tuseday==true){
                if(wayforkillalamr.equals("기본알람")){
                    Intent intent1 = new Intent(context, alarm.class);
                    intent1.putExtra("알람날짜미루기용1",alarmposition);
                    intent1.putExtra("알람소리종류1",alarm_sound_kind);
                    intent1.putExtra("요일여부1",usingspecificdate);
                    intent1.putExtra("알람미루기1",alarmpush);
                    intent1.putExtra("알람메모임",memo);
                    //알람 음악 및 진동 시작됨.
                    alarmmusic();

                    context.startActivity(intent1);
                }else if(wayforkillalamr.equals("수학문제   쉬움")||wayforkillalamr.equals("수학문제   중간")||wayforkillalamr.equals("수학문제   어려움")){
                    Intent intent1 = new Intent(context, alarming_math.class);
                    intent1.putExtra("수학난이도구분",levelmath);
                    intent1.putExtra("알람날짜미루기용2",alarmposition);
                    intent1.putExtra("알람소리종류2",alarm_sound_kind);
                    intent1.putExtra("요일여부2",usingspecificdate);
                    intent1.putExtra("알람미루기2",alarmpush);
                    intent1.putExtra("알람메모임2",memo);
                    //알람 음악 및 진동 시작됨.
                    alarmmusic();

                    context.startActivity(intent1);
                }else if(wayforkillalamr.equals("바코드 찍기")){
                    Intent intent1 = new Intent(context, alarming_barcode.class);
                    intent1.putExtra("알람날짜미루기용3",alarmposition);
                    intent1.putExtra("알람소리종류3",alarm_sound_kind);
                    intent1.putExtra("요일여부3",usingspecificdate);
                    intent1.putExtra("알람미루기3",alarmpush);
                    intent1.putExtra("알람메모임3",memo);
                    intent1.putExtra("바코드번호임",barcodenumber);
                    intent1.putExtra("바코드사진임",barcodephoto);

                    //알람 음악 및 진동 시작됨.
                    alarmmusic();


                    context.startActivity(intent1);
                }else if(wayforkillalamr.equals("위치 알람")){
                    //  LatLng from=alarm_map_latlng_reciever.getParcelable("latlng");
                    Intent intent1 = new Intent(context, alarmingmap.class);
                    intent1.putExtra("알람위도",latitude);
                    intent1.putExtra("알람경도",longtitude);
                    intent1.putExtra("알람날짜미루기용4",alarmposition);
                    intent1.putExtra("알람소리종류4",alarm_sound_kind);
                    intent1.putExtra("요일여부4",usingspecificdate);
                    intent1.putExtra("알람미루기4",alarmpush);
                    intent1.putExtra("알람메모임4",memo);


                    //알람 음악 및 진동 시작됨.
                    alarmmusic();
                    context.startActivity(intent1);
                }

                //요일 반복으로 다시  알람등록해주는 메소드
                //set_repeat_day_alarm(i,intent,alarm_time);

            }
        } else if(week==Calendar.WEDNESDAY){
            if(wednesday==true){
                if(wayforkillalamr.equals("기본알람")){
                    Intent intent1 = new Intent(context, alarm.class);
                    intent1.putExtra("알람날짜미루기용1",alarmposition);
                    intent1.putExtra("알람소리종류1",alarm_sound_kind);
                    intent1.putExtra("요일여부1",usingspecificdate);
                    intent1.putExtra("알람미루기1",alarmpush);
                    intent1.putExtra("알람메모임",memo);
                    //알람 음악 및 진동 시작됨.
                    alarmmusic();

                    context.startActivity(intent1);
                }else if(wayforkillalamr.equals("수학문제   쉬움")||wayforkillalamr.equals("수학문제   중간")||wayforkillalamr.equals("수학문제   어려움")){
                    Intent intent1 = new Intent(context, alarming_math.class);
                    intent1.putExtra("수학난이도구분",levelmath);
                    intent1.putExtra("알람날짜미루기용2",alarmposition);
                    intent1.putExtra("알람소리종류2",alarm_sound_kind);
                    intent1.putExtra("요일여부2",usingspecificdate);
                    intent1.putExtra("알람미루기2",alarmpush);
                    intent1.putExtra("알람메모임2",memo);
                    //알람 음악 및 진동 시작됨.
                    alarmmusic();

                    context.startActivity(intent1);
                }else if(wayforkillalamr.equals("바코드 찍기")){
                    Intent intent1 = new Intent(context, alarming_barcode.class);
                    intent1.putExtra("알람날짜미루기용3",alarmposition);
                    intent1.putExtra("알람소리종류3",alarm_sound_kind);
                    intent1.putExtra("요일여부3",usingspecificdate);
                    intent1.putExtra("알람미루기3",alarmpush);
                    intent1.putExtra("알람메모임3",memo);
                    intent1.putExtra("바코드번호임",barcodenumber);
                    intent1.putExtra("바코드사진임",barcodephoto);

                    //알람 음악 및 진동 시작됨.
                    alarmmusic();


                    context.startActivity(intent1);
                }else if(wayforkillalamr.equals("위치 알람")){
                    //  LatLng from=alarm_map_latlng_reciever.getParcelable("latlng");
                    Intent intent1 = new Intent(context, alarmingmap.class);
                    intent1.putExtra("알람위도",latitude);
                    intent1.putExtra("알람경도",longtitude);
                    intent1.putExtra("알람날짜미루기용4",alarmposition);
                    intent1.putExtra("알람소리종류4",alarm_sound_kind);
                    intent1.putExtra("요일여부4",usingspecificdate);
                    intent1.putExtra("알람미루기4",alarmpush);
                    intent1.putExtra("알람메모임4",memo);


                    //알람 음악 및 진동 시작됨.
                    alarmmusic();
                    context.startActivity(intent1);
                }
                //요일 반복으로 다시  알람등록해주는 메소드
                //set_repeat_day_alarm(i,intent,alarm_time);

            }
        } else if(week==Calendar.THURSDAY){
            if(thursday==true){
                if(wayforkillalamr.equals("기본알람")){
                    Intent intent1 = new Intent(context, alarm.class);
                    intent1.putExtra("알람날짜미루기용1",alarmposition);
                    intent1.putExtra("알람소리종류1",alarm_sound_kind);
                    intent1.putExtra("요일여부1",usingspecificdate);
                    intent1.putExtra("알람미루기1",alarmpush);
                    intent1.putExtra("알람메모임",memo);
                    //알람 음악 및 진동 시작됨.
                    alarmmusic();

                    context.startActivity(intent1);
                }else if(wayforkillalamr.equals("수학문제   쉬움")||wayforkillalamr.equals("수학문제   중간")||wayforkillalamr.equals("수학문제   어려움")){
                    Intent intent1 = new Intent(context, alarming_math.class);
                    intent1.putExtra("수학난이도구분",levelmath);
                    intent1.putExtra("알람날짜미루기용2",alarmposition);
                    intent1.putExtra("알람소리종류2",alarm_sound_kind);
                    intent1.putExtra("요일여부2",usingspecificdate);
                    intent1.putExtra("알람미루기2",alarmpush);
                    intent1.putExtra("알람메모임2",memo);
                    //알람 음악 및 진동 시작됨.
                    alarmmusic();
                     
                    context.startActivity(intent1);
                }else if(wayforkillalamr.equals("바코드 찍기")){
                    Intent intent1 = new Intent(context, alarming_barcode.class);
                    intent1.putExtra("알람날짜미루기용3",alarmposition);
                    intent1.putExtra("알람소리종류3",alarm_sound_kind);
                    intent1.putExtra("요일여부3",usingspecificdate);
                    intent1.putExtra("알람미루기3",alarmpush);
                    intent1.putExtra("알람메모임3",memo);
                    intent1.putExtra("바코드번호임",barcodenumber);
                    intent1.putExtra("바코드사진임",barcodephoto);

                    //알람 음악 및 진동 시작됨.
                    alarmmusic();


                    context.startActivity(intent1);
                }else if(wayforkillalamr.equals("위치 알람")){
                    //  LatLng from=alarm_map_latlng_reciever.getParcelable("latlng");
                    Intent intent1 = new Intent(context, alarmingmap.class);
                    intent1.putExtra("알람위도",latitude);
                    intent1.putExtra("알람경도",longtitude);
                    intent1.putExtra("알람날짜미루기용4",alarmposition);
                    intent1.putExtra("알람소리종류4",alarm_sound_kind);
                    intent1.putExtra("요일여부4",usingspecificdate);
                    intent1.putExtra("알람미루기4",alarmpush);
                    intent1.putExtra("알람메모임4",memo);


                    //알람 음악 및 진동 시작됨.
                    alarmmusic();
                    context.startActivity(intent1);
                }
                //요일 반복으로 다시  알람등록해주는 메소드
                //set_repeat_day_alarm(i,intent,alarm_time);

            }
        } else if(week==Calendar.FRIDAY){
            if(friday==true){
                if(wayforkillalamr.equals("기본알람")){
                    Intent intent1 = new Intent(context, alarm.class);
                    intent1.putExtra("알람날짜미루기용1",alarmposition);
                    intent1.putExtra("알람소리종류1",alarm_sound_kind);
                    intent1.putExtra("요일여부1",usingspecificdate);
                    intent1.putExtra("알람미루기1",alarmpush);
                    intent1.putExtra("알람메모임",memo);
                    //알람 음악 및 진동 시작됨.
                    alarmmusic();

                    context.startActivity(intent1);
                }else if(wayforkillalamr.equals("수학문제   쉬움")||wayforkillalamr.equals("수학문제   중간")||wayforkillalamr.equals("수학문제   어려움")){
                    Intent intent1 = new Intent(context, alarming_math.class);
                    intent1.putExtra("수학난이도구분",levelmath);
                    intent1.putExtra("알람날짜미루기용2",alarmposition);
                    intent1.putExtra("알람소리종류2",alarm_sound_kind);
                    intent1.putExtra("요일여부2",usingspecificdate);
                    intent1.putExtra("알람미루기2",alarmpush);
                    intent1.putExtra("알람메모임2",memo);
                    //알람 음악 및 진동 시작됨.
                    alarmmusic();

                    context.startActivity(intent1);
                }else if(wayforkillalamr.equals("바코드 찍기")){
                    Intent intent1 = new Intent(context, alarming_barcode.class);
                    intent1.putExtra("알람날짜미루기용3",alarmposition);
                    intent1.putExtra("알람소리종류3",alarm_sound_kind);
                    intent1.putExtra("요일여부3",usingspecificdate);
                    intent1.putExtra("알람미루기3",alarmpush);
                    intent1.putExtra("알람메모임3",memo);
                    intent1.putExtra("바코드번호임",barcodenumber);
                    intent1.putExtra("바코드사진임",barcodephoto);

                    //알람 음악 및 진동 시작됨.
                    alarmmusic();


                    context.startActivity(intent1);
                }else if(wayforkillalamr.equals("위치 알람")){
                    //  LatLng from=alarm_map_latlng_reciever.getParcelable("latlng");
                    Intent intent1 = new Intent(context, alarmingmap.class);
                    intent1.putExtra("알람위도",latitude);
                    intent1.putExtra("알람경도",longtitude);
                    intent1.putExtra("알람날짜미루기용4",alarmposition);
                    intent1.putExtra("알람소리종류4",alarm_sound_kind);
                    intent1.putExtra("요일여부4",usingspecificdate);
                    intent1.putExtra("알람미루기4",alarmpush);
                    intent1.putExtra("알람메모임4",memo);


                    //알람 음악 및 진동 시작됨.
                    alarmmusic();
                    context.startActivity(intent1);




                }

                //요일 반복으로 다시  알람등록해주는 메소드
                //set_repeat_day_alarm(i,intent,alarm_time);
            }
        } else if(week==Calendar.SATURDAY){
            if(saturday==true){
                if(wayforkillalamr.equals("기본알람")){
                    Intent intent1 = new Intent(context, alarm.class);
                    intent1.putExtra("알람날짜미루기용1",alarmposition);
                    intent1.putExtra("알람소리종류1",alarm_sound_kind);
                    intent1.putExtra("요일여부1",usingspecificdate);
                    intent1.putExtra("알람미루기1",alarmpush);
                    intent1.putExtra("알람메모임",memo);
                    //알람 음악 및 진동 시작됨.
                    alarmmusic();

                    context.startActivity(intent1);
                }else if(wayforkillalamr.equals("수학문제   쉬움")||wayforkillalamr.equals("수학문제   중간")||wayforkillalamr.equals("수학문제   어려움")){
                    Intent intent1 = new Intent(context, alarming_math.class);
                    intent1.putExtra("수학난이도구분",levelmath);
                    intent1.putExtra("알람날짜미루기용2",alarmposition);
                    intent1.putExtra("알람소리종류2",alarm_sound_kind);
                    intent1.putExtra("요일여부2",usingspecificdate);
                    intent1.putExtra("알람미루기2",alarmpush);
                    intent1.putExtra("알람메모임2",memo);
                    //알람 음악 및 진동 시작됨.
                    alarmmusic();

                    context.startActivity(intent1);
                }else if(wayforkillalamr.equals("바코드 찍기")){
                    Intent intent1 = new Intent(context, alarming_barcode.class);
                    intent1.putExtra("알람날짜미루기용3",alarmposition);
                    intent1.putExtra("알람소리종류3",alarm_sound_kind);
                    intent1.putExtra("요일여부3",usingspecificdate);
                    intent1.putExtra("알람미루기3",alarmpush);
                    intent1.putExtra("알람메모임3",memo);
                    intent1.putExtra("바코드번호임",barcodenumber);
                    intent1.putExtra("바코드사진임",barcodephoto);

                    //알람 음악 및 진동 시작됨.
                    alarmmusic();


                    context.startActivity(intent1);
                }else if(wayforkillalamr.equals("위치 알람")){
                    //  LatLng from=alarm_map_latlng_reciever.getParcelable("latlng");
                    Intent intent1 = new Intent(context, alarmingmap.class);
                    intent1.putExtra("알람위도",latitude);
                    intent1.putExtra("알람경도",longtitude);
                    intent1.putExtra("알람날짜미루기용4",alarmposition);
                    intent1.putExtra("알람소리종류4",alarm_sound_kind);
                    intent1.putExtra("요일여부4",usingspecificdate);
                    intent1.putExtra("알람미루기4",alarmpush);
                    intent1.putExtra("알람메모임4",memo);


                    //알람 음악 및 진동 시작됨.
                    alarmmusic();
                    context.startActivity(intent1);
                }

                 //요일 반복으로 다시  알람등록해주는 메소드
              //  set_repeat_day_alarm(i,intent,alarm_time);

            }
        }

    }//특정날짜를 정하지않은 경우

}
//
////요일 반복으로 진행했을때,  원래 setrepeat을  사용했지만, 도즈모드  때문에
////setclock으로 바꿨고, 해당 요일에 알람이 울리면,  다시  해당  알람으  등록해주는 식으로 진행한다.
//@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//public void set_repeat_day_alarm(int i, Intent intent, long alarm_time){
//
//    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//    AlarmManager.AlarmClockInfo ac = new AlarmManager.AlarmClockInfo(alarm_time, pendingIntent);
//    AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);;
//    alarmManager.setAlarmClock(ac,pendingIntent);
//
//}


    public  void alarmmusic(){

        mediaPlayer=new MediaPlayer();
        vibrator = (Vibrator)context. getSystemService(Context.VIBRATOR_SERVICE);


        final AudioManager mAudioManager = (AudioManager)context.getSystemService(AUDIO_SERVICE);
        final int originalVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        //벨소리 상태를 항상 최대치로  만들어 놓기 위한 코드이다.
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0);
            }
        });


        if(alarm_sound_kind.equals("알람 사운드 사용 x")){

        }
        else if(alarm_sound_kind.equals("기본싸이렌소리1")){
            mediaPlayer=MediaPlayer.create(context,R.raw.siren1);
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(1,1);
            mediaPlayer.start();
        }else if(alarm_sound_kind.equals("기본싸이렌소리2")){
            mediaPlayer=MediaPlayer.create(context,R.raw.siren2);
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(1,1);
            mediaPlayer.start();
        }else {
            try {

                mediaPlayer.setDataSource(mymusic_path);
                mediaPlayer.prepare();
                mediaPlayer.setLooping(true);

            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        }





        if (vibration == true) {
            //진동같은 경우에는  0.5초 대기후 1.5초동안 울리며  그 주기를 반복하도록 하였다
            vibrator.vibrate(new long[]{500, 1500}, 0);

        }
        }

    }













