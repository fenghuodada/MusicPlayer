package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

public class PersionalMusicPlay extends AppCompatActivity implements View.OnClickListener {
    private TextView endTime, perisionalSong, PersionalSinger,startTime;
    private SeekBar seekBar;
    private ImageView last_music, next_music, pause_music, listMusic;
//    private ScheduledExecutorService scheduledExecutorService;
    private String song_music;
    private String singer_music;
    private Myapplication perisionalMyapplition;
    private MyMediaPlayer musicplayer;
    private Runnable runnable;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persional_music_play);
//         musicplayer=new MyMediaPlayer();
        musicplayer=Myapplication.musicplayer;
        initView();
        addTimer();
        initSetDate();
        intSetClickListener();
    }
//    添加定时器
    private void addTimer() {
//        if (scheduledExecutorService==null) {
//     //org.apache.commons.lang3.concurrent.BasicThreadFactory
//     scheduledExecutorService = new ScheduledThreadPoolExecutor(1,
//             new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
//     scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
//         @SuppressLint("SimpleDateFormat")
//         @Override
//         public void run() {
//             int timeshow=musicplayer.onMusicSongPosition();
//             seekBar.setProgress(timeshow);
//             startTime.setText(new SimpleDateFormat("mm:ss").format(timeshow));
//         }
//     },0,1000, TimeUnit.MILLISECONDS);
// }

        handler=new Handler();
         runnable = new Runnable() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void run() {
                int p=musicplayer.onMusicSongPosition();
                int d=musicplayer.onMusicSongDuration();
                seekBar.setProgress(p);
                handler.postDelayed(this,1000);
                startTime.setText(new SimpleDateFormat("mm:ss").format(p));
            }
        };
    }
    public void startTimer(){
        handler.postDelayed(runnable,1000);
    }
    public void endTimer(){
        handler.removeCallbacks(runnable);
    }
    @Override
    protected void onDestroy() {
//        scheduledExecutorService.shutdown();
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
/*        musicplayer.onMusicPrepare();
        musicplayer.onMusicSeekTo(Myapplication.getMusicSongInPosition());
        musicplayer.onMusicStart();*/
        musicplayer.onMusicSetListener(perisionalSong,PersionalSinger);
        seekBar.setMax(musicplayer.onMusicSongDuration());
        //seekBar.setProgress(musicplayer.onMusicSongPosition());
//        startTimer();
    }
    private void intSetClickListener() {
        pause_music.setOnClickListener(this);
        next_music.setOnClickListener(this);
        last_music.setOnClickListener(this);
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
    }
    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.musicPlayCenterBottomPause) {
            musicplayer.PlayeronMusicPause(pause_music);
            return;
        }
        if (view.getId()==R.id.musicPlayCenterBottomNext) {
            musicplayer.PlayeronMusicNext(perisionalSong,PersionalSinger);
            endTime.setText(Myapplication.getDataMusicList().get(Myapplication.getMusicPosition()).getTime());
            return;
        }
        if (view.getId()==R.id.musicPlayCenterBottomLast) {
            musicplayer.PlayeronMusicLast(perisionalSong,PersionalSinger);
            endTime.setText(Myapplication.getDataMusicList().get(Myapplication.getMusicPosition()).getTime());
        }
    }
    @Override
    protected void onStop() {
        Myapplication.setMusicSongInPosition(musicplayer.onMusicSongPosition());
//        musicplayer.onMusicDesory();
//        endTimer();
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        musicplayer=new MyMediaPlayer();
        musicplayer=Myapplication.musicplayer;
//        startTimer();

    }
}