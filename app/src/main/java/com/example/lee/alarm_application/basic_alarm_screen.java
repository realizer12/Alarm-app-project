package com.example.lee.alarm_application;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.example.lee.alarm_application.Myadapter.makedAlarms_arraylist;



/**
 * A simple {@link Fragment} subclass.
 * 기본 알람 기능이 시작되서  저장되는 프래그먼트이다.
 * 이곳에서 알람이 추가되고  수정  삭제도  가능하다.
 */
public class basic_alarm_screen extends Fragment {
   public static AlarmManager alarmManagers[];
    //알람 내용을 모은뒤,  리사이클러뷰 아이템에 보일 데이터를 넣을때 사용할 어레이리스트(데이터 그릇역할)

    public static   PendingIntent pendingIntent;
    public static ArrayList<PendingIntent> intentArrayList;

    public static  Intent intent;

    View view;
    //만들어진 알람 리스트들이 보일 리사이클러뷰
    private RecyclerView recyclerView;

    //알람 추가 버튼  블랙 화이트로 2개로 나누어 눌렀을때 눌림 효과 주었다..
    ImageView addbutton_black;
    ImageView addbutton_white;
    static TextView explainfordeletandchange;
    //어레이리스트에 들어간 데이터들을 리사이클러뷰 아이템으로 변환 시켜 사용자눈에 보이게 해주는
    //어댑터
    Myadapter myadapter;
      public static Context context2;
    //메인화면에서 기본알람이 나오는 프래그먼트 이다.
    //이곳에서는 알람 리스트들을 추가할수 있으며,
    //만들어진 리스트들을 각각 수정, 삭제 할수 있게 한다.


    public basic_alarm_screen() {
        // Required empty public constructor

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
                context2=context;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        Log.v("플래그먼트", "oncreateview");

        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_basic_alarm_screen, container, false);

        //리사이클러뷰 객체 선언.
        recyclerView = (RecyclerView) rootView.findViewById(R.id.alalrm_list);

        //선언하여 메모리에 올라온 리사이클러뷰를 레이아웃 매니저를 통해 리니어 레이아웃 매니저에
        //장착하는것이다. 그밖에 그리드형태 커스텀형태등 많은 레이아웃 매니저를 리사이클러뷰가 지원하고,
        //나같은 경우에는 세로로 증가하는 리니어 레이아웃이 필요하여 이렇게 설정하였다.
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);





        //sharedpreferecnce로 저장한  어레이  값들을  프래그먼트의  oncreateview에서 보여줌으로써
        //저장된값이 나오도록 하여따.

            loadData();


        //위 어레이리스트 내용을 리사이클러뷰로 변환할 어댑터 선언
        myadapter = new Myadapter(getActivity(), makedAlarms_arraylist);

        //선언한 리사이클러뷰 선언한 어댑터에 장착.
        recyclerView.setAdapter(myadapter);
        recyclerView.setNestedScrollingEnabled(true);

        //어댑터의 사이즈 픽스 함.
        recyclerView.setHasFixedSize(false);

        //기본알람 프래그먼트에서 기본알람을 추가할수 있는 버튼이다.
        addbutton_black = (ImageView) rootView.findViewById(R.id.addbutton_black);
        addbutton_white = (ImageView) rootView.findViewById(R.id.addbutton_white);

        //처음에 들어올때 추가 버튼이 검점색으로 보이게 하는 코드
        addbutton_black.setVisibility(View.VISIBLE);
        addbutton_white.setVisibility(View.INVISIBLE);


        //추가 버튼을 누를경우 인텐트를 통해  settingbasicalarm 액티비티를 나오게 한다.
        // 이때 settingbasicalarm은  이곳의 정보를 입력하고 가지고와  basic_alarm_screen프래그먼트에서 리스트뷰에 리스트를 추가 시키기 위한용도이다.
        //그러므로 startactiviryㄹforresult를 통해  정보를 바로 가지고 돌아 올수 있도록  만들었다.
        addbutton_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //버튼을 누를대 누른효과를 내기위해 검정색 추가버튼이 하얀색으로 보이게 하였다.
                //다시 돌아갈때나 처음들어갈때는 검정으로 보이게 해야함.
                addbutton_black.setVisibility(View.INVISIBLE);
                addbutton_white.setVisibility(View.VISIBLE);

                //알람 정보를 가져오기 위해서 실행하는 startactivityforresult 메소드이다.
                Intent intent1 = new Intent(getActivity(), SettingBasicAlarm.class);
                getActivity().startActivityForResult(intent1, 153);


            }
        });

        registerForContextMenu(this.recyclerView);


        //맨처음 시작할때  삭제,수정 설명글이 눈에 보이지 않도록 설정하였다.
        explainfordeletandchange = (TextView) rootView.findViewById(R.id.more_explain_text_for_alarm_list);
        explainfordeletandchange.setVisibility(View.INVISIBLE);



        return rootView;


    }


    public  static  void releasealarm(Context context) {



        alarmManagers = new AlarmManager[makedAlarms_arraylist.size()];


        intentArrayList = new ArrayList<PendingIntent>();

        intentArrayList.clear();



        Calendar[] calendar = new Calendar[makedAlarms_arraylist.size()];
        if(makedAlarms_arraylist.size()>0) {
            for (int i = 0; i < makedAlarms_arraylist.size(); i++) {
                if (makedAlarms_arraylist.get(i).isSelected() ==false) {
                    calendar[i] = Calendar.getInstance();
                    if (makedAlarms_arraylist.get(i).isUsingspecificdate() == true) {
                        calendar[i].set(Calendar.YEAR, makedAlarms_arraylist.get(i).getSpecificyear());
                        calendar[i].set(Calendar.MONTH, makedAlarms_arraylist.get(i).getSpecificmonth());
                        calendar[i].set(Calendar.DAY_OF_MONTH, makedAlarms_arraylist.get(i).getSpecificday());
                        calendar[i].set(Calendar.HOUR_OF_DAY, makedAlarms_arraylist.get(i).getAlarmtime());
                        calendar[i].set(Calendar.MINUTE, makedAlarms_arraylist.get(i).getAlarmminute());
                        calendar[i].set(Calendar.SECOND, 0);
                        calendar[i].set(Calendar.MILLISECOND, 0);
                    } else if (makedAlarms_arraylist.get(i).isUsingspecificdate() == false) {

                        //요일 반복 해결해야됨

                        calendar[i].set(Calendar.HOUR_OF_DAY, makedAlarms_arraylist.get(i).getAlarmtime());
                        calendar[i].set(Calendar.MINUTE, makedAlarms_arraylist.get(i).getAlarmminute());
                        calendar[i].set(Calendar.SECOND, 0);
                        calendar[i].set(Calendar.MILLISECOND, 0);

                    }

                    // 기존 저장된 시간이 현재 시간보다 이전이면,   알람이   울리지 않는다.
                    if (calendar[i].getTimeInMillis() < System.currentTimeMillis()) {
                        Log.v("바뀜", "시간 지나감  못나옴");


                    } else {

                        intent= new Intent(context, AlarmReceiver.class);

                        intent.putExtra("알람맵해제용위도",makedAlarms_arraylist.get(i).getLatitude());
                        intent.putExtra("알람맵해제용경도",makedAlarms_arraylist.get(i).getLongtitude());
                        intent.putExtra("알람바코드번호",makedAlarms_arraylist.get(i).getBarcodenumber());
                        intent.putExtra("알람바코드사진",makedAlarms_arraylist.get(i).getBarcodestuffphotouri());
                        intent.putExtra("알람날짜미루기용", i);
                        intent.putExtra("알람해제종류",makedAlarms_arraylist.get(i).getWayforfinish_alarm());
                        intent.putExtra("알람소리종류", makedAlarms_arraylist.get(i).getAlarmsound_text());
                        intent.putExtra("알람미루기", makedAlarms_arraylist.get(i).isAlamrpushed());
                        intent.putExtra("진동여부", makedAlarms_arraylist.get(i).isVibration());
                        intent.putExtra("벨소리위치", makedAlarms_arraylist.get(i).getAlarm_music_path());
                        intent.putExtra("요일여부", makedAlarms_arraylist.get(i).isUsingspecificdate());
                        intent.putExtra("월요일여부", makedAlarms_arraylist.get(i).isMonday());
                        intent.putExtra("화요일여부", makedAlarms_arraylist.get(i).isTuesday());
                        intent.putExtra("수요일여부", makedAlarms_arraylist.get(i).isWednesday());
                        intent.putExtra("목요일여부", makedAlarms_arraylist.get(i).isThursday());
                        intent.putExtra("금요일여부", makedAlarms_arraylist.get(i).isFriday());
                        intent.putExtra("토요일여부", makedAlarms_arraylist.get(i).isSaturday());
                        intent.putExtra("일요일여부", makedAlarms_arraylist.get(i).isSunday());
                        intent.putExtra("알람메모", makedAlarms_arraylist.get(i).getMemo());


                        //추가됨.
                        intent.putExtra("알람시간", calendar[i].getTimeInMillis());
                        intent.putExtra("알람리퀘", i);

                        alarmManagers[i] = (AlarmManager) context.getSystemService(ALARM_SERVICE);
                        pendingIntent = PendingIntent.getBroadcast(context, i, intent, PendingIntent.FLAG_UPDATE_CURRENT);


                        if (Build.VERSION.SDK_INT >= 23) {
                            if (makedAlarms_arraylist.get(i).isUsingspecificdate() == true) {

                                //도즈 모드 관련 처리를 위해  ->  setExactAndAllowWhileIdle를  사용하였다.
                                //alarmManagers[i].setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar[i].getTimeInMillis(), pendingIntent);
                                AlarmManager.AlarmClockInfo ac = new AlarmManager.AlarmClockInfo(calendar[i].getTimeInMillis(), pendingIntent);

                                alarmManagers[i].setAlarmClock(ac,pendingIntent);
                                intentArrayList.add(pendingIntent);

                                Log.v("만들면 진행한다.", "알람추가됨");
                            } else if (makedAlarms_arraylist.get(i).isUsingspecificdate() == false) {

                                //반복알람문에는 setrepeating 함수와  setinexactrepeaeting  함수가 있는데  setrepeating의 경우에는 배터리 소모가 심하지만 api 19이하까지는 정확한 시간이 잡히고 그이상은
                                //잡히지가 않고  setinexactrepeating함수 경우에는  배터리 소모가  심하지 않지만  시간  정확도가 맣이 떨어진다.
                                //그래서 apu 19 이상부턴  단발성 알람 함수들을 이용하여  다시  재등록 하는 형태로  반복 알람을 진행한다고 한다.
                                //하지만   setrepeating함수를 사용한 결과  시간 차이가 정확하여서 여기서 나는 setrepeating 함수를 사용하였다.
                                alarmManagers[i].setRepeating(AlarmManager.RTC_WAKEUP, calendar[i].getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                                intentArrayList.add(pendingIntent);

//                                AlarmManager.AlarmClockInfo ac = new AlarmManager.AlarmClockInfo(calendar[i].getTimeInMillis(), pendingIntent);
//                                alarmManagers[i].setAlarmClock(ac,pendingIntent);
//
//                                intentArrayList.add(pendingIntent);

                            }

                        } else {
                            if (Build.VERSION.SDK_INT >= 19) {
                                if (makedAlarms_arraylist.get(i).isUsingspecificdate()) {
                                    alarmManagers[i].setExact(AlarmManager.RTC_WAKEUP, calendar[i].getTimeInMillis(), pendingIntent);
                                    intentArrayList.add(pendingIntent);
                                    Log.v("만들면 진행한다.", "알람추가됨");
                                } else if (!makedAlarms_arraylist.get(i).isUsingspecificdate()) {
                                    alarmManagers[i].setRepeating(AlarmManager.RTC_WAKEUP, calendar[i].getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                                    intentArrayList.add(pendingIntent);
                                }
                            } else {
                                if (makedAlarms_arraylist.get(i).isUsingspecificdate() == true) {
                                    alarmManagers[i].set(AlarmManager.RTC_WAKEUP, calendar[i].getTimeInMillis(), pendingIntent);
                                    intentArrayList.add(pendingIntent);
                                    Log.v("만들면 진행한다.", "알람추가됨");
                                } else if (makedAlarms_arraylist.get(i).isUsingspecificdate() == false) {
                                    alarmManagers[i].setRepeating(AlarmManager.RTC_WAKEUP, calendar[i].getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                                    intentArrayList.add(pendingIntent);
                                }
                            }
                        }
                    }
                }
            }
        }
    }







    public   void savaData(){

        //shared preference에서 getsharedpreferences()메드는  context의 object이다 즉  프래그먼트가 아닌 프래그먼트를
        // 감싸고 있는 액티비티에서만 사용가능하다.
        // 그래서  프래그먼트안에서 사용하기 위해서 this.getactivity()를 넣어줘서 현재 엑티비티의 context를 가지고오고

        // 해당 메소드를 사용하였따.                                            mode_private은  sharepreference에서  모드 설정할때 자기 앱에서만
        //                                                                   사용하도록 설정하는 기본 값임.  다른 모드도 존재함.
        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=gson.toJson(makedAlarms_arraylist);
        editor.putString("gogson",json);
        editor.apply();

    }
   public void loadData(){

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("shared preferences",Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("gogson",null);
        Type type = new TypeToken<ArrayList<MakedAlarm>>() {}.getType();
        makedAlarms_arraylist=gson.fromJson(json,type);

        }

    @Override
    public void onResume() {
        addbutton_black.setVisibility(View.VISIBLE);
        addbutton_white.setVisibility(View.INVISIBLE);
        Log.v("플래그먼트","onresume");

        releasealarm(getActivity());
        super.onResume();




        //데이터를 받아와서  어레이리스트에 1이상 추가되었을 경우에는 삭제 편집 관련 설명글이 눈에 보이도록 조치하였다.
        //on resume에 넣은 이유는  createview에서는  설명글이 안보이도록 설정을 해놓았는데,
        // 사이즈가 영이면  보이게 하는것까지 코드를 같이 넣으니까  작동이안됨. 그래서  프래그먼트가화면에 보이는 지점까지  통과하게 되는 resume에 해당 코드를 넣어서
        // 사용자에게 보일때는  리스트값이 0이상이면 설명글이 보이도록 하엮다.
        if(makedAlarms_arraylist.size()>0) {
            explainfordeletandchange.setVisibility(View.VISIBLE);
        }



        savaData();
    }

    //처음에 savedata를 destroy쪽에 넣었었는데, 저장은 되지만  수정값은  조정이되지만,  프래그먼트에서 보이는 스위치값을 바꾸고 바로  앱을 종료하면  값이 저장되지 않는  상황이나옴,
    //프래그먼트가 켜져있는 상태로 앱을 끄기위해 비활성화 단계로 들어가면 우선 stop이되는데 거기서  빨리 화면을 껏을때 프래그먼트가  destroy까지 않고 거기서  stop에서 멈춰지는것같음.
    //그래서 stop에다가  데이터 저장을 넣으니까 정상적으로 다  저장됨.// 만약에 stop까지 가기전에 종료가 되는 경우??
    //뭔가  STOP까지 가는것도 좀 불안해서 PAUSE로 바꾸었다.
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onPause() {
        super.onPause();
        savaData();

        releasealarm(getActivity());
        Log.v("frag_pause","나오나??");
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==153)
        {
             //settingBasicAlarm 액티비티에서 받아오는 결가값들임
             //여기서 넘어오는 값들이 어댑터로도 들어가고 그밖에 알람매니저 설정에 필요한 값들 참조시 사용될것임.
             //전부 MakeAlarm클래스에 넣어야함.
            if(resultCode==33){

             String barcodephoto=data.getStringExtra("바코드사진");
             String barcodenumber=data.getStringExtra("바코드넘버");

              int timeresult=data.getIntExtra("time",1);
              int minuteresult=data.getIntExtra("분",1);
              String waysforkillingalarmtext=data.getStringExtra("종료방법");
              String alarmmemo=data.getStringExtra("메모");
              boolean alarmpushresult=data.getBooleanExtra("알람미루기",false);
              boolean alamrvibration=data.getBooleanExtra("진동사용",false);
              int spcificdateyear=data.getIntExtra("날짜년도",0);
              int specificdatemonth=data.getIntExtra("날짜월",0);
              int specificdateday=data.getIntExtra("날짜일",0);
              boolean usespecificdate=data.getBooleanExtra("특정날짜사용",false);
              boolean monday=data.getBooleanExtra("월",false);
              boolean tuesday=data.getBooleanExtra("화",false);
              boolean wednesday=data.getBooleanExtra("수",false);;
              boolean thursday=data.getBooleanExtra("목",false);;
              boolean friday=data.getBooleanExtra("금",false);;
              boolean saturday=data.getBooleanExtra("토",false);;
              boolean sunday=data.getBooleanExtra("일",false);;
              String monday_text=data.getStringExtra("월요일텍스트");
              String tuseday_text=data.getStringExtra("화요일텍스트");
              String wednesday_text=data.getStringExtra("수요일텍스트");
              String thursday_text=data.getStringExtra("목요일텍스트");
              String friday_text=data.getStringExtra("금요일텍스트");
              String saturday_text=data.getStringExtra("토요일텍스트");
              String sunday_text=data.getStringExtra("일요일텍스트");
              String alamr_sound_title=data.getStringExtra("알람소리텍스트");
              long myalarm_sound_id=data.getLongExtra("알람뮤직아이디",0);
              String alarm_path=data.getStringExtra("알람뮤직경로");
              double alarm_map_latitude=data.getDoubleExtra("알람위도임니다",0.0);
              double alarm_map_longtitude=data.getDoubleExtra("알람경도입니다",0.0);



              MakedAlarm makedAlarm=new MakedAlarm();
              makedAlarm.setAlarmsound_text(alamr_sound_title);
              makedAlarm.setAlarm_music_path(alarm_path);

              makedAlarm.setMonday_text(monday_text);
              makedAlarm.setTuseday_text(tuseday_text);
              makedAlarm.setWednesday_text(wednesday_text);
              makedAlarm.setThursday_text(thursday_text);
              makedAlarm.setFriday_text(friday_text);
              makedAlarm.setSaturday_text(saturday_text);
              makedAlarm.setSunday_text(sunday_text);

              makedAlarm.setMonday(monday);
              makedAlarm.setTuesday(tuesday);
              makedAlarm.setWednesday(wednesday);
              makedAlarm.setThursday(thursday);
              makedAlarm.setFriday(friday);
              makedAlarm.setSaturday(saturday);
              makedAlarm.setSunday(sunday);
              makedAlarm.setUsingspecificdate(usespecificdate);
              makedAlarm.setSpecificday(specificdateday);
              makedAlarm.setSpecificmonth(specificdatemonth);
              makedAlarm.setSpecificyear(spcificdateyear);
              makedAlarm.setAlarmtime(timeresult);
              makedAlarm.setAlarmminute(minuteresult);
              makedAlarm.setWayforfinish_alarm(waysforkillingalarmtext);
              makedAlarm.setMemo(alarmmemo);
              makedAlarm.setAlamrpushed(alarmpushresult);
              makedAlarm.setVibration(alamrvibration);
              makedAlarm.setAlarm_sound_id(myalarm_sound_id);

              makedAlarm.setBarcodenumber(barcodenumber);
              makedAlarm.setBarcodestuffphotouri(barcodephoto);

              makedAlarm.setLatitude(alarm_map_latitude);
              makedAlarm.setLongtitude(alarm_map_longtitude);


              makedAlarms_arraylist.add(0,makedAlarm);





              myadapter.notifyDataSetChanged();

            }
        }


        //어댑터에 startactivitforresult를 사용하면 result값이 그 부모인 액티비티나 프래그먼트로
        //넘어가기 때문에 아래와 같은 코드로 현재 부모인 이 프래그먼트로 넘어온 결과값을
        //다시 어댑터 onactivity로  넘겨주게 만든다.
        myadapter.onActivityResult(requestCode,resultCode,data);

        savaData();
        myadapter.notifyDataSetChanged();
        releasealarm(getActivity());


        //알람 실행하는 메소드를  이곳에 넣어서  데이터가 추가 될때에도 그 값을 받아 작동하고,
        // 수정되었을때에도 그값을 받아  작동하도록 하였다.


    }
}

