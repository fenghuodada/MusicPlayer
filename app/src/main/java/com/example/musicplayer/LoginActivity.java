package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity{

    private TextView user;
    private TextView pwd;
    private CheckBox agrement;
    private String str1;
    private String str2;
    private Button wellogin,welregister;
    MySqlHelpOPendb helpOPendb;
    private User users;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        helpOPendb=new MySqlHelpOPendb(this);
        initView();
        initSetclick();
    }
    private void initSetclick() {
        agrement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //设置多选框的行为
                if (b) {
                    agrement.setText(str1);
                }
            }
        });
        wellogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor;
                if (user.getText().toString().trim().length()!=0&&pwd.getText().toString().trim().length()!=0) {
                    SQLiteDatabase db = helpOPendb.getReadableDatabase();
                     cursor=db.query("userInformation",new String[]{"user_name","user_pwd"},"user_name=?",new String[]{user.getText().toString()},null,null,null);
                    if (cursor.getCount()==0) {
                        Toast.makeText(LoginActivity.this, getString(R.string.user_not_exist),Toast.LENGTH_SHORT).show();
                    }else {
                        if (agrement.isChecked()) {
                            cursor.moveToFirst();
                                @SuppressLint("Range")
                                String user_names=cursor.getString(cursor.getColumnIndex("user_name"));
                                @SuppressLint("Range")
                                String user_pwds=cursor.getString(cursor.getColumnIndex("user_pwd"));
                                users=new User(user_names,user_pwds);
                            cursor.close();
                            db.close();
                            if (user.getText().toString().equals(users.getUserNam()) && pwd.getText().toString().equals(users.getUserPwd())) {

                                Intent intent=new Intent(LoginActivity.this, MusicPlayActivity.class);
                                if (intent.resolveActivity(getPackageManager())!= null) {
                                    startActivity(intent);
                                    Toast.makeText(LoginActivity.this, getString(R.string.loginsucceed),Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(LoginActivity.this, getString(R.string.activityfailed),Toast.LENGTH_LONG).show();
                                }
                            }else {
                                Toast.makeText(LoginActivity.this, getString(R.string.pwdsuccessful),Toast.LENGTH_SHORT).show();

                            }
                        }else {
                            Toast.makeText(LoginActivity.this,str2,Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(LoginActivity.this, getString(R.string.usernamesuccessful),Toast.LENGTH_SHORT).show();
                }
            }
        });
        welregister.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                if (intent.resolveActivity(getPackageManager())!=null) {
                    startActivity(intent);
                }
            }
        });
    }

    private void initView() {
        user = findViewById(R.id.login_user);
        pwd = findViewById(R.id.login_pwd);
        wellogin=findViewById(R.id.login_loginButton);
        welregister=findViewById(R.id.login_registerButton);
        agrement = findViewById(R.id.loginCheck);
        str1 = getString(R.string.str1);
        str2 = getString(R.string.str2);
    }
}