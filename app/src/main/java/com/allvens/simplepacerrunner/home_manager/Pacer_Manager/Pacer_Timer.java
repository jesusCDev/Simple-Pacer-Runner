package com.allvens.simplepacerrunner.home_manager.Pacer_Manager;

import android.os.Handler;

import com.allvens.simplepacerrunner.home_manager.Home_UI_Manager;
import com.allvens.simplepacerrunner.data_manager.session_database.DataSession;

import java.util.Calendar;

public class Pacer_Timer {

    private Handler timerHandler;
    private Runnable timerRunnable;
    private long startTime = 0;

    private Home_UI_Manager ui_manager;
    private Pacer pacer;

    /****************************************
     /**** SETTERS
     ****************************************/

    public Pacer_Timer(){
        pacer = new Pacer();
    }

    public void set_UIFeedBack(Home_UI_Manager ui){
        this.ui_manager = ui;
    }

    /****************************************
     /**** TIMER METHODS
     ****************************************/

    public void create_timer() {
        // basic ui_manager
        ui_manager.set_Stage(Integer.toString((pacer.get_CurrentStage() + 1)));
        ui_manager.set_Level(Integer.toString(pacer.get_CurrentLevelTracker() + 1));

        final int time_forLevel = pacer.get_CurrentTimePerLevel();

        timerHandler  = new Handler();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                // time passed
                long millis = System.currentTimeMillis() - startTime;
                long time_tracker = (time_forLevel - millis);
                long seconds = (time_tracker / 1000);

                // update ui_manager
                ui_manager.set_Time(seconds, (time_tracker - (seconds * 1000)));

                timerHandler.postDelayed(this, 100);
                if(time_tracker < 0){

                    ui_manager.vibrate();
                    ui_manager.play_basicSound();

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
                        ui_manager.set_Screen(Home_UI_Manager.SCREEN_STARTING_DONE);
                        stop_timer();
                    }
                }

            }

        };
    }

    /********** Timer Actions **********/

    public void start_timer(){
        ui_manager.play_basicSound();
        ui_manager.vibrate();

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

    /****************************************
     /**** SESSIONS METHODS
     ****************************************/

    public DataSession get_Session(){
        return new DataSession(Calendar.getInstance().getTime().toString(), pacer.get_CurrentDistance(),
                pacer.get_CurrentStage(), pacer.get_CurrentLevelTracker());
    }
}
