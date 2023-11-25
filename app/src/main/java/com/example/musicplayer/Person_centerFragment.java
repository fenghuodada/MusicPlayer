package com.example.musicplayer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Objects;

public class Person_centerFragment extends Fragment {
    TextView qq,weixin,yun,introduce,comments,userName,editor;
    private View view;
    MySqlHelpOPendb dbhelper;
    IntroduceActivity introduceActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.fragment_person_center, container, false);
        dbhelper=new MySqlHelpOPendb(getActivity());
        introduceActivity=new IntroduceActivity();
        initView();
        if (Myapplication.getISend()) {
            initget();
        }
/*        introduceActivity.setonBackClickListener(new IntroduceActivity.OnBackClickListener() {
            @Override
            public void backCenterFragmentButton() {
                initget();
            }
        });*/
        editor.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),IntroduceActivity.class);
                if (intent.resolveActivity(requireActivity().getPackageManager())!=null) {
                    startActivity(intent);
                }
            }
        });
        return view;
    }
//  得到数据
    @SuppressLint("Range")
    private void initget() {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor=db.query("characterInformation",new String[]{"qq","weixin","yun","comments","introduce"},"user_name=?",new String[]{Myapplication.getUsername()},null,null,null,null);
        if (cursor!=null) {
            cursor.moveToFirst();
            qq.setText(cursor.getString(cursor.getColumnIndex("qq")));
            weixin.setText(cursor.getString(cursor.getColumnIndex("weixin")));
            yun.setText(cursor.getString(cursor.getColumnIndex("yun")));
            comments.setText(cursor.getString(cursor.getColumnIndex("comments")));
            introduce.setText(cursor.getString(cursor.getColumnIndex("introduce")));
        }
    }
    private void initView() {
        qq=view.findViewById(R.id.qq);
        weixin=view.findViewById(R.id.weixin);
        yun=view.findViewById(R.id.yun);
        comments=view.findViewById(R.id.message);
        introduce=view.findViewById(R.id.person_introduce);
        userName=view.findViewById(R.id.user_names);
        editor=view.findViewById(R.id.edit);
    }

}