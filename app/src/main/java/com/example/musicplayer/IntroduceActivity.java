package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class IntroduceActivity extends AppCompatActivity {
    private EditText qq,weXin, yun, introduce,comments;
    private CardView back;
    private View view;
    private MySqlHelpOPendb helpdb;
/*    private OnBackClickListener listener;

    public interface OnBackClickListener{
        public void backCenterFragmentButton();
    }
    public void setonBackClickListener(OnBackClickListener onBackClickListener){
        this.listener=onBackClickListener;
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        helpdb=new MySqlHelpOPendb(this);
        initView();
        initSetClickListener();
    }
    private void initSetClickListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = helpdb.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("user_name",Myapplication.getUsername());
                values.put("qq",qq.getText().toString());
                values.put("weixin",weXin.getText().toString());
                values.put("yun",yun.getText().toString());
                values.put("introduce",introduce.getText().toString());
                values.put("comments",comments.getText().toString());
                db.insert("characterInformation",null,values);
                Toast.makeText(IntroduceActivity.this,getString(R.string.putsuccess),Toast.LENGTH_SHORT).show();
                Myapplication.setISend(true);
                Intent intent=new Intent(IntroduceActivity.this, MusicPlayActivity.class);
                if (intent.resolveActivity(getPackageManager())!=null) {
                    startActivity(intent);
                }
//                listener.backCenterFragmentButton();
            }
        });
    }
    private void initView() {
        qq=findViewById(R.id.qq_input);
        weXin=findViewById(R.id.weixin_input);
        introduce=findViewById(R.id.person_introduce_input);
        comments=findViewById(R.id.message_input);
        back=findViewById(R.id.entermusic);
        yun=findViewById(R.id.yun_input);
    }
}