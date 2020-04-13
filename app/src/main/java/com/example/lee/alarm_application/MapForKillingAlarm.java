package com.example.lee.alarm_application;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Project name: alarm_application
 * Class: MapForKillingAlarm
 * Created by lee.
 * Created On 2018-08-21.
 * Description:
 */
public class MapForKillingAlarm extends AppCompatActivity implements mapviewfragement.OnMyListener,mapviewfragement.OnMyListner1{
public static double lan;
public static double lon;

    @Override
    public void onReceivedData(double data) {

        lan=data;


    }
    @Override
    public void onReceiveData1(double data) {
        lon=data;
        TextView showloaction=(TextView)findViewById(R.id.show_loaction);
        showloaction.setText(""+lan+"  ,  "+lon);
    }
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_for_kill_alarm);



        android.support.v4.app.FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        mapviewfragement mainFragment = new mapviewfragement();
        transaction.replace(R.id.mapcontainer,mainFragment);
        transaction.commit();




        //설정을 하고  설정완료 버튼을 눌렀을때  처음  알람 설정  라인으로  정보를 보내고  alarm setting 화면으로 가진다.

        Button select_finish=(Button)findViewById(R.id.finish_to_select_map);
        select_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Intent click_MAP=new Intent();
                String a="위치 알람";


                //기본알람을 골랐을때는 리절트 값을  100으로 주었다.
                click_MAP.putExtra("위치알람선택",a);
                click_MAP.putExtra("위치알람위도",lan);
                click_MAP.putExtra("위치알람경도",lon);
                setResult(120,click_MAP);

                //설정 완료 버튼을 눌렀을때, 취소 버튼과 는 다른게  맨처음  액티비티로 돌아가기 위해서는 중간 액티비티를 죽여야만 한다/
                //그러므로  중간액티비티를 객체로 받아와서  현재 액티비티에서 완료 버튼을 누를시  종하게 만들어준다.
                WaysForKillingAlarm waysForKillingAlarm=(WaysForKillingAlarm)WaysForKillingAlarm.WaysForKillingAlarm;
                waysForKillingAlarm.finish();

                finish();
            }
        });





    }//on create  닿힘.

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }



}
