package com.example.musicplayer;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Myapplication extends Application {

    private static List<MusicBean> dataMusicList;
    private static Myapplication myapplication;


    public static MyMediaPlayer musicplayer;

    public static int getMusicSongInPosition() {
        return musicSongInPosition;
    }

    public static void setMusicSongInPosition(int musicSongInPosition) {
        Myapplication.musicSongInPosition = musicSongInPosition;
    }

    private static int musicSongInPosition;

    private static int musicPosition;
    public static List<MusicBean> getDataMusicList() {
        return dataMusicList;
    }

    public static void setDataMusicList(List<MusicBean> dataMusicList) {
        Myapplication.dataMusicList = dataMusicList;
    }

    public static int getMusicPosition() {
        return musicPosition;
    }

    public static void setMusicPosition(int musicPosition) {
        Myapplication.musicPosition = musicPosition;
    }
    @Override
    public void onCreate() {
        super.onCreate();
//      程序创建，读取myapplication
        myapplication=this;
        Myapplication.onCreatDate();
        Myapplication.setMusicPosition(0);
        Myapplication.setMusicSongInPosition(0);
        musicplayer=new MyMediaPlayer();
    }
public static Myapplication getMyapplication(){
        return myapplication;

}
//    获取数据
    public static List<MusicBean> onCreatDate(){
        dataMusicList=new ArrayList<MusicBean>();
        //        获取contentresolver对象
        ContentResolver contentResolver=myapplication.getApplicationContext().getContentResolver();
//        确定存储路径
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        开始查询
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
//        遍历对象
        int id=0;//统计个数
        if (cursor!=null&&cursor.getCount()>0) {
            cursor.moveToFirst();
            while(cursor.moveToNext()){
//                歌曲名
                @SuppressLint("Range")
                String song=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
//                作者
                @SuppressLint("Range")
                String singer=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                @SuppressLint("Range")
//                专辑
                String album=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                id++;
                String num= String.valueOf(id);
                @SuppressLint("Range")
//                    音乐路径
                String path=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                @SuppressLint("Range")
//                        音乐时长
                long duration=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                if (duration==0) {
                    continue;
                }
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat dateformat=new SimpleDateFormat("mm:ss");
                String time =dateformat.format(new Date(duration));
                MusicBean dataMusic = new MusicBean(singer, song, num, album, time, path);
                dataMusicList.add(dataMusic);
            }
        }else {
            Toast.makeText(myapplication.getApplicationContext(),myapplication.getString( R.string.not_have_music),Toast.LENGTH_SHORT).show();
        }
        return dataMusicList;

    }
/*//    创建播放器
    public static void onCreateMusicPlayer(){
        musicPlayer=new MediaPlayer();
    }
//    释放播放器
    public  static void onDesoryMusicPlayer(){
        musicPlayer.release();
    }
//    歌曲切换
    public  static void onMusicChange(){
        if (musicPlayer!=null) {
            MusicBean musicDatePosition=Myapplication.dataMusicList.get(musicPosition);
            if (musicPlayer.isPlaying()) {
                musicPlayer.pause();
                return;
            }
            musicPlayer.stop();
            musicPlayer.reset();
            try {
                musicPlayer.setDataSource(musicDatePosition.getPath());
                musicPlayer.prepare();
                musicPlayer.seekTo(0);
                musicPlayer.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            Toast.makeText(myapplication.getApplicationContext(),myapplication.getString( R.string.no_have_musicplayer),Toast.LENGTH_LONG).show();
        }

    }
//    歌曲暂停和继续
    public  static void onMusicPause(ImageView view){
        if (musicPlayer!=null) {
            if (musicPlayer.isPlaying()) {
                musicPlayer.pause();
                musicSongInPosition=musicPlayer.getCurrentPosition();
                view.setImageResource(R.drawable.pause);
            }else {
                musicPlayer.seekTo(musicSongInPosition);
                musicPlayer.start();
                view.setImageResource(R.drawable.play);
            }
        }else {
            Toast.makeText(myapplication.getApplicationContext(),myapplication.getString( R.string.no_have_musicplayer),Toast.LENGTH_LONG).show();
        }

    }
//    上一曲

    public static void onMusicNext(){
        if (musicPosition== Myapplication.dataMusicList.size()-1) {
            Toast.makeText(myapplication.getApplicationContext(), myapplication.getString(R.string.lastMusic),Toast.LENGTH_SHORT).show();
        } else if (musicPosition>=0&&musicPosition<Myapplication.dataMusicList.size()-1) {
            musicPosition++;
            Myapplication.onMusicChange();
        }else {
            Toast.makeText(myapplication.getApplicationContext(), myapplication.getString(R.string.song_not_exist),Toast.LENGTH_SHORT).show();
        }
    }
//    下一曲

    public static void onMusicLast(){
        if (musicPosition==0) {
            Toast.makeText(myapplication.getApplicationContext(), myapplication.getString(R.string.firstMusic),Toast.LENGTH_SHORT).show();
        } else if (musicPosition>0&&musicPosition<=Myapplication.dataMusicList.size()-1) {
            musicPosition--;
            Myapplication.onMusicChange();
        }else {
            Toast.makeText(myapplication.getApplicationContext(), myapplication.getString(R.string.song_not_exist),Toast.LENGTH_SHORT).show();
        }
    }
//    获取歌曲的时长
*//*    public static int onMusicTimeTotal(int musicPosition){
        MusicBean musicdata=Myapplication.dataMusicList.get(musicPosition);
        MediaPlayer mediaPlayered=new MediaPlayer();
        try {
            mediaPlayered.setDataSource(musicdata.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int resultMusic=mediaPlayered.getDuration();
        mediaPlayered.release();
        return resultMusic;
    }*//*
public  int onMusicTimeTotal(){
    return musicPlayer.getDuration();
}*/
}




