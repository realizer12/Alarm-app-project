package com.example.lee.alarm_application;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * alarm_application
 * Class: ChangeMakedAlarm.
 * Created by leedonghun.
 * Created On 2018-08-27.
 * Description:설정하여서 이미 추가된 알람을 다시 수정하기 위해 들어가는 수정 창이다.
 */
public class ChangeMakedAlarm extends AppCompatActivity {


    boolean mon;
    boolean tue;
    boolean wed;
    boolean thr;
    boolean fri;
    boolean sat;
    boolean sun;

    String monday_text;
    String tuseday_text;
    String wednesday_text;
    String thursday_text;
    String friday_text;
    String saturday_text;
    String sunday_text;

    //요일이 선택된 경우 나오는 주황색 텍스트
   TextView monday_press;
   TextView tuseday_press;
   TextView wednesday_press;
   TextView thursday_press;
   TextView friday_press;
   TextView saturday_press;
   TextView sunday_press;

   //요일이 선택되지 않은 경우 나오는 회색 텍스트
    TextView monday_unpress;
    TextView tuseday_unpress;
    TextView wednesday_unpress;
    TextView thursday_unpress;
    TextView friday_unpress;
    TextView saturday_unpress;
    TextView sunday_unpress;

    String barcodenumber55;
    String barcodephoto55;

    //위도 경도
    double latitudeinchange;
    double longtitudeinchange;


    boolean usingspecificdate;
    ArrayList<MakedAlarm> makedAlarms_arraylist;
    TextView time;
    TextView date1;
    Intent intent2;
    EditText memoline;
    Button change_suceed;
    TextView killing_way_line;
    String wayforkill;
    //수정된거 다시 보낼때 사용
    boolean alarmpush;
    boolean vibration;

    int hour;
    int minute1;


    TextView alarm_sound_text;
    String alarmsoundtitle;
    long mymusic_alarm_id;
    String mymusic_alarm_path;

    //수정된거 다시 보낼때사용


    int year_change;
    int month_change;
    int day_change;
    boolean specificdate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_maked_alarm);
           memoline=(EditText)findViewById(R.id.make_text_for_write_message_change);
           change_suceed=(Button)findViewById(R.id.fix_button);
           time=(TextView)findViewById(R.id.text_for_showing_maked_alarm_time_change);
           killing_way_line=(TextView)findViewById(R.id.text_for_make_mission_to_clear_alarm_change);
           final TextView unselectespecificdate=(TextView)findViewById(R.id.unmaked_specific_date_text_change);
           final TextView selectedspecificdate=(TextView)findViewById(R.id.maked_specific_date_text_change);
           final LinearLayout showselectingweekday=(LinearLayout)findViewById(R.id.linear_make_each_day_for_alarming_change);
           final TextView showspecificdate=(TextView)findViewById(R.id.maked_textview_for_show_specific_alarming_date_change);

          //알람 미루기 를 설정할수 있다.  미루기를 누르면 인비져블로 되어있던 미루기 설정  주황색 텍스트뷰가보이고
          //회색으로 설정되었던 미루지 않은 텍스트는 인비져블 상태로 된다.
          final TextView using_alarm_push=(TextView)findViewById(R.id.make_using_alarmpush_change);
          final TextView unusing_alarm_push=(TextView)findViewById(R.id.make_unusing_alarmpush_change);


          //알람이 울릴때 진동여부를  설정 한다.
          //진동 선택 여부에 따라 해당 리니어가  인비져블 또는 비져블 된다.
          final LinearLayout using_vibration=(LinearLayout)findViewById(R.id.make_using_vibration_icon_linear_change);
          final LinearLayout unusing_vibration=(LinearLayout)findViewById(R.id.make_unusing_vibration_icon_linear_change);

          //수정창내에서도 알람설정창과 마찬가지로 요일 클릭여부를 비져블 인비져블을 사용하여 보여줘야하기 때문에
          //해당 텍스 객체를 가지고 왔다.
          //클릭된 텍스트뷰 (주황)
          monday_press=(TextView)findViewById(R.id.make_monday_pressed_text_change);
          tuseday_press=(TextView)findViewById(R.id.make_tuesday_pressed_text_change);
          wednesday_press=(TextView)findViewById(R.id.make_wednesday_pressed_text_change);
          thursday_press=(TextView)findViewById(R.id.make_thursday_pressed_text_change);
          friday_press=(TextView)findViewById(R.id.make_friday_pressed_text_change);
          saturday_press=(TextView)findViewById(R.id.make_saturday_pressed_text_change);
          sunday_press=(TextView)findViewById(R.id.make_sunday_pressed_text_change);

          //안클릭된 텍스트뷰 (회색)
          monday_unpress=(TextView)findViewById(R.id.make_monday_unpressed_text_change);
          tuseday_unpress=(TextView)findViewById(R.id.make_tuesday_unpressed_text_change);
          wednesday_unpress=(TextView)findViewById(R.id.make_wednesday_unpressed_text_change);
          thursday_unpress=(TextView)findViewById(R.id.make_thursday_unpressed_text_change);
          friday_unpress=(TextView)findViewById(R.id.make_friday_unpressed_text_change);
          saturday_unpress=(TextView)findViewById(R.id.make_saturday_unpressed_text_change);
          sunday_unpress=(TextView)findViewById(R.id.make_sunday_unpressed_text_change);

          alarm_sound_text=(TextView)findViewById(R.id.text_for_make_alarm_sound_change);






          //알람을 수정하는 창에서 취소 버튼을 누르면 액티비티를 finish()하게 했다.
        final Button cancle=(Button)findViewById(R.id.make_cancel_button_change);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //리사이클러뷰 아이템을 눌렀을 경우 수정창으로 가지므로  어댑터에서  인텐트로 어레이리스트 값을 보냈고,
        //그 보낸 값들을  아래와 같이 수정 액티비티로 가지고왔다.
        intent2=getIntent();

        latitudeinchange=intent2.getDoubleExtra("latitude_tochange",0.0);
        longtitudeinchange=intent2.getDoubleExtra("longtitude_tochange",0.0);
        hour=intent2.getIntExtra("hour",99);
        minute1=intent2.getIntExtra("minute",99);
        String memo_change= (String) intent2.getExtras().get("memo");
        wayforkill=(String) intent2.getExtras().get("wayforkill");
        boolean alarmpushed_change=(boolean)intent2.getExtras().get("alarmpushed") ;
        boolean alarm_vibration_change=(boolean)intent2.getExtras().get("alarmvibration") ;
        year_change= (int) intent2.getExtras().get("year");
        month_change=(int) intent2.getExtras().get("month");
        day_change=(int)intent2.getExtras().get("day");
        usingspecificdate=(boolean)intent2.getExtras().get("specificdate");
        mon=(boolean)intent2.getExtras().get("mon");
        tue=(boolean)intent2.getExtras().get("tue");
        wed=(boolean)intent2.getExtras().get("wed");
        thr=(boolean)intent2.getExtras().get("thur");
        fri=(boolean)intent2.getExtras().get("fri");
        sat=(boolean)intent2.getExtras().get("sat");
        sun=(boolean)intent2.getExtras().get("sun");
        monday_text=(String) intent2.getExtras().get("monday_text");
        tuseday_text=(String)intent2.getExtras().get("tuseday_text");
        wednesday_text=(String)intent2.getExtras().get("wednesday_text");
        thursday_text=(String)intent2.getExtras().get("thursday_text");
        friday_text=(String)intent2.getExtras().get("friday_text");
        saturday_text=(String)intent2.getExtras().get("saturday_text");
        sunday_text=(String)intent2.getExtras().get("sunday_text");
        alarmsoundtitle=(String)intent2.getExtras().get("alarm_sound_title");
        mymusic_alarm_id=(long)intent2.getExtras().get("mymusic_alarm");
        mymusic_alarm_path=(String)intent2.getExtras().get("mymusic_path");
        barcodenumber55=(String)intent2.getExtras().get("barcodenumber");
        barcodephoto55=(String)intent2.getExtras().get("barcodephoto");

        //수정 액티비티에서 보여질 어레이부터 저장된 시간의 텍스트 설정할때,
        //오전오후를 나누어 보이기 위한 코드
        String 오전오후 = "";
        int showhour;
        showhour=hour;
        if(hour>=12){
            오전오후="오후";
            if(hour>12) {
                showhour= hour - 12;
            }
        }else if(hour<12){
            오전오후="오전";
        }
       //------------------------------------오전오후 나누기


        //수정되기전에  어레이리스트에 저장된  받아온 값으로  처음 화면의 보여주는 값 처리
        //현재 지정된 알람시간  수정창에 나타냄
        time.setText(""+오전오후+"  "+showhour+"시 "+minute1+"분"+"");

        //현재 지정된 메모내용  수정창에 나타냄
        memoline.setText(memo_change);

        //현재 지정되  알람 종료 방법  수정창에 나타냄
        killing_way_line.setText(wayforkill);



       //현재 지정된 알람  소리 제목이  뜬다.
        alarm_sound_text.setText(alarmsoundtitle);




        //특정날짜 고를때 사용하는 경우,
        //우선 특정날짜 지정 버튼이 투르인지 false 인지 여부부터 알아내고 비져블 여부를 설정한다.

        if( usingspecificdate==true){
            unselectespecificdate.setVisibility(View.INVISIBLE);
            selectedspecificdate.setVisibility(View.VISIBLE);
            showselectingweekday.setVisibility(View.INVISIBLE);
            showspecificdate.setVisibility(View.VISIBLE);
            showspecificdate.setText(""+year_change+". "+(month_change+1)+". "+day_change+". 알람울림          *수정시 터치*");



            //특정날짜 지정이  안되어있을 경우에  요일 셀렉팅하는 칸이 보이게됨.
        }else if( usingspecificdate==false){
            unselectespecificdate.setVisibility(View.VISIBLE);
            selectedspecificdate.setVisibility(View.INVISIBLE);
            showselectingweekday.setVisibility(View.VISIBLE);
            showspecificdate.setVisibility(View.INVISIBLE);




            //특정날짜 지정이 안되어있는 경우에는
            //어댑터에서 getintent로 받아온 요일 체크 여부를 사용하여, 수정창에서 처음들어올때 해당 데이터 값대로
            //체크여부가 보이도록 설정한다.
            //월요일경우
            if(mon==true){
                monday_press.setVisibility(View.VISIBLE);
                monday_unpress.setVisibility(View.INVISIBLE);

            }else if(mon==false){
                monday_press.setVisibility(View.INVISIBLE);
                monday_unpress.setVisibility(View.VISIBLE);
            }

            //화요일경우
            if(tue==true){
                tuseday_press.setVisibility(View.VISIBLE);
                tuseday_unpress.setVisibility(View.INVISIBLE);
            }else if(tue==false){
                tuseday_press.setVisibility(View.INVISIBLE);
                tuseday_unpress.setVisibility(View.VISIBLE);
            }

            //수요일경우
            if(wed==true){
                wednesday_press.setVisibility(View.VISIBLE);
                wednesday_unpress.setVisibility(View.INVISIBLE);

            }else if(wed==false){
                wednesday_press.setVisibility(View.INVISIBLE);
                wednesday_unpress.setVisibility(View.VISIBLE);
            }

            //목요일경우
            if(thr==true){
                thursday_press.setVisibility(View.VISIBLE);
                thursday_unpress.setVisibility(View.INVISIBLE);
            }else if(thr==false){
                thursday_press.setVisibility(View.INVISIBLE);
                thursday_unpress.setVisibility(View.VISIBLE);
            }

            //금요일 경우
            if(fri==true){
                friday_press.setVisibility(View.VISIBLE);
                friday_unpress.setVisibility(View.INVISIBLE);

            }else if(fri==false){
                friday_press.setVisibility(View.INVISIBLE);
                friday_unpress.setVisibility(View.VISIBLE);
            }

            //토요일 경우
            if(sat==true){
                saturday_press.setVisibility(View.VISIBLE);
                saturday_unpress.setVisibility(View.INVISIBLE);
            }else if(sat==false){
                saturday_press.setVisibility(View.INVISIBLE);
                saturday_unpress.setVisibility(View.VISIBLE);
            }

            //일요일 경우
            if(sun==true){
               sunday_press.setVisibility(View.VISIBLE);
               sunday_unpress.setVisibility(View.INVISIBLE);
            }else if(sun==false){
                sunday_press.setVisibility(View.INVISIBLE);
                sunday_unpress.setVisibility(View.VISIBLE);
            }




        }





        //어레이리스트값을 여기에 지정한 알람푸시값에 사작할때 넣어주어야지 변경없이 수정버튼을 눌러도 값이 그대로 유지할수 있다.
        //이렇게 안하면 받아온 값이 수정버튼을 눌러 갈때 default 값으로 가지기 때무에 false로 뜨게 된다.
        //위에서는 텍스트값에 넣는 거여서 연결이 되었지만
        //이경우에는 택스트값이 아니라서 헷갈렸음.
        alarmpush=alarmpushed_change;
        //현재 지정된 알람 미루기 여부
        if(alarmpush==true){
            using_alarm_push.setVisibility(View.VISIBLE);
            unusing_alarm_push.setVisibility(View.INVISIBLE);
        }else if(alarmpush==false){
            using_alarm_push.setVisibility(View.INVISIBLE);
            unusing_alarm_push.setVisibility(View.VISIBLE);
        }


        //어레이리스트값을 여기에 지정한 알람푸시값에 사작할때 넣어주어야지 변경없이 수정버튼을 눌러도 값이 그대로 유지할수 있다.
        //이렇게 안하면 받아온 값이 수정버튼을 눌러 갈때 default 값으로 가지기 때무에 false로 뜨게 된다.
        vibration=alarm_vibration_change;
        //현재 지정된 알람 진동여부
        if(alarm_vibration_change==true){
            using_vibration.setVisibility(View.VISIBLE);
            unusing_vibration.setVisibility(View.INVISIBLE);
        }else if(alarm_vibration_change==false){
            using_vibration.setVisibility(View.INVISIBLE);
            unusing_vibration.setVisibility(View.VISIBLE);
        }






        //완료 버튼 누르고 수정된 값 다시 보낼때 사용.
        change_suceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changed=new Intent();
                changed.putExtra("changed_day2",day_change);
                changed.putExtra("changed_month2",month_change);
                changed.putExtra("changed_year2",year_change);
                changed.putExtra("changed_specific_date_boolean",usingspecificdate);
                changed.putExtra("changed_killingway",wayforkill);
                changed.putExtra("changed_minute",minute1);
                changed.putExtra("chaged_time",hour);
                changed.putExtra("changed_vibration",vibration);
                changed.putExtra("changed_push",alarmpush);
                changed.putExtra("changed_memo",memoline.getText().toString());
                changed.putExtra("changed_monday",mon);
                changed.putExtra("changed_tuseday",tue);
                changed.putExtra("changed_wednesday",wed);
                changed.putExtra("changed_thursday",thr);
                changed.putExtra("changed_friday",fri);
                changed.putExtra("changed_saturday",sat);
                changed.putExtra("changed_sunday",sun);
                changed.putExtra("changed_mon_text",monday_text);
                changed.putExtra("changed_tue_text",tuseday_text);
                changed.putExtra("changed_wed_text",wednesday_text);
                changed.putExtra("changed_thur_text",thursday_text);
                changed.putExtra("changed_fri_text",friday_text);
                changed.putExtra("changed_sat_text",saturday_text);
                changed.putExtra("changed_sun_text",sunday_text);
                changed.putExtra("changed_alarm_title", alarmsoundtitle);
                changed.putExtra("changed_mymusic_alarm_id",mymusic_alarm_id);
                changed.putExtra("changed_mymusic_alarm_path",mymusic_alarm_path);
                changed.putExtra("changed_barcodenumber",barcodenumber55);
                changed.putExtra("changed_barcodephoto",barcodephoto55);

                changed.putExtra("changed_latitude",latitudeinchange);
                changed.putExtra("changed_lontitude",longtitudeinchange);

                setResult(95,changed);
                finish();
            }
        });



                                                          //위쪽은   취소 완료 버튼시 행동과 수정 하였을때 값들을 인탠트애넣고,/ 원래 기존 수정전 값들을 받아올때 들어가는 코드
//--------------------------------------------------------여기 아래부터는 데이터들을 수정하는데 사용한다.

       //알람에서 요일을  선택하거나 선택해제할수 있도록 제어하는 코드이다.

        //각 요일별로 원하는 요일을 고르면  주황색으로 텍스트 칸이 변하고 원하지 않으면
        //해당 주항색텍스트칸을 다시 누르면 다시 돌아가게 만드는 코드이다.
        //이경우에는 선택의 대한 true, false값이  선택한 경우에만 적용되는 것이다.
        //그냥 아무 터치도 안하고 맨처음 false or true 값일 경우에는 텍스트 보이는 값이 다르다는것을 보여주는 코드들 맨위에 작성함.
        monday_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                monday_unpress.setVisibility(View.VISIBLE);
                monday_press.setVisibility(View.INVISIBLE);

                mon=false;
                monday_text="";

            }
        });
        monday_unpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monday_unpress.setVisibility(View.INVISIBLE);
                monday_press.setVisibility(View.VISIBLE);
                monday_text="월";
                mon=true;


            }
        });

        tuseday_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tuseday_press.setVisibility(View.INVISIBLE);
                tuseday_unpress.setVisibility(View.VISIBLE);
                tuseday_text="";
                tue=false;


            }
        });
        tuseday_unpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tuseday_press.setVisibility(View.VISIBLE);
                tuseday_unpress.setVisibility(View.INVISIBLE);
                tuseday_text="화";
                tue=true;


            }
        });
        wednesday_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wednesday_press.setVisibility(View.INVISIBLE);
                wednesday_unpress.setVisibility(View.VISIBLE);
                wednesday_text="";
                wed=false;


            }
        });
        wednesday_unpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wednesday_press.setVisibility(View.VISIBLE);
                wednesday_unpress.setVisibility(View.INVISIBLE);
                wednesday_text="수";
                wed=true;

            }
        });
        thursday_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thursday_press.setVisibility(View.INVISIBLE);
                thursday_unpress.setVisibility(View.VISIBLE);
                thursday_text="";
                thr=false;

            }
        });

        thursday_unpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thursday_press.setVisibility(View.VISIBLE);
                thursday_unpress.setVisibility(View.INVISIBLE);
                thursday_text="목";
                thr=true;


            }
        });

        friday_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friday_press.setVisibility(View.INVISIBLE);
                friday_unpress.setVisibility(View.VISIBLE);
                friday_text="";
                fri=false;


            }
        });

        friday_unpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friday_press.setVisibility(View.VISIBLE);
                friday_unpress.setVisibility(View.INVISIBLE);
                friday_text="금";
                fri=true;


            }
        });

        saturday_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saturday_press.setVisibility(View.INVISIBLE);
                saturday_unpress.setVisibility(View.VISIBLE);
                saturday_text="";
                sat=false;


            }
        });

        saturday_unpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saturday_press.setVisibility(View.VISIBLE);
                saturday_unpress.setVisibility(View.INVISIBLE);
                saturday_text="토";
                sat=true;


            }
        });

        sunday_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sunday_press.setVisibility(View.INVISIBLE);
                sunday_unpress.setVisibility(View.VISIBLE);
                sunday_text="";
                sun=false;

            }
        });

        sunday_unpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sunday_press.setVisibility(View.VISIBLE);
                sunday_unpress.setVisibility(View.INVISIBLE);
                sunday_text="일";
                sun=true;


            }
        });
        //여기까지 요일 버튼으로  요일을 설정하는 코드







        //알람 미루기기능을 취소할때
        using_alarm_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                using_alarm_push.setVisibility(View.INVISIBLE);
                unusing_alarm_push.setVisibility(View.VISIBLE);
                alarmpush=false;
            }
        });


        //알람 미루기 기능 사용할때
        unusing_alarm_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                using_alarm_push.setVisibility(View.VISIBLE);
                unusing_alarm_push.setVisibility(View.INVISIBLE);
                alarmpush=true;
            }
        });




        //진동 기능 사용할때
        unusing_vibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                using_vibration.setVisibility(View.VISIBLE);
                unusing_vibration.setVisibility(View.INVISIBLE);
                vibration=true;
            }
        });

        //진동 기능 사용안할때
        using_vibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                using_vibration.setVisibility(View.INVISIBLE);
                unusing_vibration.setVisibility(View.VISIBLE);
                vibration=false;
            }
        });



        //타임과  데이트 를  고르는  다이얼로그를  눌렀을때 어레이리스트에서 받아온 시간 및 분을
        //보여주기위해서  캘린터 메소드 안에 어레이리스트에서 가져온 데이터 객체 값을 넣었다.




        //알람 시간을 설정하기 위해서  타임피커 다이얼로그를  가지고옴.
        //콘텍스트는 settingbasicalarm 액티비티이므로 this, 타임피커 다이얼로그가  실행해보니 스피너 형태가 아니어서  테마를 따로 설정함.,
        // 실행당시  현재시간의 -9의 시간으로 나와서 +9를 해놓음
        final TimePickerDialog timePickerDialog= new TimePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog,listner_time,hour,minute1,false);


        //알람시간 설정 버튼을 누렀을때 진행되는 클릭리스너와 해당 버튼 객체 선언.
        Button select_alarming_time=(Button)findViewById(R.id.button_for_make_alarming_time_change);
        //시간 설정 버튼을 누르면   타임피커 다이얼로그가  show 되도록  설정하였다.
        select_alarming_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePickerDialog.show();
            }
        });//알람시간 설정 버튼  닫힘.



        //알람 해제방법을  고르기위한  액티비티로 가는  코드
        //역시나 결과 값을 가져오기 위한  형태로 사용하므로 start activityforresult를 사용하였다.
        TextView select_mission=(TextView)findViewById(R.id.text_for_make_mission_to_clear_alarm_change);
        select_mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call_select_mission_activity= new Intent(getApplicationContext(),WaysForKillingAlarm.class);
                startActivityForResult(call_select_mission_activity,12);
            }
        });




        //알람 사운드를  고르기위한 액티비티로가는 코드
        //역시나 결과 값을 가져오기 위한 형태로 사용하므로 startactivityforresult를 사용하였다,
        alarm_sound_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent call_select_alarm_sound_activity=new Intent(getApplicationContext(),AlarmSoundList.class);
                startActivityForResult(call_select_alarm_sound_activity,117);

            }
        });

//---------------------------------------------------------------------------------------------------------------알람 데이트 픽커



        final Calendar calendar = Calendar.getInstance();

        final int year2=calendar.get(calendar.YEAR);//현재  년도
        final int month2=calendar.get(calendar.MONTH);//현재 달
        final int day2=calendar.get(calendar.DAY_OF_MONTH);//현재 일



        //만약에 알람 반복되는 날을  특정날짜로 정하고 싶을때  사용하는 다이얼로그이다
        //알람이 울림 날짜를  선택한다.

        if(year_change==0){
            year_change=year2;
        }else if(!(year_change==0)){

        }

         if(month_change==0){
            month_change=month2;
        }else if(!(month_change==0)){

         }

         if(day_change==0){
            day_change=day2;
        }else if(!(day_change==0)){

         }

        final DatePickerDialog datePickerDialog=new DatePickerDialog(this,listener_date1,year_change,month_change,day_change);

        //텍스트 중에 특정 알람날짜 미지정을  clickable로 바꾸어서
        //클릭하면  date picker dialog가  나오도록 하는 코드이다.

        //알람날짜 미지정 텍스트를 누르면 show(); 나옴.
         unselectespecificdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        //특정요일 설정한 값을 다시 수정하고 싶을때는 날짜가 보이는 텍스트를 다시 누르면 달력이 나온다.
        //처음에 설정한 날짜에 찍혀있으면 원하는대로 바꿀수 있다.
        showspecificdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datePickerDialog.show();

            }
        });



        //특정날짜  알람이 지정이 되어있을때
        //지정되어있을을 알리는 주황색 텍스트뷰를 누르면  원래 미지정 텍스트가 나오고,
        //요일을 지정하는 텍슽트가  나오고 아까 데이트 타임은  리셋되도록 설정하였다.
        selectedspecificdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselectespecificdate.setVisibility(View.VISIBLE);
                selectedspecificdate.setVisibility(View.INVISIBLE);
                showspecificdate.setVisibility(View.INVISIBLE);
                showselectingweekday.setVisibility(View.VISIBLE);
                datePickerDialog.updateDate(year2, month2, day2);

                //아래부분은 혹시나 클릭을해서 날짜를 고르고 그다음에 다시 취소를 하고 수정을 하였을경우에
                //그 해당 값들이 저장될때 지정한 값들을 리셋시킨것이므로 다시 해당 날짜가 들어가게 지정하였다.
                month_change=month2;
                year_change=year2;
                day_change=day2;
                showspecificdate.setText("");
                usingspecificdate=false;
            }
        });





    }//oncreate 닫힘


    String 오후오전;

    int hourday1;

    //타임피커다이얼로그의  리스너이다.
    //이곳에서는 타임피커 설정 확인을 눌렀을때 진행되는 상황이 들어간다.
    //우선, 토스트로  현재 정해진 시간을  띄우도록 하였다.
    private TimePickerDialog.OnTimeSetListener listner_time=new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //리스너를 통해  hourofday를 출력하면  오전 오후 상관 없이   24시간으로 나와있다.
            //이를 오전 오후 로  표현하기 위해서 아래와 같은 코드를 만듬.
            //오후 12시 이상붙터는 오후라는  스트링값을 주고, 13시 이후부터는  시간-12를 하여 1시,2시 등으로  표현하게함.
            //그외 오후 12시 이전은  오전이라는 스트링값을 주었다.
            hour = hourOfDay;//어레이리스트로 보내기 위해서 넣음
            minute1=minute;
            //이곳에서 고른 시간을 보여줄때 사용하는 객체
            hourday1=hourOfDay;

            오후오전="";
            if(hourOfDay>=12){
                오후오전="오후";
                if(hourOfDay>12) {
                    hourday1= hourOfDay - 12;
                }
            }else if(hourOfDay<12){
                오후오전="오전";

            }

            //아래는 리스너를 통해 출력되는 값들이다.
            //텍스트 뷰중에 알람 지정 시간을 보여주는 텍스트를  객체로 선언후,  그곳에  위에서 조정한 시간 코드를 이용하여,  알람 설정 시간을 나타내었다.
            //또한 토스트 메세지를 사용하여 토스트로도 설정한 시간을 한번더  보이게 하였다.
            TextView show_alarm_time_text=(TextView)findViewById(R.id.text_for_showing_maked_alarm_time_change);
            show_alarm_time_text.setText(""+오후오전+"  "+ hourday1 + "시 " + minute + "분"+"");
            Toast.makeText(getApplicationContext(), "알람시간은 "+오후오전+"  "+ hourday1 + "시 " + minute + "분"+" 입니다.", Toast.LENGTH_SHORT).show();


        }
    }; //타임피커다이얼로그의  리스너 메소드 닫힘.





    //Startactivityror result의 값을 받아올때 사용하는 코드이다.
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView select_mission=(TextView)findViewById(R.id.text_for_make_mission_to_clear_alarm_change);
        if(requestCode==12){

            //waysforKillingAlarm에서  기본 알람 눌렀을 경우 리절트코드를 100으로 설정하였기 때문에
            //해당값을 받기 위해서 100을 집어 넣었다.

            if(resultCode==100){
                String  basic_result=data.getStringExtra("기본알람선택");
                wayforkill=basic_result;
                select_mission.setText(basic_result);
            }//기본알람 값
            if(resultCode==120){
                String map_result=data.getStringExtra("위치알람선택");
                double lati_result=data.getDoubleExtra("위치알람위도",0.0);
                double lonti_result=data.getDoubleExtra("위치알람경도",0.0);

                //위도값
                latitudeinchange=lati_result;
                //경도값
                longtitudeinchange=lonti_result;
                wayforkill=map_result;
                select_mission.setText(map_result);
            }
            if(resultCode==123){
                String math_result=data.getStringExtra("수학문제풀기");
                int spinner=data.getIntExtra("spinner",5);

                //스피너 포지션값을 이용하여 난이도의 한글화를 하였다.
                String level="";
                if(spinner==0) {
                    level = "쉬움";
                }else if(spinner==1) {
                    level = "중간";
                } else if (spinner==2){
                    level="어려움";
                }

                wayforkill=math_result+" "+level;
                select_mission.setText(math_result+"  "+level);


            }if(resultCode==124){
                String photo_result=data.getStringExtra("사진찍기");
                String barcodenumber_result=data.getStringExtra("바코드번호");
                String barcodestuff_photo_result=data.getStringExtra("바코드물건사진");
                barcodenumber55=barcodenumber_result;
                barcodephoto55=barcodestuff_photo_result;
                wayforkill=photo_result;
                select_mission.setText(photo_result);
            }
        }//처음 request코드가 11일때
       else if(requestCode==117){

            if(resultCode==930){
                String no_sound=data.getStringExtra("알람소리없음");

                alarmsoundtitle=no_sound;
                alarm_sound_text.setText(alarmsoundtitle);

            }
            if(resultCode==931){
                String siren1=data.getStringExtra("알람소리싸이렌1");
                alarmsoundtitle=siren1;
                alarm_sound_text.setText(alarmsoundtitle);


            }
            if(resultCode==932){
                String siren2=data.getStringExtra("알람소리싸이렌2");
                alarmsoundtitle=siren2;
                alarm_sound_text.setText(alarmsoundtitle);
            }
            if(resultCode==397){
                String mymusic_title=data.getStringExtra("타이틀");
                String mymusic_path=data.getStringExtra("음악경로");
                long mymusic_id=data.getLongExtra("음악아이디",0);
                mymusic_alarm_path=mymusic_path;
                mymusic_alarm_id=mymusic_id;
                alarmsoundtitle=mymusic_title;
                alarm_sound_text.setText(alarmsoundtitle);
            }






        }



    }//startactivitfor result로  값 받는 메소드



    //데이트 피커 다이얼로그의 리스너이다.
    //이곳에서는 데이트피커 다이얼로그의 설정을 ok하였을때 상황이 들어간다.
    private  DatePickerDialog.OnDateSetListener listener_date1=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            //month는 0부터 ~11로   12월이 구성되어서
            //+1을 해주어야지  제되로된  달이  출력된다.

            int monthfix = month + 1;

            //토스트 메세지
            Toast.makeText(getApplicationContext(), "알람날짜 " + year + "년 " + monthfix + "월 " + dayOfMonth + "일로 지정됨", Toast.LENGTH_SHORT).show();

            //선택되었을때  주황색으로 변화면서 지정됨이라고 나오는 텍스트뷰 선언
            TextView selected_specific_alarming_date = (TextView) findViewById(R.id.maked_specific_date_text_change);
            //날짜 미지정일때 나오는 텍스트 선언
            TextView unselected_specific_alarming_date = (TextView) findViewById(R.id.unmaked_specific_date_text_change);

            //특정날짜가 설정이되면
            //지정됨  텍스트는  앞으로 보인다.
            //그리고 미지정 텍스트는 인비져블된다.
            selected_specific_alarming_date.setVisibility(View.VISIBLE);
            unselected_specific_alarming_date.setVisibility(View.INVISIBLE);
            usingspecificdate = true;







            //위에서 지정으로 텍스트가 바뀌면
            //원래 요일 정하는   각요일별 프레임 레이아웃을 담고 있던 리니어 레이아웃은
            //지정된 날짜가 나오는  텍스트 뷰로 바뀌며 인비져블된다.
            TextView show_date_text = (TextView) findViewById(R.id.maked_textview_for_show_specific_alarming_date_change);
            LinearLayout show_weekday_linear = (LinearLayout) findViewById(R.id.linear_make_each_day_for_alarming_change);

            show_date_text.setVisibility(View.VISIBLE);
            show_weekday_linear.setVisibility(View.INVISIBLE);

            //리니어대신  지정된  날짜와 함께  나오는 텍스트뷰에 들어갈 내용이다.
            show_date_text.setText("" + year + ". " + monthfix + ". " + dayOfMonth + ". 알람울림          *수정시 터치*");



            //요일을 정할때 날짜  지정 버튼을 찍혀진 상태에서 다시 그 텍스트를 누르면
            //날짜가 적혀있던 텍스트칸이 요일고르는 칸으로 변하게 되는데
            //그때 이전에 요일을 골랐었다면 다시 리셋되게 만들어주기 위한 객체 선언 및 버튼 설정 코드이다.


            monday_press.setVisibility(View.INVISIBLE);
            tuseday_press.setVisibility(View.INVISIBLE);
            wednesday_press.setVisibility(View.INVISIBLE);
            thursday_press.setVisibility(View.INVISIBLE);
            friday_press.setVisibility(View.INVISIBLE);
            saturday_press.setVisibility(View.INVISIBLE);
            sunday_press.setVisibility(View.INVISIBLE);

            monday_unpress.setVisibility(View.VISIBLE);
            tuseday_unpress.setVisibility(View.VISIBLE);
            wednesday_unpress.setVisibility(View.VISIBLE);
            thursday_unpress.setVisibility(View.VISIBLE);
            friday_unpress.setVisibility(View.VISIBLE);
            saturday_unpress.setVisibility(View.VISIBLE);
            sunday_unpress.setVisibility(View.VISIBLE);



            //날짜 가 바뀌게 되면 정해놨던 요일의 클릭여부가 위의 코드로 바뀌게 되고  선택값에 대한 여부도 바뀌어야한다.
            //그러므로 아래와 같이 월화수목금토일을 false로 해주고, 당연히 월화수목금토일 텍스트값도 false로 바꿔준다.
            mon=false;
            monday_text="";

            tuseday_text="";
            tue=false;

            wednesday_text="";
            wed=false;

            thursday_text="";
            thr=false;

            friday_text="";
            fri=false;

            saturday_text="";
            sat=false;

            sunday_text="";
            sun=false;







            //인텐트로 넘어가는 값들에는 수정된 값이 들어가야하므로 넣었다.
            year_change=year;
            day_change=dayOfMonth;
            month_change=month;

        }
    };

}
