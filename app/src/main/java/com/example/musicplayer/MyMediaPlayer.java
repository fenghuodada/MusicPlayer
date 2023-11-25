package com.example.musicplayer;

import android.media.MediaPlayer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.io.IOException;

public class MyMediaPlayer {
    Myapplication myapplication=Myapplication.getMyapplication();
    MediaPlayer musicPlayer;
    public MyMediaPlayer() {
         musicPlayer=new MediaPlayer();
    }

    //    释放播放器
    public   void onDesoryMusicPlayer(){
        musicPlayer.release();
    }
    //    歌曲切换
    public   void onMusicChange(TextView song,TextView singer,LottieAnimationView lottieAnimationView){
        if (musicPlayer!=null) {
            MusicBean musicDatePosition= Myapplication.getDataMusicList().get(Myapplication.getMusicPosition());
            if (musicPlayer.isPlaying()) {
                musicPlayer.pause();
                lottieAnimationView.pauseAnimation();
            }
            musicPlayer.stop();
            musicPlayer.reset();
            try {
                musicPlayer.setDataSource(musicDatePosition.getPath());
                musicPlayer.prepare();
                musicPlayer.seekTo(0);
                musicPlayer.start();
                lottieAnimationView.playAnimation();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            song.setText(Myapplication.getDataMusicList().get(Myapplication.getMusicPosition()).getSong());
            singer.setText(Myapplication.getDataMusicList().get(Myapplication.getMusicPosition()).getSinger());
        }else {
            Toast.makeText(myapplication.getApplicationContext(),myapplication.getString( R.string.no_have_musicplayer),Toast.LENGTH_LONG).show();
        }

    }
    //    歌曲暂停和继续
    public   void PlayeronMusicPause(ImageView view, LottieAnimationView lottieAnimationView){
        if (musicPlayer!=null) {
            if (musicPlayer.isPlaying()) {
                musicPlayer.pause();
                Myapplication.setMusicSongInPosition(musicPlayer.getCurrentPosition());
                view.setImageResource(R.drawable.pause);
                lottieAnimationView.pauseAnimation();
            }else {
                musicPlayer.seekTo(Myapplication.getMusicSongInPosition());
                musicPlayer.start();
                view.setImageResource(R.drawable.play);
                lottieAnimationView.playAnimation();
            }
        }else {
            Toast.makeText(myapplication.getApplicationContext(),myapplication.getString( R.string.no_have_musicplayer),Toast.LENGTH_LONG).show();
        }

    }
//    上一曲

    public  void PlayeronMusicNext(TextView song,TextView singer,LottieAnimationView lottieAnimationView){
        if (Myapplication.getMusicPosition()== Myapplication.getDataMusicList().size()-1) {
            Toast.makeText(myapplication.getApplicationContext(), myapplication.getString(R.string.lastMusic),Toast.LENGTH_SHORT).show();
            lottieAnimationView.pauseAnimation();
        } else if (Myapplication.getMusicPosition()>=0&&Myapplication.getMusicPosition()<Myapplication.getDataMusicList().size()-1) {
            Myapplication.setMusicPosition(Myapplication.getMusicPosition()+1);
            onMusicChange(song,singer,lottieAnimationView);
        }else {
            Toast.makeText(myapplication.getApplicationContext(), myapplication.getString(R.string.song_not_exist),Toast.LENGTH_SHORT).show();
        }
    }
//    下一曲

    public  void PlayeronMusicLast(TextView song,TextView singer,LottieAnimationView lottieAnimationView){
        if (Myapplication.getMusicPosition()==0) {
            Toast.makeText(myapplication.getApplicationContext(), myapplication.getString(R.string.firstMusic),Toast.LENGTH_SHORT).show();
            lottieAnimationView.pauseAnimation();
        } else if ((Myapplication.getMusicPosition()>0&&Myapplication.getMusicPosition()<=Myapplication.getDataMusicList().size()-1) ){
            Myapplication.setMusicPosition(Myapplication.getMusicPosition()-1);
            onMusicChange(song, singer,lottieAnimationView);
        }else {
            Toast.makeText(myapplication.getApplicationContext(), myapplication.getString(R.string.song_not_exist),Toast.LENGTH_SHORT).show();
        }
    }
    public  int onMusicTimeTotal(){
        return musicPlayer.getDuration();
    }
    public int onMusicSongPosition(){
        return musicPlayer.getCurrentPosition();
    }
    public void onMusicSeekTo(int sec){
        musicPlayer.seekTo(sec);
    }
    public void onMusicPause(){
        musicPlayer.pause();
    }
    public void onMusicStart(){
        musicPlayer.start();
    }
    public void onMusicPrepare(){
        try {
            musicPlayer.reset();
            musicPlayer.setDataSource(Myapplication.getDataMusicList().get(Myapplication.getMusicPosition()).getPath());
            musicPlayer.prepare();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onMusicDesory(){
        musicPlayer.release();
    }
    public void onMusicStop(){
        musicPlayer.stop();
    }
    public int onMusicSongDuration(){
        return musicPlayer.getDuration();
    }
    public void onMusicSetListener(TextView song,TextView singer,LottieAnimationView lottieAnimationView){
        musicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                PlayeronMusicNext(song,singer,lottieAnimationView);
            }
        });
    }
    public boolean isplaying(){
        return musicPlayer.isPlaying();
    }

}
