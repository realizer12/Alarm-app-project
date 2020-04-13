package com.example.lee.alarm_application;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * alarm_application
 * Class: NewsList.
 * Created by leedonghun.
 * Created On 2018-09-28.
 * Description:
 */
public class NewsList extends AppCompatActivity {
    private RecyclerView newsrecyclerview;
    ArrayList<NewsItems>newsItemsArrayList;
    ImageView finishNewList;
    ImageView finishNewList2;
    PowerManager.WakeLock wakeLock;
    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //알람해제 창이 맨처음 나왔을때
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.news_list);

        //뉴스아이템데이터들의 어레이리스트
        newsItemsArrayList=new ArrayList<NewsItems>();
        //뉴스아이템이 정렬되는 리사이클러뷰선언
        newsrecyclerview=(RecyclerView)findViewById(R.id.recyclerView_for_newitem);
        //현재 페이지 종료 버튼
        finishNewList=(ImageView)findViewById(R.id.finishbutton_for_newsrecyclerview);
        //현재페이지 종료버튼 하얀색
       finishNewList2=(ImageView)findViewById(R.id.finishbutton_for_newsrecyclerview2);

        PowerManager pm = (PowerManager)getApplicationContext().getSystemService(POWER_SERVICE);
        //파워매니저의 웨이크락을  full wakelock-(계속 켜져있는 상태 cpu, screen keyboard 모두다 ) 로  바꿈.
        wakeLock= pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "POWER");
        wakeLock.acquire();

        finishNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finishNewList2.setVisibility(View.VISIBLE);
                //종료되고  알람앱을 실행시켜야됨
                //아래에는 인텐트를 이용하여 알람앱을 새로 실행하는 코드가 들어가야됨.
                Intent create=new Intent(getApplicationContext(),MainActivityForAlramApp.class);

                startActivity(create);
                finish();
                wakeLock.release();

            }
        });


        new Description().execute();
    }//on create끝남
    //혹시나 사용자중에  내가 의도했던 x버튼을 안누르고  뒤로가기 버튼을 누를 사람도 있기 때문에
    //뒤로가기 키이벤트에도  x버튼을 눌렀을경우의 행동을 넣어놓았다.
    public void onBackPressed() {
         super.onBackPressed();
        Intent create=new Intent(getApplicationContext(),MainActivityForAlramApp.class);

        startActivity(create);
        finish();
        wakeLock.release();
    }
    private class Description extends AsyncTask<Void,Void,Void>{


        @Override
        protected Void doInBackground(Void... params) {
            try {
                //jsoup을 사용하여  html파싱을 시도하였다.
                Document doc = Jsoup.connect("https://news.naver.com/main/list.nhn?mode=LS2D&mid=sec&sid1=105&sid2=283").get();
                Elements mElementDataSize = doc.select("div[class=list_body newsflash_body]").select("li");
                //필요한 녀석만 꼬집어서 지정
                int mElementSize = mElementDataSize.size(); //목록이 몇개인지 알아낸다. 그만큼 루프를 돌려야 하나깐.

                for (Element elem : mElementDataSize) { //이렇게 요긴한 기능이
                    //네이버 뉴스 it 목록에서  <li> 에서 다시 원하는 데이터를 추출해 낸다.
                    String my_detail_link = elem.select("li dl dt a").attr("href");

                    //네이버 뉴스 it목록을 크롤링 할때 해당 기사들의   올라오는 시간도  크롤링을 하였다.
                    //아래는  1시간이후 에서는 사이트에서  class이름  date is_outdated에  시간 텍스트를 넣어놓았고,
                    //그 이외에는 class이름 date is_new에  넣어놓아기 때문에  아래와 같이 조건문으로 나타나게 하였다.
                    String my_time_upload = elem.select("li dl dd span[class=date is_outdated]").text();
                    if (my_time_upload == null || my_time_upload.equals("")) {
                        my_time_upload = elem.select("li dl dd span[class=date is_new]").text();
                    }

                    //아래는  뉴스의 출처이다.
                    String my_companyname = elem.select("li dl dd span[class=writing]").text();

                    //아래는 뉴스의  제목이다
                    String my_title = elem.select("li dt a").text();

                    //아래는  뉴스의 이미지 url이다.
                    String my_imgUrl = elem.select("li dl dt[class=photo] a img").attr("src");
                    //특정하기 힘들다... 저 앞에 있는집의 오른쪽으로 두번째집의 건너집이 바로 우리집이야 하는 식이다.

                    newsItemsArrayList.add(new NewsItems(my_title,my_imgUrl,my_companyname,my_time_upload,my_detail_link));

                }//for문 끝남.
            }//try문 끝남.
            catch (IOException e){
                e.printStackTrace();
            }//catch문 끝남

            return null;
        }
        //에이싱크 테스크 실행후
        @Override
        protected void onPostExecute(Void result) {
            AdapterForNewsRecyvlerview adapterForNewsRecyvlerview=new AdapterForNewsRecyvlerview(newsItemsArrayList);
            LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            newsrecyclerview.setLayoutManager(layoutManager);
            newsrecyclerview.setAdapter(adapterForNewsRecyvlerview);

        }



    }




}
