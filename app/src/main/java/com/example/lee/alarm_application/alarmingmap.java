package com.example.lee.alarm_application;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.lee.alarm_application.AlarmReceiver.vibration;
import static com.example.lee.alarm_application.AlarmReceiver.vibrator;
import static com.example.lee.alarm_application.basic_alarm_screen.intentArrayList;

/**
 * alarm_application
 * Class: alarmingmap.
 * Created by leedonghun.
 * Created On 2018-10-02.
 * Description:
 */
public class alarmingmap extends AppCompatActivity implements cancelmamview.OnMyListener2{
  Button finish4;
  Button pushalarm4;
  TextView presenttime44;
  TextView memo4;
TextView explain;
    public static Intent intentalarm;
    public static int  get_alarm_position;
    public  static boolean alarm_date_using;
    public String get_alarm_kinds;
    public String get_message;
    public boolean alarm_pushed;

  String result;

  //해제용 맵 프레그먼트에서 넘어옴
    @Override
    public void onReceivedData2(String data) {
      result=data;
      if(result.equals("성공")){
          finish4.setVisibility(View.VISIBLE);
          explain.setVisibility(View.INVISIBLE);
      }else{


      }

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //알람해제 창이 맨처음 나왔을때
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

       setContentView(R.layout.alarming_map);
       finish4=(Button) findViewById(R.id.finishalarming4);
       pushalarm4=(Button)findViewById(R.id.alarmingpush4);
       presenttime44=(TextView)findViewById(R.id.presenttime4);
       memo4=(TextView)findViewById(R.id.message4);
       explain=(TextView)findViewById(R.id.explainmapcancel);

       //맵뷰 프래그먼트 연결
        android.support.v4.app.FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        cancelmamview mainFragment = new cancelmamview();
        transaction.replace(R.id.cancemapvie_contatiner,mainFragment);
        transaction.commit();

        Animation translate = AnimationUtils.loadAnimation(this, R.anim.translate);
        Animation rotate=AnimationUtils.loadAnimation(this,R.anim.rotation);


        //파워매니저를 사용하여서  알람이 울렸을경우, 알람 해제 창이  시간이 지나도 슬립모드로 빠지지 않도록 조치하였다.
        //파워매니저 선언
        PowerManager pm = (PowerManager)getApplicationContext().getSystemService(POWER_SERVICE);
        //파워매니저의 웨이크락을  full wakelock-(계속 켜져있는 상태 cpu, screen keyboard 모두다 ) 로  바꿈.
        final PowerManager.WakeLock wakeLock= pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "POWER");
        wakeLock.acquire();


        intentalarm = getIntent();
        alarm_pushed = intentalarm.getBooleanExtra("알람미루기4", false);
        get_message = intentalarm.getStringExtra("알람메모임4");
        get_alarm_kinds=intentalarm.getStringExtra("알람소리종류4");
        alarm_date_using=intentalarm.getBooleanExtra("요일여부4",false);
        get_alarm_position = intentalarm.getIntExtra("알람날짜미루기용4", -1);
        //메세지가  있을때 메세지  창이  비져블로 바뀐다.

        memo4.setVisibility(View.INVISIBLE);
        if(get_message.equals("")||get_message==null){
            memo4.setVisibility(View.INVISIBLE);
        }else if(!(get_message.equals("")||get_message==null)) {
            memo4.setVisibility(View.VISIBLE);
            memo4.setText(get_message);
            memo4.startAnimation(translate);
        }
        //아닐때는 메세지 창이 인비져블로 보이지 않게된다.

        //알람 푸쉬가  TRUE일때  알람  미루기 버튼이 보인다
        if (alarm_pushed == true) {
            pushalarm4.setVisibility(View.VISIBLE);
        } else if (alarm_pushed == false) {
            pushalarm4.setVisibility(View.INVISIBLE);
        }


        //현재시간을 텍스트에서 보여주고
        presenttime44.setText(DateFormat.getTimeInstance().format(new Date()));
        //아래에 정해놓은  쓰레드를 사용해서 현재시간이 계속 업데이트 된다.
        ShowTimeMethod();


        //해제버튼
        finish4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent create=new Intent(getApplicationContext(),NewsList.class);

                startActivity(create);
                cancelAlarm(getApplicationContext());
                Toast.makeText(getApplicationContext(), "알람이 해제 되었습니다.", Toast.LENGTH_LONG).show();
                finish();


                //알람을 해제하면  파워매니저도 해제된다.
                wakeLock.release();

            }
        });
        //알람 미루기  버튼을 눌렀을때 진행되는 사항
        pushalarm4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                //아래쪽에서 만들어놓은 메소드를  가지고 왔다.
                expandAlarm(getApplicationContext());

                finish();

                //아래 코드 넣으니까 앱이 종료된 상태에서도 알람미루기가 잘 잘독됨  이 유알아보자
                //system.exit함수는  시스템을 종료한다는 뜻인데  이때 status를 영으로 두면  어느것이든 괜찮다가 되어
                //그냥 종료된다.
                //기존에 이함수를 넣기 전에는  에러 부분이 나와서  프로세스가 종료? 가 되지 않고 바로 에러를 반환 하였다.
                //하지만  에러를 반환할때 경우는 broadcast intent 의 anr 코드상의 문제는 아니였던것 같다.
                //그래서 이를 그냥 무시하고 종료하게 되면 기존대로  진행이 되어진다라고 이해함.
                System.exit(0);
            }
        });


        //엑티비티가 나온상태에서  스크린이 꺼지면 진동같은 경우에는 멈추는데
        //엑티비티에서  알람리시버로  인텐트 필터를 통해  스크린이 꺼질경우  바이브레이션을 다시 시작하도록 조치 하였다.
        BroadcastReceiver AlarmReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
                    if (vibration == true) {
                        //진동같은 경우에는  0.5초 대기후 1.5초동안 울리며  그 주기를 반복하도록 하였다
                        vibrator.vibrate(new long[]{500, 1500}, 0);

                    }
                }else if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
                    if (vibration == true) {
                        //진동같은 경우에는  0.5초 대기후 1.5초동안 울리며  그 주기를 반복하도록 하였다
                        vibrator.vibrate(new long[]{500, 1500}, 0);
                    }


                }
            }
        };
        IntentFilter filter=new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(AlarmReceiver,filter);







    }//oncreate 꺼짐


    //현재 시간을  텍스트에 넣고  핸들러를  1초마다  보내주는 쓰레드이다.
    //이렇게 하여  현재 시간이 계속 갱신되어서 보여진다.
    public void ShowTimeMethod() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                presenttime44.setText(DateFormat.getTimeInstance().format(new Date()));
            }
        };
        Runnable task = new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException E) {
                    }
                    handler.sendEmptyMessage(1);
                }
            }

        };
        Thread thread = new Thread(task);
        thread.start();
    }
    //뒤로가기 키버튼의 대한  이벤트를 받는 메소드이다.
    //눌렀을때 아무것도 진행되지 않게 막아놓았기 때문에 아무것도  진행되지 않느다.
    public void onBackPressed() {
        //  super.onBackPressed();

    }

    //onkeuup의 경우  눌렀다가 놓았을경우 ,  해당 이벤트가 실행된다.
    //나같은 경우에는 누르자마자 이벤트자 진행이 되어야하므로 onkeydown메소드를 사용하였다.
    public boolean onKeyDown(int keycode, KeyEvent event) {
        switch (keycode) {
            //볼륨 다운 키의  키이벤트에 아무것도 넣지 않아  막아놓은 효과냄
            case KeyEvent.KEYCODE_VOLUME_DOWN:

                break;
            //볼륨 업 키의  키이벤트에 아무것도 넣지 않아  막아놓은 효과냄
            case KeyEvent.KEYCODE_VOLUME_UP:


                break;
            case KeyEvent.KEYCODE_MENU:

                Log.i("menu", "menu Pressed..!");
                break;
        }
        return true;
    }
    @Override
    protected void onPause() {
        //홈키가 눌리면 아래 함수를  통해 홈키 눌림 여부를 판단하고
        //엑티비티 순서를 최상위로 바꾸어 주는  intent 를 실행한다.
        if(isApplicationSentToBackground(this)){

            expandAlarm1(getApplicationContext());

            //인텐트로  홈키가  눌렷을때  현재  알람 페이지를 다시 실행한다.
            //하지만 다시 실행하면  리셋된 페이지가 나오므로
            //flag activity reorder to front를 사용한다. 이 플래그는 최상단위에서 이미  진행된
            //엑티비티에 경우 그 해당 엑티비티를 재사용한다.
            //이렇게 되면 해결됨.
            //하지만
            //Intent i = new Intent(getApplicationContext(), alarm.class);
            //i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            //startActivity(i);
            //Log.i("menu", "진행됬나??");


            finish();
            System.exit(0);
        }
        super.onPause();
        //메뉴버튼을 누르면  onpause상태로  오는 것을 확인 하였고 여기에
        // 엑티비티 매니저를 사용하여  현재  task를  가장 상단으로  오도록 만들었다
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.moveTaskToFront(getTaskId(), 0);
        Log.i("menu", "메뉴눌림??");
    }


    //홈 키를 눌렀을 경우에는 홈키가  키이벤트로 먹히도록  진행한다.
    //이해 필요
    public boolean isApplicationSentToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
    //알람을 취소하는 메소드이다.
    public static void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        intentArrayList = new ArrayList<PendingIntent>();

        for (int i = 0; i < intentArrayList.size(); i++) {

            PendingIntent pendingIntent = intentArrayList.get(i);
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }

        AlarmReceiver.mediaPlayer.stop();
        AlarmReceiver.mediaPlayer.release();
        vibrator.cancel();

    }

    //알람 미루기  메소드이다.
    public void expandAlarm(Context context) {


        //우선 알람이 처음 진행될때  여러  진행 사항들을 취소 시킨다.
        AlarmReceiver.mediaPlayer.stop();
        AlarmReceiver.mediaPlayer.release();
        vibrator.cancel();

        //그다음에  알람을 다시 진행하는데  기존에  설정 했던 알람의 아이디 값을 받아왔으므로 그 아이디값 get_alarm_position을 넣어서//
        //해당 알라믈 불러와 다시  현재시간의 10초 뒤로 바꿔주어  진행하였다.
        //그렇게 되므로 써 해당 알람의  기존 인텐트 값을 유지ㅏ면서 알람의 트리거 시간이 바끼게 된다.
        Intent intent=new Intent(context,AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,get_alarm_position,intent,0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= 23) {
            //현재는 과제시현을 생각하여 10초뒤로 설정함.
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+10000, pendingIntent);

        } else {
            if (Build.VERSION.SDK_INT >= 19) {

                alarmManager.setExact(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis()+10000, pendingIntent);

            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+10000, pendingIntent);
            }
        }




    }

    //알람 미루기  메소드이다.
    public void expandAlarm1(Context context) {


        //우선 알람이 처음 진행될때  여러  진행 사항들을 취소 시킨다.
        AlarmReceiver.mediaPlayer.stop();
        AlarmReceiver.mediaPlayer.release();
        vibrator.cancel();

        //그다음에  알람을 다시 진행하는데  기존에  설정 했던 알람의 아이디 값을 받아왔으므로 그 아이디값 get_alarm_position을 넣어서//
        //해당 알라믈 불러와 다시  현재시간의 10초 뒤로 바꿔주어  진행하였다.
        //그렇게 되므로 써 해당 알람의  기존 인텐트 값을 유지ㅏ면서 알람의 트리거 시간이 바끼게 된다.
        Intent intent=new Intent(context,AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,get_alarm_position,intent,0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= 23) {
            //현재는 과제시현을 생각하여 10초뒤로 설정함.
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+500, pendingIntent);

        } else {
            if (Build.VERSION.SDK_INT >= 19) {

                alarmManager.setExact(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis()+500, pendingIntent);

            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+500, pendingIntent);
            }
        }




    }

}
