package com.example.musicplayer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySqlHelpOPendb extends SQLiteOpenHelper {
    public MySqlHelpOPendb(@Nullable Context context) {
        super(context, "dbMusic", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String str1="CREATE TABLE UserInformation(_id INTEGER PRIMARY KEY AUTOINCREMENT,user_name VARCHAR(20),user_pwd VARCHAR(20))";
        String str2="CREATE TABLE songInformation(_id INTEGER PRIMARY KEY AUTOINCREMENT,user_name VARCHAR(20),user_pwd VARCHAR(20))";
        String str3="CREATE TABLE characterInformation(_id INTEGER PRIMARY KEY AUTOINCREMENT,user_name VARCHAR(20),qq VARCHAR(20),weixin VARCHAR(20),yun VARCHAR(20),comments VARCHAR(200),introduce VARCHAR(200))";
        sqLiteDatabase.execSQL(str1);
        sqLiteDatabase.execSQL(str2);
        sqLiteDatabase.execSQL(str3);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
