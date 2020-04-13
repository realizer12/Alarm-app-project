package com.example.lee.alarm_application;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.CharacterPickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;

/**
 * Project name: alarm_application
 * Class: AlarmSoundList
 * Created by lee.
 * Created On 2018-08-20.
 * Description: 기본적으로  알람앱에서 제공하는 알람 사운드와
 * 핸드폰 내에있는 mp3파일을  알람소리로 고를수 있게 리스트를 보여주는 액티비티이다.
 */
public class AlarmSoundList extends AppCompatActivity {
    public static Activity soundlist;

    Button select_finish_bt;
    LinearLayout no_sound;
    LinearLayout sound_from_my_music;
    LinearLayout siren1_sound;
    LinearLayout siren2_sound;
    RadioButton radioButton_for_nosound;
    RadioButton radioButton_for_my_music;
    RadioButton radioButton_for_siren1;
    RadioButton getRadioButton_for_siren2;
    public static MediaPlayer mediaPlayer1;
    public static MediaPlayer mediaPlayer2;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_list_in_alarmapp);
        no_sound=(LinearLayout)findViewById(R.id.no_music_linear);
        sound_from_my_music=(LinearLayout)findViewById(R.id.music_from_my_machine_linear);
        siren1_sound=(LinearLayout)findViewById(R.id.siren1_linear);
        siren2_sound=(LinearLayout)findViewById(R.id.siren2_linear);



        radioButton_for_nosound=(RadioButton)findViewById(R.id.radioButton_for_no_sound);
        radioButton_for_my_music=(RadioButton)findViewById(R.id.radioButton_for_music_in_my_phone);
        radioButton_for_siren1=(RadioButton)findViewById(R.id.radioButton_for_siren1);
        getRadioButton_for_siren2=(RadioButton)findViewById(R.id.radioButton_for_siren2);


        //알람 소리 정할때 맨처음에 라디오 버튼 아무것도 선택되지 않도록 설정한다.
        radioButton_for_siren1.setChecked(false);
        radioButton_for_my_music.setChecked(false);
        radioButton_for_nosound.setChecked(false);
        getRadioButton_for_siren2.setChecked(false);

        mediaPlayer1=MediaPlayer.create(this,R.raw.siren1);
        mediaPlayer2=MediaPlayer.create(this,R.raw.siren2);








        //선택완료 버튼을 눌렀을경우에 일어나는 코드이다.
        select_finish_bt=(Button)findViewById(R.id.music_list_in_alarm_finish_button);
        select_finish_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //알람사운드 선택하는 곳에서
                //알람음 없음을  선택할경우
                if(radioButton_for_nosound.isChecked()==true){
                    Intent intent=new Intent();
                    String a="알람 사운드 사용 x";
                    intent.putExtra("알람소리없음",a);
                    setResult(930,intent);

                    //기본 제공음들이 혹시나 재생되고 있을 경우를 대비하여  멈춤 처리를 하였다.
                    mediaPlayer1.stop();
                    mediaPlayer2.stop();
                    finish();
                }

                //싸이렌 소리1 이   체크되었을 경우에 진행되는 코드이다.
                if(radioButton_for_siren1.isChecked()==true){
                      Intent intent=new Intent();
                      String b="기본싸이렌소리1";
                      intent.putExtra("알람소리싸이렌1",b);
                      setResult(931,intent);


                      mediaPlayer1.stop();
                      mediaPlayer2.stop();

                    finish();
                }

                //싸이렌 소리 2가  체크되었을때  진행되는 코드이다.
               if(getRadioButton_for_siren2.isChecked()==true){

                      Intent intent=new Intent();
                      String c="기본싸이렌소리2";
                     intent.putExtra("알람소리싸이렌2",c);
                     setResult(932,intent);

                     mediaPlayer1.stop();
                     mediaPlayer2.stop();

                     finish();
               }

            }
        });










        //음악 없음을 눌렀을때 나오는 코드이다.
       no_sound.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               //라디오 버튼 상태가 바뀜.
               radioButton_for_nosound.setChecked(true);
               radioButton_for_my_music.setChecked(false);
               radioButton_for_siren1.setChecked(false);
               getRadioButton_for_siren2.setChecked(false);


               //기본 제공음악의  플레이 상태가 바뀜.
               if(mediaPlayer1.isPlaying()==true){
                   mediaPlayer1.pause();
                   mediaPlayer1.seekTo(0);
               }
             if(mediaPlayer2.isPlaying()==true){
                   mediaPlayer2.pause();
                   mediaPlayer2.seekTo(0);               }
                   }
       });


       //내 음악 파일  보기를 눌렀을 경우.
       sound_from_my_music.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               radioButton_for_nosound.setChecked(false);
               radioButton_for_my_music.setChecked(true);
               radioButton_for_siren1.setChecked(false);
               getRadioButton_for_siren2.setChecked(false);
               mediaPlayer1.stop();
               mediaPlayer2.stop();

              //여기서 부터는  기기 내의 mp3파일 목록으로 넘어가서 진행되기 위한 코드이다.
               //플래그를 이용해서  MusicListFrommachine 엑티비티가 생성되면 그곳에서 받은 값을 앞으로 넘기도록 하였다.
               Intent intent=new Intent(getApplicationContext(),MusicListFromMachine.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);

               startActivity(intent);

              finish();


           }

       });

        //싸이렌 2를  골랐을때
       siren2_sound.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               radioButton_for_nosound.setChecked(false);
               radioButton_for_my_music.setChecked(false);
               radioButton_for_siren1.setChecked(false);
               getRadioButton_for_siren2.setChecked(true);

               if(mediaPlayer1.isPlaying()==false){

                   mediaPlayer2.start();
               }else if(mediaPlayer1.isPlaying()==true){

                   mediaPlayer1.pause();
                   mediaPlayer1.seekTo(0);
                   mediaPlayer2.start();


               }

           }
       });

       siren1_sound.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               radioButton_for_nosound.setChecked(false);
               radioButton_for_my_music.setChecked(false);
               radioButton_for_siren1.setChecked(true);
               getRadioButton_for_siren2.setChecked(false);

               if(mediaPlayer2.isPlaying()==false){

                   mediaPlayer1.start();
               }else if(mediaPlayer2.isPlaying()==true){

                   mediaPlayer2.pause();
                   mediaPlayer2.seekTo(0);
                   mediaPlayer1.start();


               }



           }
       });



    }
}
