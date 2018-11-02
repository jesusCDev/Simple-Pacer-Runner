package com.allvens.simplepacerrunner.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.widget.TextView;

import com.allvens.simplepacerrunner.settings.Settings_Values;

public class UI_Feedback {

    public final static int RUNNING_SCREEN = 0;
    public final static int COUNTDOWN_SCREEN = 1;
    public final static int FINISHED_SCREEN = 2;

    private LinearLayoutCompat cl_home_background;

    private TextView tv_stage;
    private TextView tv_level;
    private TextView tv_time;

    private ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
    private Vibrator v;
    private SharedPreferences sharedPrefs;

    public UI_Feedback(LinearLayoutCompat cl_home_background){
        this.cl_home_background = cl_home_background;
    }

    public void set_Vibrator(Vibrator v){
        this.v = v;
    }

    public void set_Screen(int screen){
        if(screen == 0){
//            cl_home_background.setBackgroundColor(getResources().getColor(R.color.idname));
        }else if(screen == 1){
//            cl_home_background.setBackgroundColor(Color.DKGRAY);
        }else{
//            cl_home_background.setBackgroundColor(Color.DKGRAY);
        }
    }

    public void set_SharedPreferences(SharedPreferences sharedPrefs){
        this.sharedPrefs = sharedPrefs;
    }

    public void set_Labels(TextView tv_stage, TextView tv_level, TextView tv_time) {
        this.tv_stage = tv_stage;
        this.tv_level = tv_level;
        this.tv_time = tv_time;
    }

    public void set_Stage(String str){
        tv_stage.setText("Stage: " + str);
    }

    public void set_Level(String str){
        tv_level.setText("Level: " + str);
    }

    public void set_Time(long seconds, long miliSec){
        tv_time.setText(seconds + "." + fix_Time(miliSec));
    }

    private String fix_Time(long mili){
        if(Long.toString(mili).length() < 3){
            return mili + "0";
        }
        return Long.toString(mili);
    }

    public void playSound(){
        if(sharedPrefs.getBoolean(Settings_Values.KEY_PREF_SOUND_SWITCH, true)){
            toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 100);
        }
    }

    public void vibrate(){
        if(sharedPrefs.getBoolean(Settings_Values.KEY_PREF_VIBRATE_SWITCH, true)){
           // Vibrate for 500 milliseconds
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
              v.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
           }else{
              //deprecated in API 26
              v.vibrate(500);
           }
        }
    }

    public void totalReset_ScreenText(){
        tv_level.setText("Level: 1");
        tv_stage.setText("Stage: 1");
        tv_time.setText("0.0");
    }
}
