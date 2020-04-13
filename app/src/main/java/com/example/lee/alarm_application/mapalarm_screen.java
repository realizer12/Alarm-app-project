package com.example.lee.alarm_application;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.lee.alarm_application.Myadapter.makedAlarms_arraylist;


/**
 * A simple {@link Fragment} subclass.
 * 맵 알람 설정하는 mapforkillingalarm 액티비티에서
 * 지도가 들어갈 프래그먼트이다.
 */
public class mapalarm_screen extends Fragment implements Serializable {

    TextView myOutput;
    TextView myRec;
    Button myBtnStart;
    Button myBtnRec;
    final static int Init = 0;
    final static int Run = 1;
    final static int Pause = 2;

    int cur_Status = Init; //현재의 상태를 저장할변수를 초기화함.
    int myCount = 1;
    long myBaseTime;
    long myPauseTime;
    TextView report;
    ViewGroup rootView;
    TextView timeout;


    public mapalarm_screen() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_mapalarm_screen, container, false);
        myOutput = (TextView) rootView.findViewById(R.id.time_out);
        myRec = (TextView) rootView.findViewById(R.id.record);
        myBtnStart = (Button) rootView.findViewById(R.id.btn_start);
        myBtnRec = (Button) rootView.findViewById(R.id.btn_rec);
        report = (TextView) rootView.findViewById(R.id.record);
        timeout = (TextView) rootView.findViewById(R.id.time_out);


        //기록을 길게 누르면   공유할수 있도록 만듬
        report.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                Intent msg = new Intent(Intent.ACTION_SEND);
                msg.addCategory(Intent.CATEGORY_DEFAULT);
                msg.putExtra(Intent.EXTRA_SUBJECT, "기록  총시간  " + timeout.getText() + "  \n" + "\n" + "세부기록" + "\n");
                msg.putExtra(Intent.EXTRA_TEXT, " " + report.getText());
                msg.setType("text/plain");
                startActivity(Intent.createChooser(msg, "공유하기"));

                return true;
            }
        });


        myBtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                myOnClick(view);

            }
        });
        myBtnRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myOnClick(view);
            }
        });


        return rootView;
    }



    public void onDestroy() {

        super.onDestroy();
    }




        public void myOnClick(View v) {

            switch (v.getId()) {
                case R.id.btn_start: //시작버튼을 클릭했을때 현재 상태값에 따라 다른 동작을 할수있게끔 구현.
                    switch (cur_Status) {
                        case Init:
                            myBaseTime = SystemClock.elapsedRealtime();
                            System.out.println(myBaseTime);
                            //myTimer이라는 핸들러를 빈 메세지를 보내서 호출
                            myTimer.sendEmptyMessage(0);
                            myBtnStart.setText("멈춤"); //버튼의 문자"시작"을 "멈춤"으로 변경
                            myBtnRec.setEnabled(true); //기록버튼 활성
                            cur_Status = Run; //현재상태를 런상태로 변경


                            break;
                        case Run:

                            myTimer.removeMessages(0); //핸들러 메세지 제거
                            myPauseTime = SystemClock.elapsedRealtime();

                            myBtnStart.setText("시작");
                            myBtnRec.setText("리셋");
                            cur_Status = Pause;
                            break;
                        case Pause:
                            long now = SystemClock.elapsedRealtime();
                            myTimer.sendEmptyMessage(0);
                            myBaseTime += (now - myPauseTime);
                            myBtnStart.setText("멈춤");
                            myBtnRec.setText("기록");
                            cur_Status = Run;
                            break;


                    }
                    break;
                case R.id.btn_rec:
                    switch (cur_Status) {
                        case Run:

                            String str = myRec.getText().toString();
                            str += String.format("%d. %s\n", myCount, getTimeOut());
                            myRec.setText(str);
                            myCount++; //카운트 증가

                            break;
                        case Pause:
                            //핸들러를 멈춤
                            myTimer.removeMessages(0);

                            myBtnStart.setText("시작");
                            myBtnRec.setText("기록");
                            myOutput.setText("00:00:00");

                            cur_Status = Init;
                            myCount = 1;
                            myRec.setText("");
                            myBtnRec.setEnabled(false);
                            break;


                    }
                    break;

            }
        }


        Handler myTimer = new Handler() {
            public void handleMessage(Message msg) {
                myOutput.setText(getTimeOut());

                //sendEmptyMessage 는 비어있는 메세지를 Handler 에게 전송하는겁니다.
                myTimer.sendEmptyMessage(0);
            }
        };


        String getTimeOut() {
            long now = SystemClock.elapsedRealtime(); //애플리케이션이 실행되고나서 실제로 경과된 시간(??)^^;
            long outTime = now - myBaseTime;
            String easy_outTime = String.format("%02d:%02d:%02d", outTime / 1000 / 60, (outTime / 1000) % 60, (outTime % 1000) / 10);
            return easy_outTime;

        }



}
