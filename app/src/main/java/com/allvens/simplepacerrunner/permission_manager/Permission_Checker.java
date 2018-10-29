package com.allvens.simplepacerrunner.permission_manager;

import android.Manifest.permission;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import static android.Manifest.permission.MODIFY_AUDIO_SETTINGS;

public class Permission_Checker {

    private Context context;
    public Permission_Checker(Context context){
        this.context = context;
    }

    public void checkPermissions(){

        if (ContextCompat.checkSelfPermission(context, MODIFY_AUDIO_SETTINGS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions((Activity) context, new String[]{MODIFY_AUDIO_SETTINGS}, 1);
        }

        if (ContextCompat.checkSelfPermission(context, permission.VIBRATE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions((Activity) context, new String[]{permission.VIBRATE}, 2);
        }
    }

    public void checkPermissionsOther(){

        if (ContextCompat.checkSelfPermission(context, MODIFY_AUDIO_SETTINGS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ((Activity)context).requestPermissions(new String[]{MODIFY_AUDIO_SETTINGS}, 1);
        }

        if (ContextCompat.checkSelfPermission(context, permission.VIBRATE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ((Activity)context).requestPermissions(new String[]{permission.VIBRATE}, 2);
        }
    }
}
