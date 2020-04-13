package com.example.lee.alarm_application;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

/**
 * Project name: alarm_application
 * Class: SettingBasicAlarm
 * Created by lee.
 * Created On 2018-08-19.
 * Description: 기본알람을  추가 할때  알람 설정 및 추가 가  가능하도록  만드는  창이다.
 */
public class SettingBasicAlarm extends AppCompatActivity {
    int year1;
    int month1;
    int day1;
    String alarmmusic_path;
    String alarmsound;
    long alarm_id;
    Button addbn;
   //아래는 종료방법 변수
    String waysforrecyvlerviewdata;
    //바코드 넘버
    String barcodenumber33;
    //바코드 물건사진 uri
    String barcodestuffuri;
    EditText labelmessage;
    boolean alarmpush;
    boolean vibration;
    boolean specificdate=false;
    TextView select_alarm_sound;
    boolean  dayselectedm=false;
    boolean  dayselectedtue=false;
    boolean  dayselectedwed=false;
    boolean  dayselectedth=false;
    boolean  dayselectedfri=false;
    boolean  dayselectedsat=false;
    boolean  dayselectedsun=false;

    //위도 경도
    double lanti;
    double longti;

    String monday_text="";
    String tuseday_text="";
    String wednesday_text="";
    String thursday_text="";
    String friday_text="";
    String saturday_text="";
    String sunday_text="";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_basic_alarm);


        //처음에 시작할때 월화수목금토일 선택값이 false일경우 각 텍스트들 값이 아무것도 없을을 지정해줘야함.
        //이경우에는 클릭이 되지 않았을 경우이다.
        //당연히 클릭이 되지 않았을 경우니까 맨위에서 정한 false 값이 나오겠지만, true도 같이 설정한 이유는 혹시나 혹시나 하는 예외처리와
        //수정 할때 수정창에서 아래코드를 복 붙하면 편하기땜에 썼다.
        //나중에 메모리 등 문제가 생길시 꼭 지우자.

        //월요일경우
        if(dayselectedm==true){

            monday_text="월";
        }else if(dayselectedm==false){
            monday_text="";
        }

        //화요일경우
        if(dayselectedtue==true){
            tuseday_text="화";
        }else if(dayselectedtue==false){
            tuseday_text="";
        }

        //수요일경우
        if(dayselectedwed==true){
            wednesday_text="수";
        }else if(dayselectedwed==false){
            wednesday_text="";
        }

        //목요일경우
        if(dayselectedth==true){
            thursday_text="목";
        }else if(dayselectedth==false){
            thursday_text="";
        }

        //금요일 경우
        if(dayselectedfri==true){
            friday_text="금";

        }else if(dayselectedfri==false){
            friday_text="";
        }

        //토요일 경우
        if(dayselectedsat==true){
            saturday_text="토";
        }else if(dayselectedsat==false){
            saturday_text="";
        }

        //일요일 경우
        if(dayselectedsun==true){
            sunday_text="일";
        }else if(dayselectedsun==false){
            sunday_text="";
        }





        labelmessage=(EditText)findViewById(R.id.text_for_write_message);
        addbn=(Button)findViewById(R.id.add_button);
        addbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a1=new Intent();

                //바코드 사진  uri 및 바코드 넘버
                a1.putExtra("바코드사진",barcodestuffuri);
                a1.putExtra("바코드넘버",barcodenumber33);



                a1.putExtra("월요일텍스트",monday_text);
                a1.putExtra("화요일텍스트",tuseday_text);
                a1.putExtra("수요일텍스트",wednesday_text);
                a1.putExtra("목요일텍스트",thursday_text);
                a1.putExtra("금요일텍스트",friday_text);
                a1.putExtra("토요일텍스트",saturday_text);
                a1.putExtra("일요일텍스트",sunday_text);


                a1.putExtra("월",dayselectedm);
                a1.putExtra("화",dayselectedtue);
                a1.putExtra("수",dayselectedwed);
                a1.putExtra("목",dayselectedth);
                a1.putExtra("금",dayselectedfri);
                a1.putExtra("토",dayselectedsat);
                a1.putExtra("일",dayselectedsun);

                a1.putExtra("날짜일",day1);
                a1.putExtra("날짜월",month1);
                a1.putExtra("날짜년도",year1);
                a1.putExtra("특정날짜사용",specificdate);
                a1.putExtra("time",hourofdaynb);
                a1.putExtra("오전오후",오후오전);
                a1.putExtra("분",minute2);
                a1.putExtra("종료방법",waysforrecyvlerviewdata);
                a1.putExtra("메모",labelmessage.getText().toString());
                a1.putExtra("알람미루기",alarmpush);
                a1.putExtra("진동사용",vibration);

                //알람 소리 설정의 대한 상태를 보여주는 텍스트
                a1.putExtra("알람소리텍스트",alarmsound);
                a1.putExtra("알람뮤직아이디",alarm_id);
                a1.putExtra("알람뮤직경로",alarmmusic_path);


                //위도경도
               a1.putExtra("알람위도임니다",lanti);
               a1.putExtra("알람경도입니다",longti);



                setResult(33,a1);



                finish();
            }
        });







        //취소버튼을 눌러서  알람을 추가하는 행위를  그만두고 다시 원래 알람 설정  프래그먼트로 돌아간다.
        Button cancle_button=(Button)findViewById(R.id.cancel_button);
        cancle_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        //타임과  데이트 를  고르는  다이얼로그를  눌렀을때  현재 날짜 및  현재 시간을
        //보여주기위해서  캘린터 메소드들  사용하여,  현재 컴퓨터  날짜 시간 정보를  가져온다.
        final Calendar calendar = Calendar.getInstance();
        final int hour=calendar.get(calendar.HOUR_OF_DAY);//현재시간
        final int minute=calendar.get(calendar.MINUTE);//현재  분
        final int year2=calendar.get(calendar.YEAR);//현재  년도
        final int month2=calendar.get(calendar.MONTH);//현재 달
        final int day2=calendar.get(calendar.DAY_OF_MONTH);//현재 일


        //알람 시간을 설정하기 위해서  타임피커 다이얼로그를  가지고옴.
        //콘텍스트는 settingbasicalarm 액티비티이므로 this, 타임피커 다이얼로그가  실행해보니 스피너 형태가 아니어서  테마를 따로 설정함.,
        // 실행당시  현재시간의 -9의 시간으로 나와서 +9를 해놓음
        final TimePickerDialog timePickerDialog= new TimePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog,listner_time,hour+9,minute,false);


        //알람시간 설정 버튼을 누렀을때 진행되는 클릭리스너와 해당 버튼 객체 선언.
        Button select_alarming_time=(Button)findViewById(R.id.button_for_select_alarming_time);
        //시간 설정 버튼을 누르면   타임피커 다이얼로그가  show 되도록  설정하였다.
        select_alarming_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

          timePickerDialog.show();
            }
        });//알람시간 설정 버튼  닫힘.




        final TextView show_date_text=(TextView)findViewById(R.id.textview_for_show_specific_alarming_date);
        final LinearLayout show_weekday_linear=(LinearLayout)findViewById(R.id.linear_select_each_day_for_alarming);
        final TextView selected_specific_alarming_date=(TextView)findViewById(R.id.selected_specific_date_text);

        //만약에 알람 반복되는 날을  특정날짜로 정하고 싶을때  사용하는 다이얼로그이다
        //알람이 울림 날짜를  선택한다.
        final DatePickerDialog datePickerDialog=new DatePickerDialog(this,listener_date,year2,month2,day2);

        //텍스트 중에 특정 알람날짜 미지정을  clickable로 바꾸어서
        //클릭하면  date picker dialog가  나오도록 하는 코드이다.
         final TextView unselected_specific_alarming_date=(TextView)findViewById(R.id.unselected_specific_date_text);
         //알람날짜 미지정 텍스트를 누르면 show(); 나옴.
         unselected_specific_alarming_date.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 datePickerDialog.show();
             }
         });

        //특정요일 설정한 값을 다시 수정하고 싶을때는 날짜가 보이는 텍스트를 다시 누르면 달력이 나온다.
        //처음에 설정한 날짜에 찍혀있으면 원하는대로 바꿀수 있다.
        show_date_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datePickerDialog.show();

            }
        });

        //특정날짜  알람이 지정이 되어있을때
        //지정되어있을을 알리는 주황색 텍스트뷰를 누르면  원래 미지정 텍스트가 나오고,
        //요일을 지정하는 텍슽트가  나오고 아까 데이트 타임은  리셋되도록 설정하였다.
        selected_specific_alarming_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselected_specific_alarming_date.setVisibility(View.VISIBLE);
                selected_specific_alarming_date.setVisibility(View.INVISIBLE);
                show_date_text.setVisibility(View.INVISIBLE);
                show_weekday_linear.setVisibility(View.VISIBLE);

                //아래부분은 혹시나 클릭을해서 날짜를 고르고 그다음에 다시 취소를 하고 수정을 하였을경우에
                //그 해당 값들이 저장될때 지정한 값들을 리셋시킨것이므로 다시 해당 날짜가 들어가게 지정하였다.
                month1=month2;
                year1=year2;
                day1=day2;
                datePickerDialog.updateDate(year2, month2, day2);
                show_date_text.setText("");
               specificdate=false;
            }
        });






        //특정날짜 알람 미지정일 경우에는
        //월화수목금토일로 알람이 울릴수 있는 날짜 및  시간을 정할수 가 있다.
        final TextView monday_pressed=(TextView)findViewById(R.id.monday_pressed_text);
        final TextView monday_unpressed=(TextView)findViewById(R.id.monday_unpressed_text);
        final TextView tuseday_pressed=(TextView)findViewById(R.id.tuesday_pressed_text);
        final TextView tuseday_unpressed=(TextView)findViewById(R.id.tuesday_unpressed_text);
        final TextView wednesday_pressed=(TextView)findViewById(R.id.wednesday_pressed_text);
        final TextView wednesday_unpressed=(TextView)findViewById(R.id.wednesday_unpressed_text);
        final TextView thursday_pressed=(TextView)findViewById(R.id.thursday_pressed_text);
        final TextView thursday_unpressed=(TextView)findViewById(R.id.thursday_unpressed_text);
        final TextView friday_pressed=(TextView)findViewById(R.id.friday_pressed_text);
        final TextView friday_unpressed=(TextView)findViewById(R.id.friday_unpressed_text);
        final TextView saturday_pressed=(TextView)findViewById(R.id.saturday_pressed_text);
        final TextView saturday_unpressed=(TextView)findViewById(R.id.saturday_unpressed_text);
        final TextView sunday_pressed=(TextView)findViewById(R.id.sunday_pressed_text);
        final TextView sunday_unpressed=(TextView)findViewById(R.id.sunday_unpressed_text);


        //각 요일별로 원하는 요일을 고르면  주황색으로 텍스트 칸이 변하고 원하지 않으면
        //해당 주항색텍스트칸을 다시 누르면 다시 돌아가게 만드는 코드이다.
        //이경우에는 선택의 대한 true, false값이  선택한 경우에만 적용되는 것이다.
        //그냥 아무 터치도 안하고 맨처음 false or true 값일 경우에는 텍스트 보이는 값이 다르다는것을 보여주는 코드들 맨위에 작성함.
        monday_pressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                monday_unpressed.setVisibility(View.VISIBLE);
                monday_pressed.setVisibility(View.INVISIBLE);

                dayselectedm=false;
                monday_text="";

            }
        });
        monday_unpressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monday_unpressed.setVisibility(View.INVISIBLE);
                monday_pressed.setVisibility(View.VISIBLE);
                monday_text="월";
                dayselectedm=true;


            }
        });

        tuseday_pressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tuseday_pressed.setVisibility(View.INVISIBLE);
                tuseday_unpressed.setVisibility(View.VISIBLE);
               tuseday_text="";
                dayselectedtue=false;


            }
        });
        tuseday_unpressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tuseday_pressed.setVisibility(View.VISIBLE);
                tuseday_unpressed.setVisibility(View.INVISIBLE);
                tuseday_text="화";
                dayselectedtue=true;


            }
        });
         wednesday_pressed.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 wednesday_pressed.setVisibility(View.INVISIBLE);
                 wednesday_unpressed.setVisibility(View.VISIBLE);
                 wednesday_text="";
                 dayselectedwed=false;


             }
         });
        wednesday_unpressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wednesday_pressed.setVisibility(View.VISIBLE);
                wednesday_unpressed.setVisibility(View.INVISIBLE);
                wednesday_text="수";
                dayselectedwed=true;

            }
        });
        thursday_pressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thursday_pressed.setVisibility(View.INVISIBLE);
                thursday_unpressed.setVisibility(View.VISIBLE);
                 thursday_text="";
                dayselectedth=false;

            }
        });

        thursday_unpressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thursday_pressed.setVisibility(View.VISIBLE);
                thursday_unpressed.setVisibility(View.INVISIBLE);
                thursday_text="목";
                dayselectedth=true;


            }
        });

         friday_pressed.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 friday_pressed.setVisibility(View.INVISIBLE);
                 friday_unpressed.setVisibility(View.VISIBLE);
                 friday_text="";
                 dayselectedfri=false;


             }
         });

        friday_unpressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friday_pressed.setVisibility(View.VISIBLE);
                friday_unpressed.setVisibility(View.INVISIBLE);
                friday_text="금";
                dayselectedfri=true;


            }
        });

        saturday_pressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saturday_pressed.setVisibility(View.INVISIBLE);
                saturday_unpressed.setVisibility(View.VISIBLE);
                saturday_text="";
                dayselectedsat=false;


            }
        });

        saturday_unpressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saturday_pressed.setVisibility(View.VISIBLE);
                saturday_unpressed.setVisibility(View.INVISIBLE);
                saturday_text="토";
                dayselectedsat=true;


            }
        });

        sunday_pressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sunday_pressed.setVisibility(View.INVISIBLE);
                sunday_unpressed.setVisibility(View.VISIBLE);
                 sunday_text="";
                dayselectedsun=false;

            }
        });

       sunday_unpressed.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               sunday_pressed.setVisibility(View.VISIBLE);
               sunday_unpressed.setVisibility(View.INVISIBLE);
               sunday_text="일";
               dayselectedsun=true;


           }
       });
      //여기까지 요일 버튼으로  요일을 설정하는 코드





        //알람 해제방법을  고르기위한  액티비티로 가는  코드
        //역시 결과 값을 가져오기 위한  형태로 사용하므로 start activityforresult를 사용하였다.
        TextView select_mission=(TextView)findViewById(R.id.text_for_select_mission_to_clear_alarm);
        select_mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call_select_mission_activity= new Intent(getApplicationContext(),WaysForKillingAlarm.class);
                startActivityForResult(call_select_mission_activity,11);
            }
        });


          //알람 사운드를 고르기 위해 사운드 리스트가 있는 액티비로 들어가는
          //코드이다.
          //역시나 결과값을 가져와야 하므로  activitiforResult를 사용하였다.
           select_alarm_sound=(TextView)findViewById(R.id.text_for_select_alarm_sound);
           select_alarm_sound.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent call_sound_list=new Intent(getApplicationContext(),AlarmSoundList.class);
                  startActivityForResult(call_sound_list,2);
              }
          });



          //알람이 울릴때 진동여부를  설정 한다.
          //진동 선택 여부에 따라 해당 리니어가  인비져블 또는 비져블 된다.
        final LinearLayout using_vibration=(LinearLayout)findViewById(R.id.using_vibration_icon_linear);
        final LinearLayout unusing_vibration=(LinearLayout)findViewById(R.id.unusing_vibration_icon_linear);


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





         //알람 미루기 를 설정할수 있다.  미루기를 누르면 인비져블로 되어있던 미루기 설정  주황색 텍스트뷰가보이고
        //회색으로 설정되었던 미루지 않은 텍스트는 인비져블 상태로 된다.
         final TextView using_alarm_push=(TextView)findViewById(R.id.using_alarmpush);
         final TextView unusing_alarm_push=(TextView)findViewById(R.id.unusing_alarmpush);


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






    }//on create 닫힘.


    //Startactivityror result의 값을 받아올때 사용하는 코드이다.
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView select_mission=(TextView)findViewById(R.id.text_for_select_mission_to_clear_alarm);
        if(requestCode==11){

        //waysforKillingAlarm에서  기본 알람 눌렀을 경우 리절트코드를 100으로 설정하였기 때문에
        //해당값을 받기 위해서 100을 집어 넣었다.
        if(resultCode==100){
            String  basic_result=data.getStringExtra("기본알람선택");
            waysforrecyvlerviewdata=basic_result;
            select_mission.setText(basic_result);
        }//기본알람 값
         if(resultCode==120){
            String map_result=data.getStringExtra("위치알람선택");
            double lati_result=data.getDoubleExtra("위치알람위도",0.0);
            double lonti_result=data.getDoubleExtra("위치알람경도",0.0);

            //위도값
            lanti=lati_result;
            //경도값
            longti=lonti_result;

            waysforrecyvlerviewdata=map_result;
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

             waysforrecyvlerviewdata=math_result+" "+level;
            select_mission.setText(math_result+"  "+level);


         }if(resultCode==124){
             String photo_result=data.getStringExtra("사진찍기");
             String barcodenumber_result=data.getStringExtra("바코드번호");
             String barcodestuff_photo_result=data.getStringExtra("바코드물건사진");

             waysforrecyvlerviewdata=photo_result;
             barcodenumber33=barcodenumber_result;
             barcodestuffuri=barcodestuff_photo_result;
             select_mission.setText(photo_result);
         }
    }//처음 request코드가 11일때
        if(requestCode==2){
            if(resultCode==930){
                String no_sound=data.getStringExtra("알람소리없음");

                alarmsound=no_sound;
                select_alarm_sound.setText(alarmsound);

            }
           if(resultCode==931){
                String siren1=data.getStringExtra("알람소리싸이렌1");
                alarmsound=siren1;
                select_alarm_sound.setText(alarmsound);


           }
          if(resultCode==932){
                String siren2=data.getStringExtra("알람소리싸이렌2");
                alarmsound=siren2;
                select_alarm_sound.setText(alarmsound);
          }
          if(resultCode==397){
                String mymusic_title=data.getStringExtra("타이틀");
                String mymusic_path=data.getStringExtra("음악경로");
                long mymusic_id=data.getLongExtra("음악아이디",0);
                alarmmusic_path=mymusic_path;
                alarm_id=mymusic_id;
                alarmsound=mymusic_title;
                select_alarm_sound.setText(mymusic_title);
          }
        }




    }//startactivitfor result로  값 받는 메소드


    //데이트 피커 다이얼로그의 리스너이다.
    //이곳에서는 데이트피커 다이얼로그의 설정을 ok하였을때 상황이 들어간다.
private  DatePickerDialog.OnDateSetListener listener_date=new DatePickerDialog.OnDateSetListener() {
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //month는 0부터 ~11로   12월이 구성되어서
        //+1을 해주어야지  제되로된  달이  출력된다.

    int monthfix=month+1;

       //토스트 메세지
        Toast.makeText(getApplicationContext(),"알람날짜 "+year+"년 "+monthfix+"월 "+dayOfMonth+"일로 지정됨", Toast.LENGTH_SHORT).show();

        //선택되었을때  주황색으로 변화면서 지정됨이라고 나오는 텍스트뷰 선언
        TextView selected_specific_alarming_date=(TextView)findViewById(R.id.selected_specific_date_text);
        //날짜 미지정일때 나오는 텍스트 선언
        TextView unselected_specific_alarming_date=(TextView)findViewById(R.id.unselected_specific_date_text);

        //특정날짜가 설정이되면
        //지정됨  텍스트는  앞으로 보인다.
        //그리고 미지정 텍스트는 인비져블된다.
        selected_specific_alarming_date.setVisibility(View.VISIBLE);
        unselected_specific_alarming_date.setVisibility(View.INVISIBLE);

        specificdate=true;


        //위에서 지정으로 텍스트가 바뀌면
        //원래 요일 정하는   각요일별 프레임 레이아웃을 담고 있던 리니어 레이아웃은
        //지정된 날짜가 나오는  텍스트 뷰로 바뀌며 인비져블된다.
        TextView show_date_text=(TextView)findViewById(R.id.textview_for_show_specific_alarming_date);
        LinearLayout show_weekday_linear=(LinearLayout)findViewById(R.id.linear_select_each_day_for_alarming);

        show_date_text.setVisibility(View.VISIBLE);
        show_weekday_linear.setVisibility(View.INVISIBLE);

        //리니어대신  지정된  날짜와 함께  나오는 텍스트뷰에 들어갈 내용이다.
        show_date_text.setText(""+year+". "+monthfix+". "+dayOfMonth+". 알람울림          *수정시 터치*");





        //요일을 정할때 날짜  지정 버튼을 찍혀진 상태에서 다시 그 텍스트를 누르면
        //날짜가 적혀있던 텍스트칸이 요일고르는 칸으로 변하게 되는데
        //그때 이전에 요일을 골랐었다면 다시 리셋되게 만들어주기 위한 객체 선언 및 버튼 설정 코드이다.
        final TextView monday_pressed=(TextView)findViewById(R.id.monday_pressed_text);
        final TextView monday_unpressed=(TextView)findViewById(R.id.monday_unpressed_text);
        final TextView tuseday_pressed=(TextView)findViewById(R.id.tuesday_pressed_text);
        final TextView tuseday_unpressed=(TextView)findViewById(R.id.tuesday_unpressed_text);
        final TextView wednesday_pressed=(TextView)findViewById(R.id.wednesday_pressed_text);
        final TextView wednesday_unpressed=(TextView)findViewById(R.id.wednesday_unpressed_text);
        final TextView thursday_pressed=(TextView)findViewById(R.id.thursday_pressed_text);
        final TextView thursday_unpressed=(TextView)findViewById(R.id.thursday_unpressed_text);
        final TextView friday_pressed=(TextView)findViewById(R.id.friday_pressed_text);
        final TextView friday_unpressed=(TextView)findViewById(R.id.friday_unpressed_text);
        final TextView saturday_pressed=(TextView)findViewById(R.id.saturday_pressed_text);
        final TextView saturday_unpressed=(TextView)findViewById(R.id.saturday_unpressed_text);
        final TextView sunday_pressed=(TextView)findViewById(R.id.sunday_pressed_text);
        final TextView sunday_unpressed=(TextView)findViewById(R.id.sunday_unpressed_text);

        monday_pressed.setVisibility(View.INVISIBLE);
        tuseday_pressed.setVisibility(View.INVISIBLE);
        wednesday_pressed.setVisibility(View.INVISIBLE);
        thursday_pressed.setVisibility(View.INVISIBLE);
        friday_pressed.setVisibility(View.INVISIBLE);
        saturday_pressed.setVisibility(View.INVISIBLE);
        sunday_pressed.setVisibility(View.INVISIBLE);

        monday_unpressed.setVisibility(View.VISIBLE);
        tuseday_unpressed.setVisibility(View.VISIBLE);
        wednesday_unpressed.setVisibility(View.VISIBLE);
        thursday_unpressed.setVisibility(View.VISIBLE);
        friday_unpressed.setVisibility(View.VISIBLE);
        saturday_unpressed.setVisibility(View.VISIBLE);
        sunday_unpressed.setVisibility(View.VISIBLE);




        //날짜 가 바뀌게 되면 정해놨던 요일의 클릭여부가 위의 코드로 바뀌게 되고  선택값에 대한 여부도 바뀌어야한다.
        //그러므로 아래와 같이 월화수목금토일을 false로 해주고, 당연히 월화수목금토일 텍스트값도 false로 바꿔준다.
        dayselectedm=false;
        monday_text="";

        tuseday_text="";
        dayselectedtue=false;

        wednesday_text="";
        dayselectedwed=false;

        thursday_text="";
        dayselectedth=false;

        friday_text="";
        dayselectedfri=false;

        saturday_text="";
        dayselectedsat=false;

        sunday_text="";
        dayselectedsun=false;



        //인텐트로 넘어가는 값들에는 수정된 값이 들어가야하므로 넣었다.
        year1=year;
        day1=dayOfMonth;
        month1=month;


    }
};



String 오후오전;
int hourofdaynb;
int hourday1;
int minute2;
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
         hourofdaynb = hourOfDay;//어레이리스트로 보내기 위해서 넣음

         //이곳에서 고른 시간을 보여줄때 사용하는 객체
         hourday1=hourOfDay;
         minute2=minute;
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
         TextView show_alarm_time_text=(TextView)findViewById(R.id.text_for_showing_selected_alarm_time);
         show_alarm_time_text.setText(""+오후오전+"  "+ hourday1 + "시 " + minute2 + "분"+"");
         Toast.makeText(getApplicationContext(), "알람시간은 "+오후오전+"  "+ hourday1 + "시 " + minute2 + "분"+" 입니다.", Toast.LENGTH_SHORT).show();


    }
}; //타임피커다이얼로그의  리스너 메소드 닫힘.



}//전체 클래스(setting basicalarm) 닫힘
