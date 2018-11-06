package com.allvens.simplepacerrunner.home;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.widget.TextView;

public class Screen_Setter {

    public final static int SCREEN_COUNTDOWN = 0;
    public final static int SCREEN_RUNNING = 1;
    public final static int SCREEN_COMPLETE = 2;

    private LinearLayoutCompat container;
    private Context context;

    public Screen_Setter(Context context, LinearLayoutCompat container){
        this.context = context;
        this.container = container;
    }

    public void set_Screen(int screen){
//        clearLyaout();
        if(screen == 0){
            create_StartingScreen();
        }else if(screen == 1){
            create_RunningScreen();
        }else{
            create_CompleteScreen();
        }
    }

    public void create_StartingScreen(){
        TextView tvText = new TextView(context);
        tvText.setText("Start");
    }

    public void create_RunningScreen(){
        TextView tv_Timer = new TextView(context);
        tv_Timer.setText("0.0");
        TextView tv_Stage = new TextView(context);
        tv_Stage.setText("Stage: ");
        TextView tv_Level = new TextView(context);
        tv_Level.setText("Level: ");

    }
    public void create_CompleteScreen(){

    }
}
