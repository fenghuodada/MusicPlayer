package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Objects;

public class EnterActivity extends AppCompatActivity {
    LottieAnimationView black,white;
    TextView english,chinese;
//    申请权限
    public Boolean checkPermission() {
        boolean isGranted = true;
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
            }
            if (this.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) !=PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
            }
            if (!isGranted) {
                this.requestPermissions(
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission
                                .ACCESS_FINE_LOCATION,
                                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        102);
            }
        }
        return isGranted;
    }

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        checkPermission();
        english=findViewById(R.id.welcome_black);
        chinese=findViewById(R.id.welcome_white);
        black=findViewById(R.id.black_cat);
        white=findViewById(R.id.white_cat);
        english.animate().translationY(-2000).setDuration(2700).setStartDelay(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animator) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animator) {
                english.animate().alpha(0).setDuration(2000).setStartDelay(2000);
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animator) {

            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animator) {

            }
        });
        black.animate().translationY(-250).setDuration(3000).setStartDelay(20).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animator) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animator) {
                black.animate().translationX(2000).setDuration(1500).setStartDelay(2000);
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animator) {

            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animator) {

            }
        });
        chinese.animate().translationX(50).setDuration(3000).setStartDelay(6000).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animator) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animator) {
                chinese.animate().translationX(2000).setDuration(2700).setStartDelay(1000);
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animator) {

            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animator) {

            }
        });
        white.animate().translationY(-250).setDuration(1000).setStartDelay(6000).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animator) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animator) {
                white.animate().alpha(0).setDuration(800).setStartDelay(100);

            }

            @Override
            public void onAnimationCancel(@NonNull Animator animator) {

            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animator) {

            }
        });
        new Handler(Objects.requireNonNull(Looper.myLooper())).postDelayed(new Runnable() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void run() {
                Intent intent=new Intent(EnterActivity.this,WelcomActivity.class);
                if (intent.resolveActivity(getPackageManager())!=null) {
                    startActivity(intent);
                }
                finish();
            }
        },12000);
    }
}