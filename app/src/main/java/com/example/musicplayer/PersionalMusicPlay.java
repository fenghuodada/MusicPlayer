package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

public class PersionalMusicPlay extends AppCompatActivity {


    private TextView endTime, perisionalSong, PersionalSinger,startTime;
    private SeekBar seekBar;
    private ImageView last_music, next_music, pause_music, listMusic;
    private MusicPlayActivity musicPlayActivity;
    private int songPosition;
    private String starttime,endtime;
    private int totalSongTime;
    private ScheduledExecutorService scheduledExecutorService;
    private TimerTask timerTask;
    private List<MusicBean> playDate;
    private String song_music;
    private String singer_music;
    private Bundle bundle;
    private int currentSongPosition;
    private int currentPosition;
    List<MusicBean> listDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persional_music_play);
        musicPlayActivity = new MusicPlayActivity();
        initView();
        initSetDate();
        intSetClickListener();
    }

    private void addTimer() {
        if (scheduledExecutorService==null) {
            //org.apache.commons.lang3.concurrent.BasicThreadFactory
            scheduledExecutorService = new ScheduledThreadPoolExecutor(1,
                    new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());

            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    seekBar.setProgress(musicPlayActivity.getMediaplayer().getCurrentPosition());
                }
            },5,500, TimeUnit.MILLISECONDS);
        }
    }
    @Override
    protected void onDestroy() {
        scheduledExecutorService.shutdown();
        super.onDestroy();
    }
    private void initSetDate() {
        listDate=(List<MusicBean>) getIntent().getSerializableExtra("listdata");
         bundle=getIntent().getExtras();
        if (bundle != null) {
            song_music=bundle.getString("song");
            singer_music=bundle.getString("singer");
            totalSongTime=bundle.getInt("duration");
            currentSongPosition=bundle.getInt("currentSongPosition");
            currentPosition=bundle.getInt("currentPausePosition");

        }
        perisionalSong.setText(song_music);
        PersionalSinger.setText(singer_music);
    }
    @SuppressLint("SimpleDateFormat")
    private void intSetClickListener() {
        seekBar.setMax(totalSongTime);
        addTimer();
        endTime.setText(new SimpleDateFormat("mm:ss").format(currentSongPosition));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBar.setProgress(currentSongPosition);
                 endTime.setText(new SimpleDateFormat("mm:ss").format(i));
                if (b) {
                    seekBar.setProgress(i);
                    musicPlayActivity.getMediaplayer().seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        last_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicPlayActivity.last(currentPosition);
            }
        });
        next_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicPlayActivity.next(currentPosition);
            }
        });
        pause_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicPlayActivity.paused(currentPosition);
            }
        });
    }

    public  void iniTimer(){
        Handler handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {

            }
        };


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
    }
}