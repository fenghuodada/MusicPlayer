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
    public ArrayList<MusicBean> musicDate;
    private MusicBean musicdata;
    private MusicAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    public int getCurrentPausePosition() {
        return currentPausePosition;
    }

    public void setCurrentPausePosition(int currentPausePosition) {
        this.currentPausePosition = currentPausePosition;
    }

    private int currentPausePosition=-1;

    public MediaPlayer getMediaplayer() {
        return mediaplayer;
    }

    public void setMediaplayer(MediaPlayer mediaplayer) {
        this.mediaplayer = mediaplayer;
    }

    private MediaPlayer mediaplayer;
    private Connect connects;

    public int getCurrentPausePositionInSong() {
        return currentPausePositionInSong;
    }

    public void setCurrentPausePositionInSong(int currentPausePositionInSong) {
        this.currentPausePositionInSong = currentPausePositionInSong;
    }
    private int currentPausePositionInSong=0;
    public interface Connect{
        public void connected(List<MusicBean> musicBeans,int positioned);
    }
    public void connection(Connect connect){
        this.connects=connect;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);
//        实例化数据源
        musicDate=new ArrayList<MusicBean>();
//        实例化媒体播放器
        mediaplayer=new MediaPlayer();
        initView();
        initClick();
        initsetAdapter();
        retDate();
        welTomusicApter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopMusicPlay();
    }

    private void welTomusicApter() {
        adapter.setOnItemClickListener(new MusicAdapter.onItemClickListener() {
            @Override
            public void onItemListener(View view, int position) {
                currentPausePosition=position;
//                为底部栏设置数据项
                musicSetBottom(position);
            }
        });
    }
    @SuppressLint("NotifyDataSetChanged")
    private void initsetAdapter() {
        adapter=new MusicAdapter(MusicPlayActivity.this,musicDate);
//        确定布局管理器
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        musicReclcle.setLayoutManager(linearLayoutManager);
        musicReclcle.setAdapter(adapter);
    }
    private void musicSetBottom( int position) {
//        填充底部栏数据
        MusicBean bottom_bean=musicDate.get(position);
        song.setText(bottom_bean.getSong());
        singer.setText(bottom_bean.getSinger());

        if (mediaplayer.isPlaying()) {
            stopMusicPlay();
            pause.setImageResource(R.drawable.pause);
            Log.d(TAG, "musicSetBottom: 停止播放");
            return;
        }
//        重置媒体播放器
        mediaplayer.reset();
        Log.d(TAG, "musicSetBottom: 重置播放器");
//        更换播放路径
        try {
            mediaplayer.setDataSource(bottom_bean.getPath());
//            播放新的音乐
            if (currentPausePositionInSong!=0) {
                restartmusicplay();
            }else {
                startMusicPlay();
            }
            Log.d(TAG, "musicSetBottom: 播放新的音乐");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void startMusicPlay() {
//        媒体播放器不为空，并且没有在播放,没有点击暂停按钮
        if ( mediaplayer!=null&&!mediaplayer.isPlaying()) {
//            进行音乐判断
            Log.d(TAG, "startMusicPlay: 音乐判断");
            if (currentPausePositionInSong==0) {
//                进行音乐准备
                try {
                    Log.d(TAG, "startMusicPlay: 开始音乐");
                    mediaplayer.prepare();
                    mediaplayer.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else {
                mediaplayer.seekTo(currentPausePositionInSong);
                mediaplayer.start();
            }
            pause.setImageResource(R.drawable.play);
        }

    }
    public void restartmusicplay(){
        currentPausePositionInSong=0;
        try {
            mediaplayer.prepare();
            mediaplayer.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pause.setImageResource(R.drawable.play);
    }
    private void stopMusicPlay() {
//        对进度条的控制
        if (mediaplayer!= null) {
//            暂停进度条
            mediaplayer.pause();
//            使进度条回到最初
            mediaplayer.seekTo(0);
//            停止播放条
            mediaplayer.stop();
//            重置暂停播放图片
        }
    }
    private void pauseMusicPlay() {
        if (mediaplayer!=null&&mediaplayer.isPlaying()) {
//            记录进度条的位置
            currentPausePositionInSong=mediaplayer.getCurrentPosition();
            mediaplayer.pause();
            pause.setImageResource(R.drawable.pause);
        }
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
            next(currentPausePosition);
        }
        if (view.getId() ==R.id.music_left) {
            last(currentPausePosition);
        }
        if (view.getId() ==R.id.music_Pause) {
//            currentPausePosition：表示播放的音乐的位置
            paused(currentPausePosition);
        }
        if (view.getId() ==R.id.bottom_music) {
            Log.d(TAG, "onClick: bottom");
            if (currentPausePosition!=-1&&mediaplayer!=null) {
                //connects.connected(musicDate,currentPausePosition);
/*                Intent intent=new Intent()
                        .setAction("com.example.person-music")
                        .addCategory("com.example.persion-music.category");*/
                Intent intent=new Intent(MusicPlayActivity.this,PersionalMusicPlay.class);
                intent.putExtra("listdata",(Parcelable) musicDate);
                Bundle bundle=new Bundle();
                bundle.putString("song",musicdata.getSong());
                bundle.putString("singer",musicdata.getSinger());
                bundle.putInt("duration",mediaplayer.getDuration());
                bundle.putInt("currentSongPosition",mediaplayer.getCurrentPosition());
                bundle.putInt("currentPosition",currentPausePosition);
                intent.putExtras(bundle);
                if (intent.resolveActivity(getPackageManager())!=null) {
                    startActivity(intent);
                    pauseMusicPlay();
                }
            }else {
                Toast.makeText(this,"请选择歌曲", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void paused(int currentPausePosition) {
        if (mediaplayer!=null) {
            if (currentPausePosition==-1) {
                Toast.makeText(MusicPlayActivity.this,"请选择要播放的音乐",Toast.LENGTH_SHORT).show();
                return;
            }
            if (mediaplayer.isPlaying()) {
//                已经在播放中
                pauseMusicPlay();
            }else {
//                开始播放
                startMusicPlay();
            }
        }
    }

    public void next(int currentPausePosition) {
        if (currentPausePosition==musicDate.size()-1) {
            Toast.makeText(MusicPlayActivity.this,"目前是最后一曲",Toast.LENGTH_SHORT).show();
        } else if (currentPausePosition==-1) {
            Toast.makeText(MusicPlayActivity.this,"还没有选择要播放的音乐",Toast.LENGTH_SHORT).show();
        }else {
            currentPausePosition++;
            musicSetBottom(currentPausePosition);
        }
    }

    public void last(int currentPausePosition) {
        if (currentPausePosition==0) {
            Toast.makeText(MusicPlayActivity.this,"目前是首曲",Toast.LENGTH_SHORT).show();
        } else if (currentPausePosition==-1) {
            Toast.makeText(MusicPlayActivity.this,"还没有选择要播放的音乐",Toast.LENGTH_SHORT).show();
        }else {
            currentPausePosition--;
            musicSetBottom(currentPausePosition);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void retDate(){
//        获取contentresolver对象
        ContentResolver contentResolver=getContentResolver();
//        确定存储路径
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        开始查询
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
//        遍历对象
        int id=0;//统计个数
        if (cursor.getCount()>0) {
            while(cursor.moveToNext()){
                @SuppressLint("Range")
                String song=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                @SuppressLint("Range")
                String singer=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                @SuppressLint("Range")
                String album=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                id++;
                String num= String.valueOf(id);
                @SuppressLint("Range")
                String path=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                @SuppressLint("Range")
                long duration=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat dateformat=new SimpleDateFormat("mm:ss");
                String time =dateformat.format(new Date(duration));
                 musicdata = new MusicBean(singer, song, num, album, time, path);
                musicDate.add(musicdata);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
