package com.example.lee.alarm_application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Project name: alarm_application
 * Class: sub
 * Created by lee.
 * Created On 2018-08-15.
 * Description:
 */
public class sub extends AppCompatActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub);
        Toast.makeText(sub.this, "on create", Toast.LENGTH_SHORT).show();
        Log.v("주기1", "on create");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.v("주기1","on start");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.v("주기1","on resume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.v("주기1","on pause");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("주기1","on restart");
    }



    @Override
    protected void onStop() {
        super.onStop();
        Log.v("주기1","stop");
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("주기1","on destroy");
    }

}
