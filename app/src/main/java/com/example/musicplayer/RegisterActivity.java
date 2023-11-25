package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private EditText user,pwd,ensure;
    private TextView  Registerlogin,Registerregister;
    private String str;
    private MySqlHelpOPendb dbHelper;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initview();
        initclick();
        dbHelper =new MySqlHelpOPendb(this);

    }
    private void initclick() {
        Registerlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                Intent intent=new Intent()
                        .setAction("music-player")
                        .addCategory("com.example.music-player.login.cattgory");*/
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                if (intent.resolveActivity(getPackageManager())!= null) {
                    startActivity(intent);
                }

            }
        });
        Registerregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                注册成功
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                @SuppressLint("Recycle") Cursor cursor = db.query("userInformation", new String[]{"user_name"}, "user_name=?", new String[]{user.getText().toString()}, null, null, null);
                if (checkBox.isChecked()) {
                    if (user.getText().toString().trim().length()==0||pwd.getText().toString().trim().length()==0) {
                    Toast.makeText(RegisterActivity.this, getString(R.string.user_name_and_pwd_name_is_not_filled),Toast.LENGTH_SHORT).show();
                    }else {
                        if (cursor.getCount() != 0) {
                            Toast.makeText(RegisterActivity.this, getString(R.string.user_qury), Toast.LENGTH_SHORT).show();
                        } else {
                            if (pwd.getText().toString().equals(ensure.getText().toString())) {
                                ContentValues values = new ContentValues();
                                values.put("user_pwd", pwd.getText().toString());
                                values.put("user_name", user.getText().toString());
                                long num = db.insert("UserInformation", null, values);
                                if (num == -1) {
                                    Toast.makeText(RegisterActivity.this, getString(R.string.fialedregister), Toast.LENGTH_SHORT).show();
                                    user.setText("");
                                    pwd.setText("");
                                    ensure.setText("");
                                } else {
                                    Toast.makeText(RegisterActivity.this, getString(R.string.successed), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, getString(R.string.pwd_and_ensurePwd), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }else {
                    Toast.makeText(RegisterActivity.this,getString(R.string.garement_register),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void initview() {
        user=findViewById(R.id.user_register);
        pwd=findViewById(R.id.pwd_register);
        ensure=findViewById(R.id.pwd_register_rensure);
        Registerlogin=findViewById(R.id.register_textview_login);
        Registerregister=findViewById(R.id.register_textview_register);
        checkBox=findViewById(R.id.register_agrement);
    }
}