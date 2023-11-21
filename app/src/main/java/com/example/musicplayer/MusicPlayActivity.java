package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MusicPlayActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "musicPlayActivity";
    private ImageView next,last,pause;
    private TextView singer,song;
    private RecyclerView musicReclcle;
    private MusicAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    MyMediaPlayer musiclistPlayer;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);
//         musiclistPlayer=new MyMediaPlayer();
        musiclistPlayer=Myapplication.musicplayer;
        initView();
        initClick();
        initsetAdapter();
//        retDate();
        welTomusicApter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Myapplication.setMusicSongInPosition(musiclistPlayer.onMusicSongPosition());
/*        musiclistPlayer.onMusicStop();
        musiclistPlayer.onMusicDesory();*/
        super.onStop();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
/*        musiclistPlayer=new MyMediaPlayer();
        musiclistPlayer.onMusicPrepare();
        musiclistPlayer.onMusicSeekTo(Myapplication.getMusicSongInPosition());
        musiclistPlayer.onMusicStart();*/
        musiclistPlayer.onMusicSetListener(song,singer);
    }

    private void welTomusicApter() {
        adapter.setOnItemClickListener(new MusicAdapter.onItemClickListener() {
            @Override
            public void onItemListener(View view, int position) {
                Myapplication.setMusicPosition(position);
                musiclistPlayer.onMusicChange(song,singer);
            }
        });
    }


    @SuppressLint("NotifyDataSetChanged")
    private void initsetAdapter() {
        adapter=new MusicAdapter(MusicPlayActivity.this,Myapplication.getDataMusicList());
//        确定布局管理器
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        musicReclcle.setLayoutManager(linearLayoutManager);
        musicReclcle.setAdapter(adapter);
    }
    private void initView(){
        next=findViewById(R.id.music_right);
        last=findViewById(R.id.music_left);
        pause=findViewById(R.id.music_Pause);
        song=findViewById(R.id.music_song);
        singer=findViewById(R.id.music_singer);
        musicReclcle=findViewById(R.id.music_recycle);
    }
    private void initClick(){
        next.setOnClickListener(this);
        last.setOnClickListener(this);
        pause.setOnClickListener(this);
        findViewById(R.id.bottom_music).setOnClickListener(this);

    }

    @SuppressLint("QueryPermissionsNeeded")
    @Override
    public void onClick(View view) {
        if (view.getId() ==R.id.music_right) {
            musiclistPlayer.PlayeronMusicNext(song,singer);
        }
        if (view.getId() ==R.id.music_left) {
            musiclistPlayer.PlayeronMusicLast(song,singer);
        }
        if (view.getId() ==R.id.music_Pause) {
//            currentPausePosition：表示播放的音乐的位置
            musiclistPlayer.PlayeronMusicPause(pause);
        }
        if (view.getId() ==R.id.bottom_music) {
            Log.d(TAG, "onClick: bottom");
            if (musiclistPlayer!=null&&Myapplication.getMusicPosition()>=0&&Myapplication.getMusicPosition()<=Myapplication.getDataMusicList().size()-1) {
                Intent intent=new Intent(MusicPlayActivity.this,PersionalMusicPlay.class);
                if (intent.resolveActivity(getPackageManager())!=null) {
                    startActivity(intent);
                }
            }else {
                Toast.makeText(this, getString(R.string.songerror), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
