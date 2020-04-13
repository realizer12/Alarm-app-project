package com.example.lee.alarm_application;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class setting_screen extends Fragment {


    public setting_screen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView=(ViewGroup)inflater.inflate(R.layout.fragment_setting_screen,container,false);
        ImageView clock=(ImageView)rootView.findViewById(R.id.settingalarmclock);
        Animation rotate= AnimationUtils.loadAnimation(getActivity(),R.anim.rotation);
        clock.startAnimation(rotate);


        Button button_call=(Button)rootView.findViewById(R.id.button5);
        button_call.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Intent.ACTION_DIAL);
               intent.setData(Uri.parse("tel:01073807810"));
               try {
                   startActivity(intent);
               }catch(Exception e){
                   e.printStackTrace();
               }
           }
       });

        Button button_online=(Button)rootView.findViewById(R.id.button4);
        button_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
               intent.setData(Uri.parse("http://naver.com"));
                try {
                    startActivity(intent);
                }catch(Exception e){
                    e.printStackTrace();
                }



            }
        });




    return rootView;

    }

}
