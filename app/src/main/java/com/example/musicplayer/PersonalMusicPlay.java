package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class PersonalMusicPlay extends AppCompatActivity implements View.OnClickListener {
    private TextView endTime, perisionalSong, PersionalSinger,startTime;
    private SeekBar seekBar;
    private ImageView last_music, next_music, pause_music, listMusic;
    private MyMediaPlayer musicplayer;
    private Runnable runnable;
    private Handler handler;
    private LottieAnimationView paly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persional_music_play);
        musicplayer=Myapplication.musicplayer;
        initView();
        addTimer();
        initSetDate();
        intSetClickListener();
    }
//    添加定时器
    private void addTimer() {


        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int p=musicplayer.onMusicSongPosition();
                seekBar.setProgress(p);
                handler.postDelayed(this,1000);
                startTime.setText(new SimpleDateFormat("mm:ss").format(p));
            }
        },500);
/*         new Handler(Objects.requireNonNull(Looper.myLooper())).postDelayed(new Runnable() {
             @Override
             public void run() {
                 int p=musicplayer.onMusicSongPosition();
                 seekBar.setProgress(p);
                 handler.postDelayed(this,1000);
                 startTime.setText(new SimpleDateFormat("mm:ss").format(p));
             }
         },500);*/
    }
    public void startTimer(){
        handler.postDelayed(runnable,1000);
    }
    public void endTimer(){
        handler.removeCallbacks(runnable);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @SuppressLint("SimpleDateFormat")
    private void initSetDate() {
//        填充数据
        startTime.setText(new SimpleDateFormat("mm:ss").format(Myapplication.getMusicSongInPosition()));
        endTime.setText(Myapplication.getDataMusicList().get(Myapplication.getMusicPosition()).getTime());
        perisionalSong.setText(Myapplication.getDataMusicList().get(Myapplication.getMusicPosition()).getSong());
        PersionalSinger.setText(Myapplication.getDataMusicList().get(Myapplication.getMusicPosition()).getSinger());
        initMusicPlayer();
    }
    private void initMusicPlayer() {
        musicplayer.onMusicSetListener(perisionalSong,PersionalSinger,paly);
        seekBar.setMax(musicplayer.onMusicSongDuration());
        if (musicplayer.isplaying()) {
            pause_music.setImageResource(R.drawable.play);
        }
        startTimer();
            paly.setAnimation(R.raw.music);
            paly.playAnimation();
    }
    private void intSetClickListener() {
        pause_music.setOnClickListener(this);
        next_music.setOnClickListener(this);
        last_music.setOnClickListener(this);
        listMusic.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBar.setProgress(i);
                if (b) {
                    seekBar.setProgress(i);
                    musicplayer.onMusicPause();
                    musicplayer.onMusicSeekTo(i);
                    musicplayer.onMusicStart();
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setProgress(musicplayer.onMusicSongDuration());
            }
        });
    }
    private void initView() {
        perisionalSong = findViewById(R.id.musicPlayCenterSong);
        PersionalSinger = findViewById(R.id.musicPlayCenterSinger);
        last_music = findViewById(R.id.musicPlayCenterBottomLast);
        pause_music = findViewById(R.id.musicPlayCenterBottomPause);
        next_music = findViewById(R.id.musicPlayCenterBottomNext);
        listMusic = findViewById(R.id.musicPlayCenterList);
        seekBar = findViewById(R.id.musicPlayCenterSeekbar);
        startTime=findViewById(R.id.musicPlayCenterstartTime);
        endTime=findViewById(R.id.musicPlayCenterendTime);
        paly=findViewById(R.id.musicPlayCenterImage);
    }
    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.musicPlayCenterBottomPause) {
            musicplayer.PlayeronMusicPause(pause_music, paly);
            return;
        }
        if (view.getId()==R.id.musicPlayCenterBottomNext) {
            musicplayer.PlayeronMusicNext(perisionalSong,PersionalSinger,paly);
            seekBar.setProgress(0);
            endTime.setText(Myapplication.getDataMusicList().get(Myapplication.getMusicPosition()).getTime());
            return;
        }
        if (view.getId()==R.id.musicPlayCenterBottomLast) {
            musicplayer.PlayeronMusicLast(perisionalSong,PersionalSinger,paly);
            seekBar.setProgress(0);
            endTime.setText(Myapplication.getDataMusicList().get(Myapplication.getMusicPosition()).getTime());
            return;
        }
        if (view.getId()==R.id.musicPlayCenterList) {
            ListDialogFragment listDialogFragment=new ListDialogFragment();
            listDialogFragment.show(getSupportFragmentManager(), "list");
        }
    }
    @Override
    protected void onStop() {
        Myapplication.setMusicSongInPosition(musicplayer.onMusicSongPosition());
        endTimer();
        paly.pauseAnimation();
        super.onStop();
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onRestart() {
        super.onRestart();
        musicplayer=Myapplication.musicplayer;
        startTimer();
        if (paly!=null) {
            paly.playAnimation();
        }
    }
}