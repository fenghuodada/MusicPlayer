package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

public class MusicPlayActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView next,last,pause,homePage,persionPage;
    private TextView singer,song;
    private MyMediaPlayer musiclistPlayer;
    LottieAnimationView lottieAnimationView;
    ListingMusicFragment listingMusicFragment;
    Person_centerFragment personCenterFragment;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);
         listingMusicFragment=new ListingMusicFragment();
//        musiclistPlayer=Myapplication.musicplayer;
        listingMusicFragment=new ListingMusicFragment();
        addFragment(listingMusicFragment);
        initView();
        song.setText(Myapplication.getDataMusicList().get(Myapplication.getMusicPosition()).getSong());
        singer.setText(Myapplication.getDataMusicList().get(Myapplication.getMusicPosition()).getSinger());
        initClick();
        welTomusicApter();
    }
    private void welTomusicApter() {
        listingMusicFragment.setClickItemListener(new ListingMusicFragment.ClickItemListener() {
            @Override
            public void sendPosition(int posion) {
                Myapplication.setMusicPosition(posion);
                lottieAnimationView.playAnimation();
//                musiclistPlayer.onMusicChange(song,singer,lottieAnimationView);
                Myapplication.musicplayer.onMusicChange(song,singer,lottieAnimationView);
                pause.setImageResource(R.drawable.play);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onStop() {
        super.onStop();
        lottieAnimationView.pauseAnimation();
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onRestart() {
        super.onRestart();
        singer.setText(Myapplication.getDataMusicList().get(Myapplication.getMusicPosition()).getSinger());
        song.setText(Myapplication.getDataMusicList().get(Myapplication.getMusicPosition()).getSong());
//        musiclistPlayer.onMusicSetListener(song,singer,lottieAnimationView);
        Myapplication.musicplayer.onMusicSetListener(song,singer,lottieAnimationView);
        if (Myapplication.musicplayer.isplaying()) {
            pause.setImageResource(R.drawable.play);
        }
        if (lottieAnimationView!=null) {
            lottieAnimationView.playAnimation();
            return;
        }
        if (listingMusicFragment!=null) {
            listingMusicFragment=new ListingMusicFragment();
        }
        if (listingMusicFragment==null) {
            listingMusicFragment=new ListingMusicFragment();
        }
        rePlaceFragment(listingMusicFragment);

    }

    private void initView(){
        next=findViewById(R.id.music_right);
        last=findViewById(R.id.music_left);
        pause=findViewById(R.id.music_Pause);
        song=findViewById(R.id.music_song);
        singer=findViewById(R.id.music_singer);
        homePage=findViewById(R.id.Navigation_bar_homePage);
        persionPage=findViewById(R.id.Navigation_bar_persionalCenter);
        lottieAnimationView=findViewById(R.id.music_icon);
        lottieAnimationView.setAnimation(R.raw.list_music);
    }
    private void initClick(){
        next.setOnClickListener(this);
        last.setOnClickListener(this);
        pause.setOnClickListener(this);
        homePage.setOnClickListener(this);
        persionPage.setOnClickListener(this);
        findViewById(R.id.bottom_music).setOnClickListener(this);

    }

    @SuppressLint("QueryPermissionsNeeded")
    @Override
    public void onClick(View view) {
        if (view.getId() ==R.id.music_right) {
//            musiclistPlayer.PlayeronMusicNext(song,singer,lottieAnimationView);
            Myapplication.musicplayer.PlayeronMusicNext(song,singer,lottieAnimationView);
            return;
        }
        if (view.getId() ==R.id.music_left) {
//            musiclistPlayer.PlayeronMusicLast(song,singer,lottieAnimationView);
            Myapplication.musicplayer.PlayeronMusicLast(song,singer,lottieAnimationView);
            return;
        }
        if (view.getId() ==R.id.music_Pause) {
//            currentPausePosition：表示播放的音乐的位置
//            musiclistPlayer.PlayeronMusicPause(pause,lottieAnimationView);
            Myapplication.musicplayer.PlayeronMusicPause(pause,lottieAnimationView);
            return;
        }
        if (view.getId() ==R.id.bottom_music) {
            if (Myapplication.musicplayer!=null&&Myapplication.getMusicPosition()>=0&&Myapplication.getMusicPosition()<=Myapplication.getDataMusicList().size()-1) {
                Intent intent=new Intent(MusicPlayActivity.this, PersonalMusicPlay.class);
                if (intent.resolveActivity(getPackageManager())!=null) {
                    startActivity(intent);
                }
            }else {
                Toast.makeText(this, getString(R.string.songerror), Toast.LENGTH_SHORT).show();
            }
            return;
        }
        if (view.getId()==R.id.Navigation_bar_homePage) {
            listingMusicFragment=new ListingMusicFragment();
            rePlaceFragment(listingMusicFragment);
            return;
        }
        if (view.getId()==R.id.Navigation_bar_persionalCenter) {
            personCenterFragment=new Person_centerFragment();
            rePlaceFragment(personCenterFragment);
        }
    }

/**
 * 创建布局替换方法
 */
    public void rePlaceFragment(Fragment fragment) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.musicFrahgmentList,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void addFragment(Fragment fragment) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.musicFrahgmentList,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
