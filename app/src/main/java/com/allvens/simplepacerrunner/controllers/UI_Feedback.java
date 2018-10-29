package com.allvens.simplepacerrunner.controllers;

import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.preference.PreferenceManager;
import android.widget.TextView;

import com.allvens.simplepacerrunner.settings.Settings_Values;

public class UI_Feedback {

    private TextView tv_stage;
    private TextView tv_level;
    private TextView tv_time;


    private ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
    private Vibrator v;
    private SharedPreferences sharedPrefs;

    public void set_Vibrator(Vibrator v){
        this.v = v;
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

    public void set_Time(String str){
        tv_time.setText(str);
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
}
