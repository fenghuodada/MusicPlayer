package com.example.musicplayer;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class appUsePermission {
    private boolean RequestPermissions(@NonNull Context context, @NonNull String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            Log.i("requestMyPermissions",": 【 " + permission + " 】没有授权，申请权限");
            ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, 100);
            return false;
        } else {
            Log.i("requestMyPermissions",": 【 " + permission + " 】有权限");
            return true;
        }
    }

}
