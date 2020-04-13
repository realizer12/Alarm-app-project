package com.example.lee.alarm_application;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

/**
 * alarm_application
 * Class: ShowNewsReport.
 * Created by leedonghun.
 * Created On 2018-09-28.
 * Description:
 */
public class ShowNewsReport extends AppCompatActivity {
     ImageView finishbtn;
      WebView newswebview;
     ImageView sharenews;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsitem_layout);
         finishbtn=(ImageView)findViewById(R.id.backbtn);
         newswebview=(WebView)findViewById(R.id.webview_for_news);
         sharenews=(ImageView)findViewById(R.id.sharenews) ;



        Intent intent=getIntent();
        final String url=intent.getStringExtra("뉴스링크");
        newswebview.setWebViewClient(new WebViewClient());

        newswebview.loadUrl(url);

        sharenews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,url);

                Intent chooser = Intent.createChooser(intent, "기사 공유하기");
                startActivity(chooser);


            }
        });

        finishbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            finish();
            }
        });





    }
}

