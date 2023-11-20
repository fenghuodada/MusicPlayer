package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class ForgetPasswordActivity extends AppCompatActivity {
    TextView reset_user,reset_pwd,reset_ensurePwd,reset_login,reset_resetPwd;
    CheckBox reset_agrement;
    MySqlHelpOPendb helpOPenDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        helpOPenDb=new MySqlHelpOPendb(this);
        initView();
        initSetClick();
    }

    private void initSetClick() {
        reset_resetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reset_pwd.getText().toString().equals(reset_ensurePwd.getText().toString())) {
                    SQLiteDatabase db = helpOPenDb.getWritableDatabase();
                    ContentValues values=new ContentValues();
                    values.put("user_pwd",reset_pwd.getText().toString());
                    db.update("userInformation",values,"user_name=?",new String[]{
                            reset_user.getText().toString()
                    });
                    db.close();
                    Toast.makeText(ForgetPasswordActivity.this, getString(R.string.resetsuccessful),Toast.LENGTH_SHORT).show();
                    reset_user.setText("");
                    reset_pwd.setText("");
                    reset_ensurePwd.setText("");
                }else {
                    Toast.makeText(ForgetPasswordActivity.this,getString(R.string.pwd_and_ensurePwd),Toast.LENGTH_SHORT).show();
                }
            }
        });
        reset_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ForgetPasswordActivity.this,LoginActivity.class);
                if (intent.resolveActivity(getPackageManager())!=null) {
                    startActivity(intent);
                }
            }
        });
    }

    @SuppressLint("CutPasteId")
    private void initView() {
        reset_user=findViewById(R.id.user_reset);
        reset_pwd=findViewById(R.id.pwd_reset);
        reset_ensurePwd=findViewById(R.id.pwd_ensure_reset);
        reset_login=findViewById(R.id.reset_textview_login);
        reset_resetPwd=findViewById(R.id.reset_textview);
        reset_agrement=findViewById(R.id.reset_agrement);
    }
}