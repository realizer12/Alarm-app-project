package com.example.lee.alarm_application;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Context.ALARM_SERVICE;
import static com.example.lee.alarm_application.alarm.cancelAlarm;
import static com.example.lee.alarm_application.basic_alarm_screen.alarmManagers;
import static com.example.lee.alarm_application.basic_alarm_screen.context2;
import static com.example.lee.alarm_application.basic_alarm_screen.explainfordeletandchange;
import static com.example.lee.alarm_application.basic_alarm_screen.intentArrayList;
import static com.example.lee.alarm_application.basic_alarm_screen.pendingIntent;
import static com.example.lee.alarm_application.basic_alarm_screen.releasealarm;

/**
 * alarm_application
 * Class: Myadapter
 * Created by leedonghun.
 * Created On ${Date}
 * Description:알람 리스트가 들어가는 리사이클러뷰의
 * 데이터 변환을 위한  어뎁터 클래스이다.
 */
public class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder>{
   public static ArrayList<MakedAlarm> makedAlarms_arraylist=new ArrayList<MakedAlarm>();
    FragmentActivity activity;


    int adpeterposition;
    public Myadapter(FragmentActivity activity, ArrayList<MakedAlarm> makedAlarms_arraylist) {
             this.activity=activity;

             //makealarm 을 사용하는데  null 값이 떠서  makearraylist가  null값이 나오면
             //새로 makealarmlist를  만들어내기로 했따.
        if (makedAlarms_arraylist == null) {
            makedAlarms_arraylist = new ArrayList<MakedAlarm>();
        }

                 this.makedAlarms_arraylist=makedAlarms_arraylist;

    }


    //아이템 리스트.xml 로 만들어 놓은 아이템리스트모양을 레이아웃 인플레이터로 부퓰려서   vuewHolder에  장착 시킨다.
    @NonNull
    @Override
    public Myadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_alarm_item,parent,false);
        return new ViewHolder(v);
    }



    //리사이클러 뷰 홀더와 보내질 값들을 묶어주는 메소드이다.

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull final Myadapter.ViewHolder holder, final int position) {
        holder.bindData(makedAlarms_arraylist.get(position));

        //아이템에 들어있는 스위치가 자기 포지션에 맞춰서 스위치 형태를 유지하게 하기위한
        //코드들이다.

        holder.switch1.setOnCheckedChangeListener(null);

        //기본 boolean 값은 false 이기때문에  앱을 실행하여서 아이템을 추가하면 체크가 안되어있는 상태로 나온다.
        //하지만 보통 알람 앱 같은 경우에는 알람을 새로 설정하면 맨 처음에는 알람이 체크 되어 잇는 상태로 나오기 때문에 그와 관련된 처리를 하였따.
        holder.switch1.setChecked(!makedAlarms_arraylist.get(position).isSelected());


        //viewholder 안에 container라는 리레이티브레이아웃(리사이클러뷰 아이템의 배경을 style을 담고 있는 레이아웃)이다.
        //이 레이아웃을 뷰홀더안에 있으니까 아래 viewholder 클래스에서 선언을 하였고, 이곳 bindviewholder에서 데이터를 이용한 동적 처리를 하였다.
        //동적처리할 내용은 스위치가 체크되지 않았을때 어두운 배경 스타일이 container 리레이티브 레이아웃에 나오도록 하는것이다.
        //아래는 기존의 container가 가지고 있는 배경색이다.
        holder.container.setBackgroundResource(R.drawable.alarm_item_style);



        //만약에 setchecekd()안에 들어가는 makedAlarms_arraylist.get(position).isSelected()이 true 일때 와  false 일때를 나누어서 배경색이 바뀌도록 설정한다.
        if(!makedAlarms_arraylist.get(position).isSelected()){
            holder.container.setBackgroundResource(R.drawable.alarm_item_style);
            holder.date.setTextColor(Color.rgb(0,0,0));
            holder.memo.setTextColor(Color.rgb(0,0,0));
            holder.time.setTextColor(Color.rgb(0,0,0));
            holder.ways.setTextColor(Color.rgb(0,0,0));

        }else if(makedAlarms_arraylist.get(position).isSelected()){

            //스위치가 OFF상태일때 아이템의 보이는  색깔을 변경해주어  OFF되어 사용되지 못할것 같은 느낌을 주었다.
            holder.date.setTextColor(Color.rgb(88,85,85));
            holder.memo.setTextColor(Color.rgb(88,85,85));
            holder.time.setTextColor(Color.rgb(88,85,85));
            holder.ways.setTextColor(Color.rgb(88,85,85));

            holder.container.setBackgroundResource(R.drawable.alarm_item_style_black);

        }

        //스위치의 변경을 받아들이는 메소드이다.
        //스위치 버튼이 눌러지면 위에서 setchecked()안에 들어가는 내용이  ,makedAlarms_arraylist.get(position).isSelected()안에서 일어나는 내용임.
        //position--==> 사실 holder.getadpterposition 이고, isSelected==> setSelected(!ischecked)이다.


        holder.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()

           {
               @Override
               public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked){

               //체크가 눌리게 되면 아래 와 같이 변하는데  맨처음에  false 였을니까
               //아래와 같이 ischecked는 (기본값은 false이지만 현재는 투르였던 값을 체인지 변환 메소드로 받아오는거여서 ischecked 는 true상태이다.) --> !ischeckd-->false로바뀌고
               //setcheckd안에 들어가는 값은 false로 바뀌는데
               makedAlarms_arraylist.get(holder.getAdapterPosition()).setSelected(!isChecked);

               //위의  배경 바뀌는 코드는 변화 없이 처음 만들어질때 모습을 나타내는 것어서 여기에는 영향을 끼치지 않으므로 한번더 true false에 따른 배경색 변화를 말해준다.
               if (!makedAlarms_arraylist.get(holder.getAdapterPosition()).isSelected()) {
                   holder.container.setBackgroundResource(R.drawable.alarm_item_style);
                   holder.date.setTextColor(Color.rgb(0, 0, 0));
                   holder.memo.setTextColor(Color.rgb(0, 0, 0));
                   holder.time.setTextColor(Color.rgb(0, 0, 0));
                   holder.ways.setTextColor(Color.rgb(0, 0, 0));
                   Log.v("알람","켜짐");


                   //알람을 on하면  팬딩인덴트가  넣어져있던 어레이리스트를 모두 조회하고
                   //알람이 올라가져있는 팬딩인텐트는  삭제하고 다시 알람을 재등록한다.
                   for(int i=0; i<intentArrayList.size(); i++) {


                       PendingIntent pendingIntent = intentArrayList.get(i);

                       if (pendingIntent != null) {

                           try {
                               alarmManagers[i].cancel(pendingIntent);
                               pendingIntent.cancel();
                           }catch (NullPointerException e){

                           }

                       }
                   }
                   releasealarm(holder.itemView.getContext());

               } else if (makedAlarms_arraylist.get(holder.getAdapterPosition()).isSelected()) {

                   //스위치가 OFF상태일때 아이템의 보이는  색깔을 변경해주어  OFF되어 사용되지 못할것 같은 느낌을 주었다.
                   holder.date.setTextColor(Color.rgb(88, 85, 85));
                   holder.memo.setTextColor(Color.rgb(88, 85, 85));
                   holder.time.setTextColor(Color.rgb(88, 85, 85));
                   holder.ways.setTextColor(Color.rgb(88, 85, 85));

                   holder.container.setBackgroundResource(R.drawable.alarm_item_style_black);

                   //알람을 off 하면  팬딩인덴트가  넣어져있던 어레이리스트를 모두 조회하고
                   //알람이 올라가져있는 팬딩인텐트는  삭제하고 다시 알람을 재등록한다.
                   for(int i=0; i<intentArrayList.size(); i++) {


                       PendingIntent pendingIntent = intentArrayList.get(i);

                       if (pendingIntent != null) {

                           try {
                               alarmManagers[i].cancel(pendingIntent);
                               pendingIntent.cancel();
                           }catch (NullPointerException e){

                           }
                       }
                   }
                   releasealarm(holder.itemView.getContext());
                   Log.v("알람","꺼짐");
               }

           }
           });



    }

    //리사이클러뷰의 아이템 개수는  어레이리스트의 사이즈와 비례한다.
    @Override
    public int getItemCount() {



        return makedAlarms_arraylist.size();

    }



//만들어진 뷰홀더를 가지고 그안의 내용들을 선언한다.
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private TextView time;
        private TextView ways;
        private TextView date;
        private  TextView memo;
        private Switch switch1;
        private  RelativeLayout container;

        public ViewHolder(View itemView) {
            super(itemView);

             container=(RelativeLayout)itemView.findViewById(R.id.alarm_entire_container);
             switch1=(Switch)itemView.findViewById(R.id.using_alarm_switch);
             time=(TextView)itemView.findViewById(R.id.alarm_time_item);
             ways=(TextView)itemView.findViewById(R.id.wayfor_finish_item);
             date=(TextView)itemView.findViewById(R.id.alarm_data_item);
             memo=(TextView)itemView.findViewById(R.id.memo_item);

            itemView.setOnCreateContextMenuListener(this);
        }
        public void bindData(MakedAlarm makedAlarm){

            //아이템 텍스트에 넣을때 , 오전오후를 나누고 만약 23시일때 11시로 보이게 하기위하여
            //어레이리스트안의 값을 수정하여 텍스트에 넣었다.
            String 오전오후 = "";
            int hour=makedAlarm.getAlarmtime();
            if(makedAlarm.getAlarmtime()>=12){
                오전오후="오후";
                if(makedAlarm.getAlarmtime()>12) {
                  hour  = makedAlarm.getAlarmtime() - 12;
                }
            }else if(makedAlarm.getAlarmtime()<12){
                오전오후="오전";

            }
            //리사이클러뷰의 아이템에서 시간 넣는 부분의
            time.setText(""+오전오후+"  "+hour+"시 "+makedAlarm.getAlarmminute()+"분"+"");
            ways.setText(makedAlarm.getWayforfinish_alarm());
            memo.setText(makedAlarm.getMemo());

            //안드로이드 캘린더 달력은 +1밖에 안됨.
            //특정일 알람 설정을 했을 경우  또는 안했을 경우를  나누어서 계산함.
            if(makedAlarm.isUsingspecificdate()==true) {
                int month = makedAlarm.getSpecificmonth() + 1;
                date.setText("" + makedAlarm.getSpecificyear() + ". " + month + ". " + makedAlarm.getSpecificday() + ". 알람울림");
            }else if(makedAlarm.isUsingspecificdate()==false){
                //일단 특정일을 정하지 않았을 경우에는 날짜 텍스트에 들어가는 값을 없도록 해놈.


                //전체가 다 클릭되었을 경우에는 텍스트에 매일이라고 나오게 만든다.
             if(makedAlarm.isMonday()==true && makedAlarm.isTuesday()==true && makedAlarm.isWednesday()==true && makedAlarm.isThursday()==true &&makedAlarm.isFriday()==true
                     && makedAlarm.isSaturday()==true && makedAlarm.isSunday()==true){
                      date.setText("매일");

                      //일,토 주말만 클릭이 되어있을 경우이다.
             }else if(makedAlarm.isMonday()==false && makedAlarm.isTuesday()==false && makedAlarm.isWednesday()==false && makedAlarm.isThursday()==false &&makedAlarm.isFriday()==false
                     && makedAlarm.isSaturday()==true && makedAlarm.isSunday()==true){

                 date.setText("주말");

                 //그밖의  위 경우를 뺀 경우  반복내용을 텍스트에 나타내는 코드
             }else {

                 date.setText(makedAlarm.getMonday_text() + makedAlarm.getTuseday_text() + makedAlarm.getWednesday_text() +
                         makedAlarm.getThursday_text() + makedAlarm.getFriday_text() + makedAlarm.getSaturday_text() + makedAlarm.getSunday_text());
             }
            }
        }

        //길게 눌렀을경우 편집및 삭제를 할수 있는 컨텐스트 메뉴를 호출하는 코드이다.
        public void onCreateContextMenu(ContextMenu contextMenu, View v, ContextMenu.ContextMenuInfo contextMenuInfo) {

             //수정 하는 버튼과  삭제버튼 두개를 메뉴로 설정하였다.
            MenuItem Edit=contextMenu.add(Menu.NONE,1001,1,"알람 수정");
            MenuItem Delete=contextMenu.add(Menu.NONE,1002,2,"알람 삭제");


            //각 수정과 삭제 아이템은 onedutmenu라는 메뉴아이템클릭리스너의 객체를 장착한다.
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);

            adpeterposition=getAdapterPosition();
        }


        //oneditmenu 라는 메뉴 아이템 클릭리스너 객체는 아래와 같은 코드를 가지고있다.
  private final MenuItem.OnMenuItemClickListener onEditMenu= new MenuItem.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case 1001:
                Intent intent=new Intent(activity,ChangeMakedAlarm.class);
                Activity origin=(Activity) activity;

                intent.putExtra("latitude_tochange",makedAlarms_arraylist.get(getAdapterPosition()).getLatitude());
                intent.putExtra("longtitude_tochange",makedAlarms_arraylist.get(getAdapterPosition()).getLongtitude());

                intent.putExtra("hour",makedAlarms_arraylist.get(getAdapterPosition()).getAlarmtime());
                intent.putExtra("minute",makedAlarms_arraylist.get(getAdapterPosition()).getAlarmminute());
                intent.putExtra("memo",makedAlarms_arraylist.get(getAdapterPosition()).getMemo());
                intent.putExtra("wayforkill",makedAlarms_arraylist.get(getAdapterPosition()).getWayforfinish_alarm());
                intent.putExtra("alarmpushed",makedAlarms_arraylist.get(getAdapterPosition()).isAlamrpushed());
                intent.putExtra("alarmvibration",makedAlarms_arraylist.get(getAdapterPosition()).isVibration());
                intent.putExtra("year",makedAlarms_arraylist.get(getAdapterPosition()).getSpecificyear());
                intent.putExtra("month",makedAlarms_arraylist.get(getAdapterPosition()).getSpecificmonth());
                intent.putExtra("day",makedAlarms_arraylist.get(getAdapterPosition()).getSpecificday());
                intent.putExtra("specificdate",makedAlarms_arraylist.get(getAdapterPosition()).isUsingspecificdate());
                intent.putExtra("mon",makedAlarms_arraylist.get(getAdapterPosition()).isMonday());
                intent.putExtra("tue",makedAlarms_arraylist.get(getAdapterPosition()).isTuesday());
                intent.putExtra("wed",makedAlarms_arraylist.get(getAdapterPosition()).isWednesday());
                intent.putExtra("thur",makedAlarms_arraylist.get(getAdapterPosition()).isThursday());
                intent.putExtra("fri",makedAlarms_arraylist.get(getAdapterPosition()).isFriday());
                intent.putExtra("sat",makedAlarms_arraylist.get(getAdapterPosition()).isSaturday());
                intent.putExtra("sun",makedAlarms_arraylist.get(getAdapterPosition()).isSunday());
                intent.putExtra("monday_text",makedAlarms_arraylist.get(getAdapterPosition()).getMonday_text());
                intent.putExtra("tuseday_text",makedAlarms_arraylist.get(getAdapterPosition()).getTuseday_text());
                intent.putExtra("wednesday_text",makedAlarms_arraylist.get(getAdapterPosition()).getWednesday_text());
                intent.putExtra("thursday_text",makedAlarms_arraylist.get(getAdapterPosition()).getThursday_text());
                intent.putExtra("friday_text",makedAlarms_arraylist.get(getAdapterPosition()).getFriday_text());
                intent.putExtra("saturday_text",makedAlarms_arraylist.get(getAdapterPosition()).getSaturday_text());
                intent.putExtra("sunday_text",makedAlarms_arraylist.get(getAdapterPosition()).getSunday_text());

                intent.putExtra("alarm_sound_title",makedAlarms_arraylist.get(getAdapterPosition()).getAlarmsound_text());
                intent.putExtra("mymusic_alarm",makedAlarms_arraylist.get(getAdapterPosition()).getAlarm_sound_id());
                intent.putExtra("mymusic_path",makedAlarms_arraylist.get(getAdapterPosition()).getAlarm_music_path());



                intent.putExtra("barcodenumber",makedAlarms_arraylist.get(getAdapterPosition()).getBarcodenumber());
                intent.putExtra("barcodephoto",makedAlarms_arraylist.get(getAdapterPosition()).getBarcodestuffphotouri());
                origin.startActivityForResult(intent,333);
                notifyDataSetChanged();

                break;



            case 1002:
                makedAlarms_arraylist.remove(getAdapterPosition());
                if(makedAlarms_arraylist.size()<=0){
                    explainfordeletandchange.setVisibility(View.INVISIBLE);
                }
                notifyItemRemoved(getAdapterPosition());
                notifyItemRangeChanged(getAdapterPosition(), makedAlarms_arraylist.size());


                //알람을 삭제를 하면  팬딩인덴트가  넣어져있던 어레이리스트를 모두 조회하고
                //알람이 올라가져있는 팬딩인텐트는  삭제하고 다시 알람을 재등록한다.
                for(int i=0; i<intentArrayList.size(); i++) {


                    PendingIntent pendingIntent = intentArrayList.get(i);

                    if (pendingIntent != null) {



                        try {
                            alarmManagers[i].cancel(pendingIntent);
                            pendingIntent.cancel();
                        }catch (NullPointerException E){

                        }
                    }
                }
                releasealarm(itemView.getContext());
                break;
        }
          return true;
      }
  };



    }//뷰홀더 클래스 닫힘.
    basic_alarm_screen a=new basic_alarm_screen();
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==333){
            if(resultCode==95){

                //수정 액티비티에서 체인지된 메모 값을 가져와 어레이리스트에 바꿔 넣는 다.
                String memofix=(String)data.getStringExtra("changed_memo");
                makedAlarms_arraylist.get(adpeterposition).setMemo(memofix);

                //수정 액티비티에서 체인지된 알람미루기 여부를 가져와 어레이리스트에 바꿔 넣는다.
                boolean alamrpush=(boolean)data.getExtras().get("changed_push");
                makedAlarms_arraylist.get(adpeterposition).setAlamrpushed(alamrpush);

                //수정 액티비티에서 체인지된 알람진동여부 를 가져와 어레이리스트에 바꿔넣는다
                boolean alarmvib=(boolean)data.getBooleanExtra("changed_vibration",false);
                makedAlarms_arraylist.get(adpeterposition).setVibration(alarmvib);


                //알람시간  값
                int alarm_time=data.getIntExtra("chaged_time",0);
                makedAlarms_arraylist.get(adpeterposition).setAlarmtime(alarm_time);

                int alarm_minute=data.getIntExtra("changed_minute",0);
                makedAlarms_arraylist.get(adpeterposition).setAlarmminute(alarm_minute);


                //종료방법 설정여부
                String killing_way_changed=data.getStringExtra("changed_killingway");
                makedAlarms_arraylist.get(adpeterposition).setWayforfinish_alarm(killing_way_changed);

                //날짜지정 설정여부
                boolean specificdate=data.getBooleanExtra("changed_specific_date_boolean",false);
                makedAlarms_arraylist.get(adpeterposition).setUsingspecificdate(specificdate);

                //날짜 연, 달, 일  값
                int year_changed=data.getIntExtra("changed_year2",0);
                makedAlarms_arraylist.get(adpeterposition).setSpecificyear(year_changed);

                int month_changed=data.getIntExtra("changed_month2",0);
                makedAlarms_arraylist.get(adpeterposition).setSpecificmonth(month_changed);

                int day_changed=data.getIntExtra("changed_day2",0);
                makedAlarms_arraylist.get(adpeterposition).setSpecificday(day_changed);


                //요일별  체크 여부
                boolean monday_changed=(boolean)data.getBooleanExtra("changed_monday",false);
                makedAlarms_arraylist.get(adpeterposition).setMonday(monday_changed);

                boolean tuseday_changed=(boolean)data.getBooleanExtra("changed_tuseday",false);
                makedAlarms_arraylist.get(adpeterposition).setTuesday(tuseday_changed);

                boolean wednesday_changed=(boolean)data.getBooleanExtra("changed_wednesday",false);
                makedAlarms_arraylist.get(adpeterposition).setWednesday(wednesday_changed);

                boolean thursday_changed=(boolean)data.getBooleanExtra("changed_thursday",false);
                makedAlarms_arraylist.get(adpeterposition).setThursday(thursday_changed);

                boolean friday_changed=(boolean)data.getBooleanExtra("changed_friday",false);
                makedAlarms_arraylist.get(adpeterposition).setFriday(friday_changed);

                boolean saturday_changed=(boolean)data.getBooleanExtra("changed_saturday",false);
                makedAlarms_arraylist.get(adpeterposition).setSaturday(saturday_changed);

                boolean sunday_changed=(boolean)data.getBooleanExtra("changed_sunday",false);
                makedAlarms_arraylist.get(adpeterposition).setSunday(sunday_changed);

                //요일 반복으로 선택시 아이템에 요일값에 보일 요일 스트링값
                String monday_text=(String)data.getStringExtra("changed_mon_text");
                makedAlarms_arraylist.get(adpeterposition).setMonday_text(monday_text);

                String tuseday_text=(String)data.getStringExtra("changed_tue_text");
                makedAlarms_arraylist.get(adpeterposition).setTuseday_text(tuseday_text);

                String wednesday_text=(String)data.getStringExtra("changed_wed_text");
                makedAlarms_arraylist.get(adpeterposition).setWednesday_text(wednesday_text);

                String thursday_text=(String)data.getStringExtra("changed_thur_text") ;
                makedAlarms_arraylist.get(adpeterposition).setThursday_text(thursday_text);

                String friday_text=(String)data.getStringExtra("changed_fri_text");
                makedAlarms_arraylist.get(adpeterposition).setFriday_text(friday_text);

                String saturday_text=(String)data.getStringExtra("changed_sat_text");
                makedAlarms_arraylist.get(adpeterposition).setSaturday_text(saturday_text);

                String sunday_text=(String)data.getStringExtra("changed_sun_text");
                makedAlarms_arraylist.get(adpeterposition).setSunday_text(sunday_text);

                String alarm_sound_title=(String)data.getStringExtra("changed_alarm_title");
                makedAlarms_arraylist.get(adpeterposition).setAlarmsound_text(alarm_sound_title);

                long mymusic_alarm_id=(long)data.getLongExtra("changed_mymusic_alarm_id",0);
                makedAlarms_arraylist.get(adpeterposition).setAlarm_sound_id(mymusic_alarm_id);

                String mymusic_alarm_path=(String)data.getStringExtra("changed_mymusic_alarm_path");
                makedAlarms_arraylist.get(adpeterposition).setAlarm_music_path(mymusic_alarm_path);

                String barcode_changednumber=(String)data.getStringExtra("changed_barcodenumber") ;
                makedAlarms_arraylist.get(adpeterposition).setBarcodenumber(barcode_changednumber);


                String barcode_changedphoto=(String)data.getStringExtra("changed_barcodephoto") ;
                makedAlarms_arraylist.get(adpeterposition).setBarcodestuffphotouri(barcode_changedphoto);

                double latitude_fromchange=(double)data.getDoubleExtra("changed_latitude",0.0);
                makedAlarms_arraylist.get(adpeterposition).setLatitude(latitude_fromchange);


                double longtitude_fromchange=(double)data.getDoubleExtra("changed_lontitude",0.0);
                makedAlarms_arraylist.get(adpeterposition).setLongtitude(longtitude_fromchange);

                //데이터가 수정된것을 시스템에게 알림.
                notifyDataSetChanged();


            }
        }
    }



}
