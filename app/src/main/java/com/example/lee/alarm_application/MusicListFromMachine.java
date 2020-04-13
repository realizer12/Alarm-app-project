package com.example.lee.alarm_application;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * alarm_application
 * Class: MusicListFromMachine.
 * Created by leedonghun.
 * Created On 2018-09-05.
 * Description:
 */
public class MusicListFromMachine extends AppCompatActivity{
     private final static int LODEAR_ID=0x001;
     private Button  finish_to_select_button;
     private RecyclerView musicrecyclervie;
     private  AudioService mservice;
     private  MusicAdapter musicAdapter;
     String a;
    CursorRecyclerViewAdapter cursorRecyclerViewAdapter;
     private MusicAdapter.AudioItem audioItem;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarlm_sound_list);
        musicAdapter=new MusicAdapter(this,null);
        finish_to_select_button=(Button)findViewById(R.id.finiish_to_select_music_from_machine_button);

        //MP3 리스트가 들어갈  리사이클러뷰 선언
        musicrecyclervie=(RecyclerView)findViewById(R.id.alarm_sound_recyclerview);



        //리니어 레이아웃 매니저로 리니어 레이아웃에다가  세로 방향으로 설정하여  리사이클러뷰에 장착함.
        LinearLayoutManager layoutmaner=new LinearLayoutManager(this);
        layoutmaner.setOrientation(LinearLayoutManager.VERTICAL);
        musicrecyclervie.setLayoutManager(layoutmaner);

         musicrecyclervie.setAdapter(musicAdapter);



        // OS가 Marshmallow 이상일 경우 권한체크를 해야 합니다.
        //이 이전에는  앱 설치 단계에서 권한 부여가되었었음.
        //이때 부터는 특정권한이 필요한 시점에 runtimepermiision 방식으로 권한을 획득해야됨.


        //mashmallow경우 안드로읻 6.0이고 sdk는 23이상이다.
        //개발 도구 수준이 23이니까 api도 23이상이 최소..
        //아래의  build.versioncodes.m은 6.0이상을 나타내는거임.
        //buld.version_codes.m== 23 이랑 같은 말임.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            //checkpermission은 현재 접근권한이 체크되어있는지 여부를 판단한다.
            //아래 if문은 권한 체크가 허용되지 않은것으로 판별될때의  진행코드이다.
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {



                //접근권한을 거부하였을경우  shouldshsowrequespermissionRationale 이 true값으로 변한다.
                //만약에 권한 의사 물어보는 다이얼로그를 거부를 누르고 다시 보이지 않음을  눌렀을 경우에는  permissionrationale이 보이게 되지않으므로 onrequestpermissionREsult에서
                //결과 값을  받은 곳에서 의 처리가 이루어진다.  현재 결과값 받는곳에서  설정창에서 다이얼로그를 설정해야하는 걸로  지정해놨다.
                //다시 보이지않기 창이 된 상태면  바로 결과값인 다이얼로그를 나오도록 한다.
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    //처음 request를 거부하였을시, true값으로 변하여, 계속해서 request값이 뜨도록 하였다.
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);

                }else {
                    //처음에 시작할때 false이므로 이때 보여줄  requestpermission임.
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);

                }

            }

            //아래같은 경우에는  권한 체크를 했을때  허용이된걸로 판별되었을때이다.  허용되었음으로 mp3파일을 가져오게
            //아래에서 지정한 getAudioListFromMediaDatabase를 실행시키도록 하였다.
            else {
                getAudioListFromMediaDatabase();
            }


        }

        //위에서 말했듯이  마쉬멜로우 6.0 이하 버전경우에는 설치 시 접근 권한 이 허용이 됨으로  실행시  바로
        //체크 여부 상관없이 리스트를 mp3가져오는 메소드를 실행한다.
        else {
            // OS가 Marshmallow 이전일 경우 권한체크를 하지 않는다.
            getAudioListFromMediaDatabase();
        }






        // 선택완료 버튼을 눌렀을때 진행되는  코드
        finish_to_select_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             //알람 소리 값을 받아와야됨.

                MusicAdapter.AudioItem audioItem=AudioApplication.getmInstance().getServiceInterface().getAudioItem();

                String alarm_title= audioItem.mTitle;
                String alarm_path=audioItem.mDataPath;
                long alrmsound_id=audioItem.mAlbumId;
                Intent intent=new Intent();
                intent.putExtra("음악아이디",alrmsound_id);
                intent.putExtra("음악경로",alarm_path);
                intent.putExtra("타이틀", alarm_title);
                setResult(397,intent);

                AudioApplication.getmInstance().getServiceInterface().stop();
                finish();

            }
        });




    }

    //selfcheckpermission을 실행하여  권한 체크 여부를 진행하였을때 그결과 값을 받아 다음을 진행하는 코드이다.
    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
                                             @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        //권한에대하여  허용을 받았을 경위다.
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // READ_EXTERNAL_STORAGE 에 대한 권한 획득.
            getAudioListFromMediaDatabase();

            //권한의 관하여  거부를 당하였을 경우이다.
        }else if(grantResults[0] == PackageManager.PERMISSION_DENIED){

            //이중  shouldshowrequestpermissionRationale이  true일 경우에는  거부를 누르면  아래와 같은  토스트 메세지를  띄워서 사용자에게
            //권한에대하여 허용해야한다고 알리고,
            //현재 엑티비티가  꺼지도록 하였디
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)==true) {
                Toast.makeText(this, "기기 내부의 mp3파일을 사용하려면 권한을 허락 하세요!", Toast.LENGTH_LONG).show();
                finish();

                //만약에  다이얼로그  생성되는  것(체크박스로 더이상 보이지 않기) 를 하여  false값이 되어버리면,  결과 값이 넘어가 버린것이므로
                //아래 false 코드를 사용하게 된다.
                //이경우에는 팝업창을 다시 볼수 없어  거부 허용  권한 설정을 못하므로  다이얼로그를 띄어서 사용자가  직접 설정창으로 들어가 권한을 설정하도록
                //설명하였다.
            }else if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)==false){
                new AlertDialog.Builder(this).setTitle("알림").setMessage("저장소 접근 권한이 거부된 상태입니다. 권한 허용을 원하시면 다음과 같이 하세요!." +
                        "                                                                                 " +
                        "                                                                                 " +
                        "홈화면 ---> setting --> apps & notification --> app permission -->  storage  " +
                        "                                                                                  " +
                        "                                                                                  " +
                        "이렇게  순서대로  들어가서셔  본 앱의 아이콘을 찾아  옆에 있는   스위치 버튼을 on으로  바꿔주시면 됩니다.!")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })

                        .setCancelable(false)//alert dialog실행시  백버튼으로 취소 못하게 false로 설정
                        .create()//alert 객체를 만드는거임
                        .show();//그다음 알러트를 보이게 하는 코드 !


            }
        }

    }

    private void getAudioListFromMediaDatabase () {


        //로더(짐꾼)란   비동기 데이터 로딩을  수행하는 추상클래스
        //안드로이드 3.0 이상부터 loadermanger를 제공.
        //각 액티비티 또는 프래그먼트는 로더 맥니저를 한개가질수 있음.
        //loader manager1개에서에서 여러개의 loader가질수 있음.
        //로더들은  비동기 데이터 로딩을 제공함.

        //support library를 사용하고 있기때문에
        //getSupportLoaderManger()사용해서 oncreateLoader 불러옴.
        //아래에 보이는 initLodaer메소드는 두가지의 결과를 갖게 되는데
        //initloader에 넘겨준 ID값을 갖는 LODAER가 이미 존재하면 재사용
        //존재안하면 로드매니저 콜백함수 사용해서 로더 만듬.
        getSupportLoaderManager().initLoader(LODEAR_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            //LoaderCallback<?>()는 LoaderManger가 시작할때, 끌날때 개발자가 원하는 일을 시킬수 있음.
            // 위처러머 loadermanger.initloader에서  선언해야됨.
            @Override
            //로더 매니저 콜백으로  loader 매니저의  시작 지점의 하고 싶은 일을 선언하는 것이다.
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                //cursor는 쿼리 내용을  데이터로 전달하여, 그 답을 가지고 돌아오는  역활.
                //현재 로더를 create되어 cursor를 이용하여 쿼리를 던지는 거임.


                //Uri란 통합자원 식별자
                //
                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                String[] projection = new String[]{
                        MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.ALBUM_ID,
                        MediaStore.Audio.Media.DATA
                };

                //현재 음악관련 데이터베이스에서는 WHERE is_music=1 이런식으로되어있음.
                //selection은 whrere절과 동일하며 IS_MUSIZ값이 1인 내용만 조회한다고함.
                String selection = MediaStore.Audio.Media.IS_MUSIC + " = 1";

                //배치 되는 순서이다. 현재는 타이틀 ㄱ준으로 특수문자-> 한글_---> 영어 순으로 해놓음.
                String sortOrder = MediaStore.Audio.Media.TITLE + " COLLATE LOCALIZED ASC";

                //마지막으로 커서를 통해  쿼리를 던저 받아온  오디오 데이터들을
                //커서로더로 리턴함.
                //이값들을 리사이클러뷰로 나타내기위해서는 커서 리사이클러뷰 어댑터를 따로 만들어줘야됨
                return new CursorLoader(getApplicationContext(), uri, projection, selection, null, sortOrder);
            }

            @Override
            //로더 매니저가 실행되서 oncreatloader가 시작되고,  이후  asyntask (여기선 비동기적 데이터 가저오는거)
            //저게 끝나면 아래 loadfinished가 호출되고,  이곳에는 data가 load된 후, ui update내용을 적으면됨.
            public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
                //가저온 데이터를 오디오 어뎁터에 넣음.
                musicAdapter.swapCursor(data);
            }

            @Override

            //loadermanager,restartLoader()에서 새로운 로더를 가져오고 쓰이지 않는 로더는 destory()하는데 이때 destory() 할때 호출됨.
            public void onLoaderReset(@NonNull Loader<Cursor> loader) {
                musicAdapter.swapCursor(null);

            }

        });
    }










    }

