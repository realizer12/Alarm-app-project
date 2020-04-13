package com.example.lee.alarm_application;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * alarm_application
 * Class: MusicAdapter.
 * Created by leedonghun.
 * Created On 2018-09-05.
 * Description:
 * 뮤직 어댑터는  실제로  리사이클러뷰에서 보여지는 부분을 담당하기 위한  변환기(adapter이다)
 * 로드매니저로  쿼리를 던지고 커서로 가지고온  데이터들을  커서 어댑터로 변환하고 그 변환 값을
 * 뮤직 어댑터로 가지고와서 리사이클러뷰에다가  보낸다.
 *
 */
public class MusicAdapter extends CursorRecyclerViewAdapter<RecyclerView.ViewHolder> {

       //가장 최근의  찍은  커서 값을 가지고 오기 위해 우선 -1 이라는 포지션에 해당 되지 않는 값을 넣은
       //변수를 만들었다.
       // 이  변수를 가지고 어댑터의 포지션값을 받아서 커서  포지션값과  같으면 라디오 버튼이 클릭되고
       // 변수값이  포지션값과  다르면 라디오 버튼의 체크가 풀려
       // 결국 라디오 버튼이 한번만 체크되게 만들었다.
       //라디오 버튼이 라디오 그룹으로 들어갈때는 1개씩만 체크가 가능하지만 리사이클러뷰에서는 라디오 그룹을 사용  못하는것
      // 같아서  이와 같이  설정을 하였다
    private  int lastSelectedPosition = -1;



//뮤직 어댑터의  생성자이다.
    public MusicAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }


    // 생성된 뷰 홀더와  데이터를 묶어 준다.
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder,  Cursor cursor) {
      MusicAdapter.AudioItem audioItem = AudioItem.bindCursor(cursor);

         // 뷰홀더의 setaudioitem을 통해  오디오 아이템 전부 불러와  오디오아이템  bindcursor로  실제 cursor값을 뷰에 보이는 오디오 아이템들에 연결 하였다ㅏ.
        // 이부분 이해 필요.
        ((AudioViewHolder) viewHolder).setAudioItem(audioItem, cursor.getPosition());


        //위해서 말했듯이 현재 커서 포지션과  위의 변수가 같으면 라딩 체크가  체크되도록 만드는 코드이다.
        //원래  라디오버튼 체크 같은 경우에는 커서 포지션으로  해당 mp3값을 다  받아올수 있어서  뷰에서만 보이도록 설정해도 상관 없지만,
        //만약에 상황  값을 못받아올때 체크 값이 true인 값을 가져오도록  하려고  오디오 아이템에  일부러 checked 라는 boolean 변수를 지정하였따.
           if(!(lastSelectedPosition==cursor.getPosition())){
               audioItem.check=false;
           }else if(lastSelectedPosition==cursor.getPosition()){
               audioItem.check=true;
           }

           //위에 말한 이유로  오디오아이템의  체크값이 투루 일때  라디오 버튼 투루 이런식이다.
            if(audioItem.check==true){

                 ((AudioViewHolder) viewHolder).radioButton.setChecked(true);

             }else if(audioItem.check==false){
                 ((AudioViewHolder) viewHolder).radioButton.setChecked(false);
             }



    }


    //뷰 홀더를 만들어서 리사이클러뷰와 연결 시킴.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_list_item, parent, false);



        return new AudioViewHolder(v);
    }

    //mp3 정보를   미디어 데이터 베이스로 부터 가져올때 데이터들을 담기위한  변수들이다.
    public static class AudioItem {
        public long mId; // 오디오 고유 ID
        public long mAlbumId; // 오디오 앨범아트 ID
        public String mTitle; // 타이틀 정보
        public String mArtist; // 아티스트 정보
        public String mAlbum; // 앨범 정보
        public String mDataPath; // 실제 데이터위치
        public boolean check;


//  오디오 아이템의  선언한  변수들을 커서와  묶어주는역활을 한다.
        public static AudioItem bindCursor(Cursor cursor) {
            AudioItem audioItem = new com.example.lee.alarm_application.MusicAdapter.AudioItem();
            audioItem.mId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.AudioColumns._ID));
            audioItem.mAlbumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM_ID));
            audioItem.mTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE));
            audioItem.mArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST));
            audioItem.mAlbum = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM));
            audioItem.mDataPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA));
            audioItem.check=false;
            return audioItem;
        }
    }

    //이해 필요
    public ArrayList<Long> getAudioIds() {
        int count = getItemCount();
        ArrayList<Long> audioIds = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            audioIds.add(getItemId(i));
        }
        return audioIds;
    }


    // 오디오 뷰홀더 클래스
    //뷰 홀더 안에 들어가는 값들 임.
    public class AudioViewHolder extends RecyclerView.ViewHolder {
        private final Uri artworkUri = Uri.parse("content://media/external/audio/albumart");
        private ImageView mImgAlbumArt;
        private TextView mTxtTitle;
        private TextView mTxtSubTitle;
        private  RadioButton radioButton;
        private int mPosition;
        private  boolean checked;





       public AudioViewHolder(View view) {
            super(view);
            mImgAlbumArt = (ImageView) view.findViewById(R.id.img_albumart);
            mTxtTitle = (TextView) view.findViewById(R.id.txt_title);
            mTxtSubTitle = (TextView) view.findViewById(R.id.txt_sub_title);
            radioButton = (RadioButton) view.findViewById(R.id.radioButton_for_music);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //재생목록 등록
                    // 뷰홀더에서 해당  mp3를 골랐을떄
                    //서비스가 실행되고,
                    //그 해당  아이템의 checked값이 true되며, 현재 어뎁터 포지션과 포지션 변수값이 같아짐.
                    //getadapterposition()= cursor.position() 과 같음.
                    AudioApplication.getmInstance().getServiceInterface().setPlayList(getAudioIds());
                    AudioApplication.getmInstance().getServiceInterface().play(mPosition);
                    checked = true;
                    lastSelectedPosition=getAdapterPosition();




                     //오디오 아이템 데이터 값이 달라지므로 데이터 값이 달라졌다고 알림.
                    notifyDataSetChanged();

                }
            });



        }


        public void setAudioItem(AudioItem item, int position) {
            item.check=checked;
            mPosition = position;
            mTxtTitle.setText(item.mTitle);
            mTxtSubTitle.setText(item.mArtist + "(" + item.mAlbum + ")");
            Uri albumArtUri = ContentUris.withAppendedId(artworkUri, item.mAlbumId);
            Picasso.with(itemView.getContext()).load(albumArtUri).error(R.drawable.ic_launcher_background).into(mImgAlbumArt);


        }
    }



}
