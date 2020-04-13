package com.example.lee.alarm_application;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * alarm_application
 * Class: AdapterForNewsRecyvlerview.
 * Created by leedonghun.
 * Created On 2018-09-28.
 * Description:
 */
public class AdapterForNewsRecyvlerview extends RecyclerView.Adapter<AdapterForNewsRecyvlerview.ViewHolder>{

  public static ArrayList<NewsItems> mList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private  TextView companyname13;
        private TextView textView_title;
        private ImageView imageView;
        private  TextView timeforupload;

        public ViewHolder(View itemView) {
            super(itemView);
            timeforupload=(TextView)itemView.findViewById(R.id.timeforuploading);
            companyname13=(TextView)itemView.findViewById(R.id.companyname1);
            textView_title = (TextView) itemView.findViewById(R.id.textView_title);
            imageView=(ImageView)itemView.findViewById(R.id.imageView_img);
        }
    }//VIEWHOLDER 종료

    public AdapterForNewsRecyvlerview(ArrayList<NewsItems> list){
        this.mList=list;
    }

    @NonNull
    @Override
    public AdapterForNewsRecyvlerview.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.newsitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterForNewsRecyvlerview.ViewHolder holder, final  int position) {

        //리사이클러뷰의  아이템을 눌렀을 경우에  관련 기사의 링크를 뉴스 리사이클러뷰 있는 엑티비티에서 jsoup으로  받아 어레이리스트에 넣었고,
        //그 데이터를 어뎁터로 가지고와서 해당 링크를 타고 해당 기사를 보여준다.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.itemView.getContext(),ShowNewsReport.class);
                intent.putExtra("뉴스링크",String.valueOf(mList.get(position).getDetail_link1()));
                view.getContext().startActivity(intent);


            }
        });


        //뉴스 시간
        holder.timeforupload.setText("- "+String.valueOf(mList.get(position).getTime1()));

        //뉴스사 이름
        holder.companyname13.setText(String.valueOf(mList.get(position).getCompanyname1()));

        //뉴스 이름
        holder.textView_title.setText(String.valueOf(mList.get(position).getTitle1()));

        //뉴스 사진
        //만약에 이미지 유알엘이  비어있다면  기존의  앱에서 제공하는 이미지로 대체한다.
        if (mList.get(position).getImg_url1().isEmpty()) {
            holder.imageView.setImageResource(R.drawable.ic_launcher_foreground);
        } else{
            Picasso.with(holder.itemView.getContext()).load(mList.get(position).getImg_url1())
                    .resize(300,300)
                    .into(holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
