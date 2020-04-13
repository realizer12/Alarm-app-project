package com.example.lee.alarm_application;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Project name: alarm_application
 * Class: MathForKillAlarm
 * Created by lee.
 * Created On 2018-08-22.
 * Description:
 */
public class MathForKillAlarm extends AppCompatActivity {
    int position1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.math_q_for_killing_alarm);
        final Spinner spinner=(Spinner)findViewById(R.id.spinner);
        Button select_finish_math=(Button)findViewById(R.id.finish_to_select_math);
        select_finish_math.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent click_MATH=new Intent();
                String a="수학문제  ";


                //기본알람을 골랐을때는 리절트 값을  100으로 주었다.
                click_MATH.putExtra("수학문제풀기",a);
                click_MATH.putExtra("spinner",position1);
                setResult(123,click_MATH);

                //설정 완료 버튼을 눌렀을때, 취소 버튼과 는 다른게  맨처음  액티비티로 돌아가기 위해서는 중간 액티비티를 죽여야만 한다/
                //그러므로  중간액티비티를 객체로 받아와서  현재 액티비티에서 완료 버튼을 누를시  종하게 만들어준다.
                WaysForKillingAlarm waysForKillingAlarm=(WaysForKillingAlarm)WaysForKillingAlarm.WaysForKillingAlarm;
                waysForKillingAlarm.finish();

                finish();



                //기본알람을 골랐을때는 리절트 값을  100으로 주었다.



            }
        });


       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {



           @Override
           public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                       //position 1은 인텐트로 보내질 값이고, 스피너 리스너로 와서  스피너 포지션을 가져간다.
                       position1=position;

                       //토스트메세지에 들어갈 문제 난이도 문자화하는 코드
                        String level="";
                       if(position==0){
                           level="쉬움";
                        }else  if(position==1){
                           level="중간";
                       }else if(position==2){
                           level="어려움";
                       }
                       //Toast.makeText(getApplicationContext(),"선택된 문제 난이도 : "+level+"",Toast.LENGTH_SHORT).show();







           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });





    }
}
