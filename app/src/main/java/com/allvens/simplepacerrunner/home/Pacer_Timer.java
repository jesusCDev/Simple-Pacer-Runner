package com.allvens.simplepacerrunner.home;

import android.os.Handler;
import com.allvens.simplepacerrunner.session_data.DataSession;

import java.util.Calendar;

public class Pacer_Timer {

    private Handler timerHandler;
    private Runnable timerRunnable;
    private long startTime = 0;

    private UI_Feedback ui;
    private Pacer pacer;

    public Pacer_Timer(){
        pacer = new Pacer();
    }

    public DataSession get_Session(){
        return new DataSession(Calendar.getInstance().getTime().toString(), pacer.get_CurrentDistance(),
                pacer.get_CurrentStage(), pacer.get_CurrentLevelTracker());
    }

    public void set_UIFeedBack(UI_Feedback ui){
        this.ui = ui;
    }

    public void create_timer() {
        // basic ui
        ui.set_Stage(Integer.toString((pacer.get_CurrentStage() + 1)));
        ui.set_Level(Integer.toString(pacer.get_CurrentLevelTracker() + 1));

        final int time_forLevel = pacer.get_CurrentTimePerLevel();

        timerHandler  = new Handler();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                // time passed
                long millis = System.currentTimeMillis() - startTime;
                long time_tracker = (time_forLevel - millis);
                long seconds = (time_tracker / 1000);

                // update ui
                ui.set_Time(seconds, (time_tracker - (seconds * 1000)));

                timerHandler.postDelayed(this, 100);
                if(time_tracker < 0){

                    ui.playSound();
                    ui.vibrate();

                    if(pacer.get_CurrentStage() != 20 || pacer.get_CurrentLevelTracker() != 15){
                        if(pacer.get_CurrentLevelTracker() != pacer.get_CurrentLevelValue()){
                            pacer.set_CurrentLevelTracker((pacer.get_CurrentLevelTracker() + 1));
                        }else{
                            // defaults
                            pacer.set_CurrentStage(pacer.get_CurrentStage() + 1);
                            pacer.set_CurrentLevelValue(pacer.get_CurrentStage());

                            // trackers
                            pacer.set_CurrentLevelTracker(0);

                        }
                        timerHandler.removeCallbacks(this);
                        create_timer();
                        start_timer();
                    }else{
                        ui.set_Screen(UI_Feedback.SCREEN_STARTING_DONE);
                        stop_timer();
                    }
                }

            }

        };
    }

    public void start_timer(){
        ui.playSound();
        ui.vibrate();

        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
    }

    public void reset_timer(){
        pacer = new Pacer();
    }

    public void stop_timer(){
        try{
            timerHandler.removeCallbacks(timerRunnable);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
