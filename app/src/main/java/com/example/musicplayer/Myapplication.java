package com.example.musicplayer;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
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

    public static boolean getISend() {
        return ISend;
    }

    public static void setISend(boolean ISend) {
        Myapplication.ISend = ISend;
    }

    private static boolean ISend;

    public static int getMusicSongInPosition() {
        return musicSongInPosition;
    }

    public static void setMusicSongInPosition(int musicSongInPosition) {
        Myapplication.musicSongInPosition = musicSongInPosition;
    }

    private static int musicSongInPosition;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Myapplication.username = username;
    }

    private static String username;

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
        Myapplication.setISend(false);
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
        int id=0;
        //统计个数
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
                if (duration==0||singer=="<unknown>") {
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
}




