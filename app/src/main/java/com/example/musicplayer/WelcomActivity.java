package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomActivity extends AppCompatActivity implements View.OnClickListener {
    TextView login,register,forgetPwd;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        initView();
        initClick();

    }
    private void initClick() {
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        forgetPwd.setOnClickListener(this);
    }
    private void initView(){
        login=findViewById(R.id.wel_login);
        register=findViewById(R.id.wel_register);
        forgetPwd=findViewById(R.id.wel_forgetPwd);
    }
    @SuppressLint("QueryPermissionsNeeded")
    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.wel_login) {
            Intent intent=new Intent(this,LoginActivity.class);
            if (intent.resolveActivity(getPackageManager())!=null) {
                startActivity(intent);
            }
        }
        if (view.getId()==R.id.wel_register) {
            Intent intent=new Intent(this,RegisterActivity.class);
            if (intent.resolveActivity(getPackageManager())!=null) {
                startActivity(intent);
            }
        }
        if (view.getId()==R.id.wel_forgetPwd) {
            Intent intent=new Intent(this,ForgetPasswordActivity.class);
            if (intent.resolveActivity(getPackageManager())!=null) {
                startActivity(intent);
            }
        }
    }
}