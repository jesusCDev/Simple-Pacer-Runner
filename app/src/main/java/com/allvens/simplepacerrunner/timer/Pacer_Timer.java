package com.allvens.simplepacerrunner.timer;

import android.os.Handler;
import android.util.Log;

import com.allvens.simplepacerrunner.Pacer;

public class Pacer_Timer {

    private Handler timerHandler = new Handler();
    private Runnable timerRunnable;
    private long startTime = 0;

    private UI_Feedback ui;
    private Pacer pacer;

    public Pacer_Timer(){
        pacer = new Pacer();
    }


    public void set_UIFeedBack(UI_Feedback ui){
        this.ui = ui;
    }

    public void create_Timer() {

        // basic ui
        ui.set_Stage(Integer.toString((pacer.get_CurrentStage() + 1)));
        ui.set_Level(Integer.toString(pacer.get_CurrentLevelTracker()));

        final int time_forLevel = pacer.get_CurrentTimePerLevel();

        timerRunnable = new Runnable() {
            @Override
            public void run() {
                // time passed
                long millis = System.currentTimeMillis() - startTime;
                long time_tracker = (time_forLevel - millis);
                long seconds = (time_tracker / 1000);

                // update ui
                ui.set_Time(seconds + "." + (time_tracker - (seconds * 1000)));

                timerHandler.postDelayed(this, 100);
                if(time_tracker < 0){
                    if(pacer.get_CurrentStage() < 20){
                        if(pacer.get_CurrentLevelTracker() != 1){
                            pacer.set_CurrentLevelTracker((pacer.get_CurrentLevelTracker() - 1));
                        }else{
                            // defaults
                            pacer.set_CurrentStage(pacer.get_CurrentStage() + 1);
                            pacer.set_CurrentLevelValue(pacer.get_CurrentStage());

                            // trackers
                            pacer.set_CurrentLevelTracker(pacer.get_CurrentLevelValue());

                        }
                        timerHandler.removeCallbacks(this);
                        create_Timer();
                        start_Timer();
                    }else{
                        // TODO DO SOMETHING LIKE MAKE IT RED OR SOME SHIT
                        stop_timer();
                    }
                }

            }

        };
    }

    public void start_Timer(){
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
    }

    public void stop_timer(){
        timerHandler.removeCallbacks(timerRunnable);
    }
}
