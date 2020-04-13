package com.example.lee.alarm_application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Project name: alarm_application
 * Class: Loading_Screen
 * Created by lee.
 * Created On 2018-08-17.
 * Description: 앱이 시작할때  제일 먼저 나오는 로딩 스크린이다.
 * 앱이 시작하고 메인화면이 구성되기 까지 cold start 기간동안
 * 준비한  로딩화면이  나오도록  함.
 */
public class Loading_Screen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //로딩 화면에 경우  인텐트를 통해  메인 엑티비티를 불러와  메인 엑티비티를 시작할때까지  나온다.
        //보통 앱을 시작할때  레이아웃이 화면에 나올때까지 시간이 걸리므로  바로 나올수 있는 로딩화면을 나오게하여
        //그뒤에 메인 엑티비티가 나옰 있도록 시간을 버는것이다.
        Intent intent = new Intent(this, MainActivityForAlramApp.class);
        startActivity(intent);

        //메인이 나왔을 경우에는  본엑티비티는 피니쉬 함수를 주어  더이상  나오지 않게 한다.
        finish();
    }
}



