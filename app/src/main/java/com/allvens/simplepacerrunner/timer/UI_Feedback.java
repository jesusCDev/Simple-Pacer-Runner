package com.allvens.simplepacerrunner.timer;

import android.widget.TextView;

public class UI_Feedback {

    private TextView tv_stage;
    private TextView tv_level;
    private TextView tv_time;

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
        tv_time.setText(convert_MiliToSec(str));
    }

    private String convert_MiliToSec(String value){
        // TODO CONVERT THIS TO TIME
        return value;
    }
}
