package com.example.lee.alarm_application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Project name: alarm_application
 * Class: WaysForKillingAlarm
 * Created by lee.
 * Created On 2018-08-20.
 * Description:
 * 이곳에서는  알람을 끄는 방법을 고를수 있다.
 */





public class WaysForKillingAlarm extends AppCompatActivity {

    public static Activity WaysForKillingAlarm;

    public void changeview(int index){
        final LinearLayout basic_method_black=(LinearLayout)findViewById(R.id.basic_alarming_unselected);
        final LinearLayout basic_method_white=(LinearLayout)findViewById(R.id.basic_alariming_selected);
        final LinearLayout map_method_black=(LinearLayout)findViewById(R.id.mapalamring_unselected);
        final LinearLayout map_method_white=(LinearLayout)findViewById(R.id.mapalaming_selected);
        final LinearLayout math_solving_black=(LinearLayout)findViewById(R.id.math_solving_unselected);
        final LinearLayout math_solving_white=(LinearLayout)findViewById(R.id.math_solving_selected);
        final LinearLayout take_photo_black=(LinearLayout)findViewById(R.id.take_photo_unselected);
        final LinearLayout take_photo_white=(LinearLayout)findViewById(R.id.take_photo_selected);

        switch(index){

            case 0://기본 알람  검정색 눌렀을 경우
                basic_method_white.setVisibility(View.VISIBLE);
                basic_method_black.setVisibility(View.INVISIBLE);
                map_method_black.setVisibility(View.VISIBLE);
                map_method_white.setVisibility(View.INVISIBLE);
                math_solving_black.setVisibility(View.VISIBLE);
                math_solving_white.setVisibility(View.INVISIBLE);
                take_photo_black.setVisibility(View.VISIBLE);
                take_photo_white.setVisibility(View.INVISIBLE);

                break;

            case 1://맵 알람 검정색일때
                basic_method_white.setVisibility(View.INVISIBLE);
                basic_method_black.setVisibility(View.VISIBLE);
                map_method_black.setVisibility(View.INVISIBLE);
                map_method_white.setVisibility(View.VISIBLE);
                math_solving_black.setVisibility(View.VISIBLE);
                math_solving_white.setVisibility(View.INVISIBLE);
                take_photo_black.setVisibility(View.VISIBLE);
                take_photo_white.setVisibility(View.INVISIBLE);

                break;



            case 2://수학문제 풀이  검정일때
                basic_method_white.setVisibility(View.INVISIBLE);
                basic_method_black.setVisibility(View.VISIBLE);
                map_method_black.setVisibility(View.VISIBLE);
                map_method_white.setVisibility(View.INVISIBLE);
                math_solving_black.setVisibility(View.INVISIBLE);
                math_solving_white.setVisibility(View.VISIBLE);
                take_photo_black.setVisibility(View.VISIBLE);
                take_photo_white.setVisibility(View.INVISIBLE);






                break;



            case 3://사진찍기 검정일때
                basic_method_white.setVisibility(View.INVISIBLE);
                basic_method_black.setVisibility(View.VISIBLE);
                map_method_black.setVisibility(View.VISIBLE);
                map_method_white.setVisibility(View.INVISIBLE);
                math_solving_black.setVisibility(View.VISIBLE);
                math_solving_white.setVisibility(View.INVISIBLE);
                take_photo_black.setVisibility(View.INVISIBLE);
                take_photo_white.setVisibility(View.VISIBLE);


                break;


        }
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ways_for_killing_alarm);

        WaysForKillingAlarm=(com.example.lee.alarm_application.WaysForKillingAlarm.this);



        final LinearLayout basic_method_black=(LinearLayout)findViewById(R.id.basic_alarming_unselected);
        final LinearLayout basic_method_white=(LinearLayout)findViewById(R.id.basic_alariming_selected);
        final LinearLayout map_method_black=(LinearLayout)findViewById(R.id.mapalamring_unselected);
        final LinearLayout map_method_white=(LinearLayout)findViewById(R.id.mapalaming_selected);
        final LinearLayout math_solving_black=(LinearLayout)findViewById(R.id.math_solving_unselected);
        final LinearLayout math_solving_white=(LinearLayout)findViewById(R.id.math_solving_selected);
        final LinearLayout take_photo_black=(LinearLayout)findViewById(R.id.take_photo_unselected);
        final LinearLayout take_photo_white=(LinearLayout)findViewById(R.id.take_photo_selected);

        basic_method_black.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changeview(0);
                Intent intent=new Intent();
                String a="기본알람";


                //기본알람을 골랐을때는 리절트 값을  100으로 주었다.
                intent.putExtra("기본알람선택",a);
                setResult(100,intent);

                finish();
            }
        });


        //맵 알람을 골랐을때이다.
        map_method_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeview(1);
                Intent intent=new Intent(getApplicationContext(),MapForKillingAlarm.class);

                //아래 플래그는 앞선 액티비티에서  startforactivityresult로  이 액티비티를 소환하였는데, 다시 그 결과 값을  또다른 액티비티에서 받아와야할경우,  이렇게  플래그를 사용ㅇ하고 다른 액티비티를 소환하여
                //그곳에서  결과 값을 보낼수 있도록한다.
                intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(intent);

            }
        });
        //맵알람에서  취소를 누르면 다시  방법을 고르는 화면이 나오도록 하는데 여기서,  하얀색으로 체크가 되있는 상태이므로
        //이때는 하얀색을 클릭할때  다시 맵알람 설정 액티비티가 띄어지도록 설정하였따.
        map_method_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeview(1);
                Intent intent=new Intent(getApplicationContext(),MapForKillingAlarm.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(intent);


            }
        });






        math_solving_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeview(2);
                Intent intent=new Intent(getApplicationContext(),MathForKillAlarm.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(intent);
            }
        });
        math_solving_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeview(2);
                Intent intent=new Intent(getApplicationContext(),MathForKillAlarm.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(intent);
            }
        });






// 사진찍기 미션을 골랐을때이다.
        take_photo_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeview(3);
                Intent intent=new Intent(getApplicationContext(),TakePhotoForKillingAlarm.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(intent);
            }
        });
        take_photo_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeview(3);
                Intent intent=new Intent(getApplicationContext(),TakePhotoForKillingAlarm.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(intent);
            }
        });

    }//on create 닫힘.


    @Override
    protected void onStart() {
        super.onStart();
        Log.v("킬링","on start");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.v("킬링","on resume");


    }





    @Override
    protected void onPause() {
        super.onPause();
        Log.v("킬링","on pause");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("킬링","on restart");
    }



    @Override
    protected void onStop() {
        super.onStop();
        Log.v("주기","stop");
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("주기","on destroy");
    }





}