package com.example.musicplayer;

import android.media.MediaPlayer;

public class myMediaPlayer  {

    MediaPlayer mediaPlayer;
/*    public onStaertPlay(){

    }
    public onStopPlay(){}*/
    public void onChangMusicPlay(){
        if (mediaPlayer!=null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
                mediaPlayer.stop();
            }else {
                mediaPlayer.seekTo(0);
                mediaPlayer.stop();
            }
            mediaPlayer.reset();

        }

    }

/*    public lastMusicPlay(){}
    public nextMusicPlay(){}*/
}
