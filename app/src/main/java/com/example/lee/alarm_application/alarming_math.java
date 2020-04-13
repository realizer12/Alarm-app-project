package com.example.lee.alarm_application;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.example.lee.alarm_application.AlarmReceiver.vibration;
import static com.example.lee.alarm_application.AlarmReceiver.vibrator;
import static com.example.lee.alarm_application.basic_alarm_screen.intentArrayList;

/**
 * alarm_application
 * Class: alarming_math.
 * Created by leedonghun.
 * Created On 2018-09-22.
 * Description:알람 해제 방법중  수학 알람 해제 방법이다.
 */
public class alarming_math extends AppCompatActivity {
    Button push_mathalarming;
    Button puttheanswer_math;
    TextView presenttimemath;
    TextView math_question_card;
    EditText putanswer_line;
    TextView alarm_message;
    TextView leaving_question;
    //인텐트  리시버로부터 가져오기
    public static Intent intentalarm2;
    public static int get_alarm_position2;
    public static boolean alarm_date_using2;
    public String get_alarm_kinds2;
    public String get_message2;
    public boolean alarm_pushed2;
    public int math_level;
    //


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //알람해제 창이 맨처음 나왔을때
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.alarming_math);

        //알람 미루기 버튼 선언
        push_mathalarming = (Button) findViewById(R.id.alarmpushmath);

        //정답입력 버튼
        puttheanswer_math = (Button) findViewById(R.id.pueanswerbtn);

        //현재시간  창
        presenttimemath = (TextView) findViewById(R.id.presenttime_math);

        //문제 카드
        math_question_card = (TextView) findViewById(R.id.math_question);

        //정답 들어가는 edit_text
        putanswer_line = (EditText) findViewById(R.id.putanswerline);

        //메세지 들어감.
        alarm_message = (TextView) findViewById(R.id.message_math);

        //문제 개수
        leaving_question = (TextView) findViewById(R.id.leaving_question);


        Animation translate = AnimationUtils.loadAnimation(this, R.anim.translate);
        final Animation shake=AnimationUtils.loadAnimation(this,R.anim.shake);

        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(POWER_SERVICE);
        //파워매니저의 웨이크락을  full wakelock-(계속 켜져있는 상태 cpu, screen keyboard 모두다 ) 로  바꿈.
        final PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "POWER");
        wakeLock.acquire();


        intentalarm2 = getIntent();
        math_level = intentalarm2.getIntExtra("수학난이도구분",0);
        alarm_pushed2 = intentalarm2.getBooleanExtra("알람미루기2", false);
        get_message2 = intentalarm2.getStringExtra("알람메모임2");
        get_alarm_kinds2 = intentalarm2.getStringExtra("알람소리종류2");
        alarm_date_using2 = intentalarm2.getBooleanExtra("요일여부2", false);
        get_alarm_position2 = intentalarm2.getIntExtra("알람날짜미루기용2", -1);


        alarm_message.setVisibility(View.INVISIBLE);
        if (get_message2.equals("") || get_message2 == null) {
            alarm_message.setVisibility(View.INVISIBLE);
        } else if (!(get_message2.equals("") || get_message2 == null)) {
            alarm_message.setVisibility(View.VISIBLE);
            alarm_message.setText(get_message2);
            alarm_message.startAnimation(translate);
        }
        //아닐때는 메세지 창이 인비져블로 보이지 않게된다.


        //알람 푸쉬가  TRUE일때  알람  미루기 버튼이 보인다
        if (alarm_pushed2 == true) {
            push_mathalarming.setVisibility(View.VISIBLE);
        } else if (alarm_pushed2 == false) {
            push_mathalarming.setVisibility(View.INVISIBLE);
        }


        //현재시간을 텍스트에서 보여주고
        presenttimemath.setText(DateFormat.getTimeInstance().format(new Date()));
        //아래에 정해놓은  쓰레드를 사용해서 현재시간이 계속 업데이트 된다.
        ShowTimeMethod();

         //수학문제 난이도  쉬움임
        if(math_level==1) {
            final Random random = new Random();
            final int[] a = {random.nextInt(10) + 1};
            final int[] b = {random.nextInt(10) + 1};

            final int[] leaving_q = {1};
            final int[] percent={random.nextInt(2)};
            final int[] c=new int[2];

            //쉬움 단계에서는 50% 의 확률로  10이내의 숫자들로 구성하여  덧셈뺄셈 공식을 사용하도록 하였다.
            //0이 나오면  덧셈 공식이나온다.
            if(percent[0]==0) {
                math_question_card.setText("" + a[0] + "+" + b[0] + " =??");
                c[0]=a[0]+b[0];
           //1이 나오면 뺄셈 공식이 나온다.
            }else if(percent[0]==1) {
                math_question_card.setText("" + a[0] + "-" + b[0]+ " =??");
                c[0] = a[0]-b[0];
            }
            leaving_question.setText("" + leaving_q[0] + "/3 문제");

            //정답  입력시
            puttheanswer_math.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    String answer = putanswer_line.getText().toString();

                    //정답 입력칸의 정답이  없을 경우이다
                    if (answer.getBytes().length <= 0) {
                        putanswer_line.startAnimation(shake);
                        Toast.makeText(getApplicationContext(), "정답을 입력하세요!!", Toast.LENGTH_LONG).show();

                        //답을 입력하였을경우이다.
                        //답을 입력하면 답이 맞는지 여부를 판단한다.
                    } else if (answer.getBytes().length > 0) {
                        //입렵창에 사용자가쓴  답이다.
                        int temp = Integer.parseInt(answer);

                        //사용자가 쓴 답과  정답이 맞을 경우이다.
                        if (c[0] == temp) {

                            if (leaving_q[0] <= 2) {

                                //정답횟수가 3이되면 알람해제로 버튼 텍스트가 바뀐다, 이전까지는 정답입력이다.
                                if (leaving_q[0] == 2) {
                                    puttheanswer_math.setText("알람해제");
                                }
                                a[0] = random.nextInt(10) + 1;
                                b[0] = random.nextInt(10) + 1;
                                percent[0]=random.nextInt(2);
                                if(percent[0]==0) {
                                    math_question_card.setText("" + a[0] + "+" + b[0] + " =??");
                                    c[0]=a[0]+b[0];
                                    //1이 나오면 뺄셈 공식이 나온다.
                                }else if(percent[0]==1) {
                                    math_question_card.setText("" + a[0] + "-" + b[0]+ " =??");
                                    c[0] = a[0]-b[0];
                                }

                                leaving_q[0]++;
                                leaving_question.setText("" + leaving_q[0] + "/3 문제");

                                putanswer_line.setText(null);
                                Toast.makeText(getApplicationContext(), "정답입니다.!!", Toast.LENGTH_LONG).show();

                             //정답 맞춘횟수가  3을 넘으면  알람이 해제 된다.
                            } else if (leaving_q[0] > 2) {
                                Intent create=new Intent(getApplicationContext(),NewsList.class);

                                startActivity(create);
                                cancelAlarm(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "알람이 해제 되었습니다.", Toast.LENGTH_LONG).show();
                                finish();


                                //알람을 해제하면  파워매니저도 해제된다.
                                wakeLock.release();
                            }
                         //정답이 아닐경우이다.
                        } else if (c[0] != temp) {
                            putanswer_line.startAnimation(shake);
                            putanswer_line.setText(null);
                            Toast.makeText(getApplicationContext(), "정답이 아닙니다.", Toast.LENGTH_LONG).show();
                           ;
                            if(percent[0]==0) {
                                math_question_card.setText("" + a[0] + "+" + b[0] + " =??");
                                c[0]=a[0]+b[0];
                                //1이 나오면 뺄셈 공식이 나온다.
                            }else if(percent[0]==1) {
                                math_question_card.setText("" + a[0] + "-" + b[0]+ " =??");
                                c[0] = a[0]-b[0];
                            }
                            leaving_question.setText("" + leaving_q[0] + "/3 문제");
                        }

                    }
                }
            });
            //수학문제 난이도 중간임
        }else if(math_level==2){
            final Random random = new Random();
            final int[] a = {random.nextInt(20) + 1};
            final int[] b = {random.nextInt(20) + 1};
            final int[] c=  {random.nextInt(20) + 1};

            final int[] leaving_q = {1};
            final int[] percent={random.nextInt(3)};
            final int[] d=new int[3];
            //percen를 이용하여  33%의 확률로 연산식이 바뀌도록 하였다.
            if(percent[0]==0) {
                math_question_card.setText("" + a[0] + "x" + b[0] + " =??");
                d[0]=a[0]*b[0];

            }else if(percent[0]==1){
                math_question_card.setText("" +(a[0] + "x" + b[0])+"+"+c[0]+" =??");
                d[0]=(a[0]*b[0])+c[0];

            }else if(percent[0]==2){
                math_question_card.setText("" +(a[0] + "x" + b[0])+"-"+c[0]+" =??");
                d[0]=(a[0]*b[0])-c[0];

            }
            leaving_question.setText("" + leaving_q[0] + "/3 문제");


            puttheanswer_math.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String answer = putanswer_line.getText().toString();
                    if (answer.getBytes().length <= 0) {
                        putanswer_line.startAnimation(shake);
                        Toast.makeText(getApplicationContext(), "정답을 입력하세요!!", Toast.LENGTH_LONG).show();
                    }else  if (answer.getBytes().length > 0) {
                        int temp = Integer.parseInt(answer);
                        if(d[0]==temp){
                            if (leaving_q[0] <= 2) {
                                if (leaving_q[0] == 2) {
                                    puttheanswer_math.setText("알람해제");
                                }
                                a[0] = random.nextInt(20) + 1;
                                b[0] = random.nextInt(20) + 1;
                                c[0] = random.nextInt(20) + 1;
                                percent[0]=random.nextInt(3);

                                if(percent[0]==0) {
                                    math_question_card.setText("" + a[0] + "x" + b[0] + " =??");
                                    d[0] = a[0] * b[0];
                                }else if(percent[0]==1){
                                    math_question_card.setText("" +(a[0] + "x" + b[0])+"+"+c[0]+" =??");
                                    d[0] =(a[0] * b[0]) + c[0];
                                }else if(percent[0]==2){
                                    math_question_card.setText("" +(a[0] + "x" + b[0])+"-"+c[0]+" =??");
                                    d[0] = (a[0] * b[0]) - c[0];
                                }

                                leaving_q[0]++;
                                leaving_question.setText("" + leaving_q[0] + "/3 문제");

                                putanswer_line.setText(null);
                                Toast.makeText(getApplicationContext(), "정답입니다.!!", Toast.LENGTH_LONG).show();



                            }else if (leaving_q[0] > 2) {
                                Intent create=new Intent(getApplicationContext(),NewsList.class);

                                startActivity(create);
                                cancelAlarm(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "알람이 해제 되었습니다.", Toast.LENGTH_LONG).show();
                                finish();


                                //알람을 해제하면  파워매니저도 해제된다.
                                wakeLock.release();
                            }


                        }else if(d[0] !=temp){
                            putanswer_line.startAnimation(shake);
                            putanswer_line.setText(null);
                            Toast.makeText(getApplicationContext(), "정답이 아닙니다.", Toast.LENGTH_LONG).show();

                            if(percent[0]==0) {
                                math_question_card.setText("" + a[0] + "x" + b[0] + " =??");

                            }else if(percent[0]==1){
                                math_question_card.setText("" +(a[0] + "x" + b[0])+"+"+c[0]+" =??");

                            }else if(percent[0]==2){
                                math_question_card.setText("" +(a[0] + "x" + b[0])+"-"+c[0]+" =??");

                            }
                            leaving_question.setText("" + leaving_q[0] + "/3 문제");
                            }

                    }
                }
            });

            //수학문제 난이도 어려움
        }else if(math_level==3){
            final Random random = new Random();
            final int[] a = {random.nextInt(20) + 1};
            final int[] b = {random.nextInt(20) + 1};
            final int[] c = {random.nextInt(20) + 1};
            final int[] d = {random.nextInt(10) + 1};
            final int[] leaving_q = {1};
            final int[] percent={random.nextInt(4)};
            final int[] e = new int[4];
            if(percent[0]==0){
                math_question_card.setText("" + a[0] + "x"+ b[0] +"x"+ d[0] + "=??");
                e[0]=a[0]*b[0]*d[0];
            } else if (percent[0]==1) {
                math_question_card.setText("(" + a[0] + "x"+ b[0] +"x"+ d[0]+")-"+c[0]+ "=?");
                e[0]=(a[0]*b[0]*d[0])-c[0];
            }else if(percent[0]==2){
                math_question_card.setText("(" + a[0] + "x"+ b[0] +"x"+ d[0]+")+"+c[0]+ "=?");
                e[0]=(a[0]*b[0]*d[0])+c[0];
            }else if(percent[0]==3){
                math_question_card.setText("(" + a[0] +"-"+ b[0] +"+"+ d[0]+")x"+c[0]+ "=?");
                e[0]=(a[0]-b[0]+d[0])*c[0];
            }
           leaving_question.setText("" + leaving_q[0] + "/3 문제");

        puttheanswer_math.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer = putanswer_line.getText().toString();
                if (answer.getBytes().length <= 0) {
                    putanswer_line.startAnimation(shake);
                    Toast.makeText(getApplicationContext(), "정답을 입력하세요!!", Toast.LENGTH_LONG).show();
                }else  if (answer.getBytes().length > 0) {
                    int temp = Integer.parseInt(answer);
                    if(e[0]==temp){

                        if (leaving_q[0] <= 2) {
                            if (leaving_q[0] == 2) {
                                puttheanswer_math.setText("알람해제");
                            }
                            a[0] = random.nextInt(20) + 1;
                            b[0] = random.nextInt(20) + 1;
                            c[0] = random.nextInt(20) + 1;
                            d[0] = random.nextInt(10) + 1;
                            percent[0]=random.nextInt(4);

                            if(percent[0]==0){
                                math_question_card.setText("" + a[0] + "x"+ b[0] +"x"+ d[0] + "=??");
                                e[0]=a[0]*b[0]*d[0];
                            } else if (percent[0]==1) {
                                math_question_card.setText("(" + a[0] + "x"+ b[0] +"x"+ d[0]+")-"+c[0]+ "=?");
                                e[0]=(a[0]*b[0]*d[0])-c[0];
                            }else if(percent[0]==2){
                                math_question_card.setText("(" + a[0] + "x"+ b[0] +"x"+ d[0]+")+"+c[0]+ "=?");
                                e[0]=(a[0]*b[0]*d[0])+c[0];
                            }else if(percent[0]==3){
                                math_question_card.setText("(" + a[0] +"-"+ b[0] +"+"+ d[0]+")x"+c[0]+ "=?");
                                e[0]=(a[0]-b[0]+d[0])*c[0];
                            }

                            leaving_q[0]++;
                            leaving_question.setText("" + leaving_q[0] + "/3 문제");

                            putanswer_line.setText(null);
                            Toast.makeText(getApplicationContext(), "정답입니다.!!", Toast.LENGTH_LONG).show();



                        }else if (leaving_q[0] > 2) {
                            Intent create=new Intent(getApplicationContext(),NewsList.class);

                            startActivity(create);
                            cancelAlarm(getApplicationContext());
                            Toast.makeText(getApplicationContext(), "알람이 해제 되었습니다.", Toast.LENGTH_LONG).show();
                            finish();


                            //알람을 해제하면  파워매니저도 해제된다.
                            wakeLock.release();

                        }


                    }else if(e[0] !=temp){
                        putanswer_line.startAnimation(shake);
                        putanswer_line.setText(null);
                        Toast.makeText(getApplicationContext(), "정답이 아닙니다.", Toast.LENGTH_LONG).show();

                        if(percent[0]==0){
                            math_question_card.setText("" + a[0] + "x"+ b[0] +"x"+ d[0] + "=??");
                            e[0]=a[0]*b[0]*d[0];
                        } else if (percent[0]==1) {
                            math_question_card.setText("(" + a[0] + "x"+ b[0] +"x"+ d[0]+")-"+c[0]+ "=?");
                            e[0]=(a[0]*b[0]*d[0])-c[0];
                        }else if(percent[0]==2){
                            math_question_card.setText("(" + a[0] + "x"+ b[0] +"x"+ d[0]+")+"+c[0]+ "=?");
                            e[0]=(a[0]*b[0]*d[0])+c[0];
                        }else if(percent[0]==3){
                            math_question_card.setText("(" + a[0] +"-"+ b[0] +"+"+ d[0]+")x"+c[0]+ "=?");
                            e[0]=(a[0]-b[0]+d[0])*c[0];
                        }
                        leaving_question.setText("" + leaving_q[0] + "/3 문제");
                    }
                }
            }
        });


        }

        //알람 미루기  버튼을 눌렀을때 진행되는 사항
        push_mathalarming.setOnClickListener(new View.OnClickListener() {
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
        //스크린화면의 on off여부를  받는 인텐트 필터이다.
        IntentFilter filter=new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(AlarmReceiver,filter);

    }//oncreate끝남.

    //현재 시간을  텍스트에 넣고  핸들러를  1초마다  보내주는 쓰레드이다.
    //이렇게 하여  현재 시간이 계속 갱신되어서 보여진다.
    public void ShowTimeMethod() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
               presenttimemath.setText(DateFormat.getTimeInstance().format(new Date()));
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
    }//showtimemethod끝남




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


            //인텐트로  홈키가  눌렷을때  현재  알람 페이지를 다시 실행한다.
            //하지만 다시 실행하면  리셋된 페이지가 나오므로
            //flag activity reorder to front를 사용한다. 이 플래그는 최상단위에서 이미  진행된
            //엑티비티에 경우 그 해당 엑티비티를 재사용한다.
            //이렇게 되면 해결됨.
            //하지만
            Intent i = new Intent(getApplicationContext(), alarming_math.class);
            i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(i);
            Log.i("menu", "진행됬나??");
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
        AlarmReceiver.vibrator.cancel();

    }


    //알람 미루기  메소드이다.
    public void expandAlarm(Context context) {


        //우선 알람이 처음 진행될때  여러  진행 사항들을 취소 시킨다.
        AlarmReceiver.mediaPlayer.stop();
        AlarmReceiver.mediaPlayer.release();
        AlarmReceiver.vibrator.cancel();

        //그다음에  알람을 다시 진행하는데  기존에  설정 했던 알람의 아이디 값을 받아왔으므로 그 아이디값 get_alarm_position을 넣어서//
        //해당 알라믈 불러와 다시  현재시간의 10초 뒤로 바꿔주어  진행하였다.
        //그렇게 되므로 써 해당 알람의  기존 인텐트 값을 유지ㅏ면서 알람의 트리거 시간이 바끼게 된다.
        Intent intent=new Intent(context,AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,get_alarm_position2,intent,0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= 23) {

            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+10000, pendingIntent);

        } else {
            if (Build.VERSION.SDK_INT >= 19) {

                alarmManager.setExact(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis()+10000, pendingIntent);

            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+10000, pendingIntent);
            }
        }




    }

}
