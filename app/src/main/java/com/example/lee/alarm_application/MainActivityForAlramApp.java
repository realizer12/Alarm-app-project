package com.example.lee.alarm_application;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Project name: alarm_application
 * Class: sub
 * Created by lee.
 * Created On 2018-08-15.
 * Description:알람앱의   맨 처음  시작하는  부분이다.
 *이곳에서  탭 화면이 들어간다. 탭화면에는  알람 화면 1개,  지도를 통한 위치 도달 알람 1개,  설정 1개 총 3개의 탭이 들어간다.
 */
public class MainActivityForAlramApp extends AppCompatActivity {




//changevie(int index)라는 메소드를 만들어
//메인화면에서  준비된 탭들을 눌렀을경우,  스위치 버튼을  이용하여, 스크린이 바뀌게 하기 위한  메소드이다.
//본 메소드를 이용하여  탭을 화면을 구성할것이다.
public void changeview(int index){


    //메인에서  탭버튼들이  눌렸을 경우  글자와 아이콘이 하양색, 그리고 보통의 경우 검정색으로 설정해놓은
    //레이아웃들을  객체로 불러오는 코드이다.

    LinearLayout basicalarm_white=(LinearLayout)findViewById(R.id.basicalarm_white_linear);
    LinearLayout basicalarm_black=(LinearLayout)findViewById(R.id.basicalarm_black_linear);
    LinearLayout mapalarm_white=(LinearLayout)findViewById(R.id.mapalarm_white_linear);
    LinearLayout mapalarm_black=(LinearLayout)findViewById(R.id.mapalarm_black_linear);
    LinearLayout setting_white=(LinearLayout)findViewById(R.id.setting_white_linear);
    LinearLayout setting_black=(LinearLayout)findViewById(R.id.setting_black_linear);


    //프래그먼트으로 구성된 스크린을 불러오는 코드를 불러오기 위해서
    //우선  프래그먼트작동을 위한  코드이다.

    android.support.v4.app.FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();




    switch (index){


        //0을 눌렀을경우에는  기본알람 창 프래그먼트가  메인화면에 나오도록 한다.
        //그밖에  눌리지 않은 탭들은 검정색을 유지하며 눌린 탭은 하얀색을 유지한다.
        case 0:

            basic_alarm_screen  basic_alarm_screenc=new basic_alarm_screen();
            transaction.replace(R.id.frag_container_,basic_alarm_screenc,"basic");
            transaction.commit();

            basicalarm_white.setVisibility(View.VISIBLE);
            basicalarm_black.setVisibility(View.INVISIBLE);
            mapalarm_black.setVisibility(View.VISIBLE);
            mapalarm_white.setVisibility(View.INVISIBLE);
            setting_black.setVisibility(View.VISIBLE);
            setting_white.setVisibility(View.INVISIBLE);

            break;


            //1을 눌렀을 경우에는  맵 알람 창이 화면에 나오도록 한다.
            //그밖에  눌리지 않은 탭들은 검정색을 유지하며 눌린 탭은 하얀색을 유지한다.
        case 1:


            mapalarm_screen mapalarm_screen=new mapalarm_screen();
            transaction.replace(R.id.frag_container_,mapalarm_screen);
            transaction.commit();

            basicalarm_white.setVisibility(View.INVISIBLE);
            basicalarm_black.setVisibility(View.VISIBLE);
            mapalarm_black.setVisibility(View.INVISIBLE);
            mapalarm_white.setVisibility(View.VISIBLE);
            setting_black.setVisibility(View.VISIBLE);
            setting_white.setVisibility(View.INVISIBLE);


            break;

            //2번을 눌렀을 경우에는 세팅창이 화면에 나오도록 한다.
            //그밖에  눌리지 않은 탭들은 검정색을 유지하며 눌린 탭은 하얀색을 유지한다.
        case 2:

           setting_screen  setting_screen=new setting_screen();
            transaction.replace(R.id.frag_container_,setting_screen);
            transaction.commit();

            basicalarm_white.setVisibility(View.INVISIBLE);
            basicalarm_black.setVisibility(View.VISIBLE);
            mapalarm_black.setVisibility(View.VISIBLE);
            mapalarm_white.setVisibility(View.INVISIBLE);
            setting_black.setVisibility(View.INVISIBLE);
            setting_white.setVisibility(View.VISIBLE);

            break;

    }



}





//액티비티가  최초로  작성되는 on create 부분이다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_for_alram_app);
        Log.v("주기", "on create");


        LinearLayout basicalarm_black1=(LinearLayout)findViewById(R.id.basicalarm_black_linear);
        LinearLayout mapalarm_black1=(LinearLayout)findViewById(R.id.mapalarm_black_linear);
        LinearLayout setting_black1=(LinearLayout)findViewById(R.id.setting_black_linear);

        //맨처음에  화면을 시작할때  맨위에만든  changeview()메소드에  인풋값 0을 너어서
        //기본알람 화면의 탭이 눌린 효과 (하얀색 효과) 그리고  해당 기본알람 화면이 화면에 보이도록 설정하였디.
        changeview(0);



      //그뒤부터 원하는 탭을 누르면  위에서 만든 changview메소드를 이용하여 화면 전환 및 탭의  색깔이 바뀌도록 하였다.
     basicalarm_black1.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             changeview(0);
         }
     });
     mapalarm_black1.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             changeview(1);
         }
     });

     setting_black1.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             changeview(2);
         }
     });




    }





    @Override
    protected void onStart() {
        super.onStart();
        Log.v("주기","on start");
    }


    @Override
    protected void onResume() {
        super.onResume();

        Log.v("주기","on resume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.v("주기","on pause");

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("주기","on restart");



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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("basic");
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}
