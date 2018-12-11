package com.allvens.simplepacerrunner.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allvens.simplepacerrunner.R;
import com.allvens.simplepacerrunner.settings.Settings_Values;

public class UI_Feedback {
    private LinearLayout ll_home_TimerPresentation;

    private TextView tv_startAndCountDown;

    private TextView tv_stage;
    private TextView tv_level;
    private TextView tv_time;

    private TextView tv_finished;

    private ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
    private Vibrator v;
    private SharedPreferences sharedPrefs;
    private Context context;

    public static int SCREEN_STARTING_COUNTDOWN = 0;
    public static int SCREEN_STARTING_RUNNING = 1;
    public static int SCREEN_STARTING_DONE = 2;

    Handler timerHandler;
    private Runnable timerRunnable;

    public void cancel_CountdownTimer(){
        try{
            timerHandler.removeCallbacks(timerRunnable);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void start_CountDown(final Pacer_Timer pt, final ImageButton btn_LogAndSave, final ImageButton btn_PlayAndPause){

        final long startTime = System.currentTimeMillis();

        timerHandler = new Handler();
        timerRunnable = new Runnable() {
            @Override
            public void run() {

                // time passed
                long millis = System.currentTimeMillis() - startTime;
                long time_tracker = (5000 - millis);
                long seconds = (time_tracker / 1000);

                tv_startAndCountDown.setText(Long.toString(seconds + 1));
                play_basicSound();

                timerHandler.postDelayed(this, 1000);
                if(time_tracker < 0){
                    play_StartEndSound();
                    btn_LogAndSave.setEnabled(true);
                    btn_PlayAndPause.setEnabled(true);

                    set_Screen(SCREEN_STARTING_RUNNING);

                    timerHandler.removeCallbacks(timerRunnable);

                    pt.create_timer();
                    pt.start_timer();
                }

            }

        };
        timerHandler.postDelayed(timerRunnable, 0);
    }

    public void set_Screen(int screen){
        if(screen == SCREEN_STARTING_COUNTDOWN){
            create_StartingCountdownScreen();
        }else if(screen == SCREEN_STARTING_RUNNING){
            create_RunningScreen();
        }else{
            create_FinishedScreen();
        }

    }

    private void create_FinishedScreen(){
        clear_Layout();

        tv_finished = new TextView(context);
        tv_finished.setText(context.getResources().getString(R.string.home_Finished));
        tv_finished.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        setStyle(tv_finished, R.style.TS_StartingCountDown_Ending);

        ll_home_TimerPresentation.addView(tv_finished);
    }

    private void create_RunningScreen(){
        clear_Layout();

        tv_time = new TextView(context);
        tv_stage = new TextView(context);
        tv_level = new TextView(context);

        setStyle(tv_time, R.style.TS_Running_Time);
        setStyle(tv_stage, R.style.TS_Running_Stage);
        setStyle(tv_level, R.style.TS_Running_Level);

        ll_home_TimerPresentation.addView(tv_time);
        ll_home_TimerPresentation.addView(tv_stage);
        ll_home_TimerPresentation.addView(tv_level);
    }

    private void setStyle(TextView tv, int style){
        if (Build.VERSION.SDK_INT < 23) {
            tv.setTextAppearance(context, style);
        } else {
            tv.setTextAppearance(style);
        }
    }

    private void create_StartingCountdownScreen(){
        clear_Layout();

        tv_startAndCountDown = new TextView(context);
        tv_startAndCountDown.setText(context.getResources().getString(R.string.home_Start));
        tv_startAndCountDown.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        setStyle(tv_startAndCountDown, R.style.TS_StartingCountDown_Title);

        ll_home_TimerPresentation.addView(tv_startAndCountDown);
    }

    private void clear_Layout(){
        ll_home_TimerPresentation.removeAllViews();
    }

    public UI_Feedback(LinearLayout ll_home_TimerPresentation){
        this.ll_home_TimerPresentation = ll_home_TimerPresentation;
    }

    public void set_Context(Context context){
        this.context = context;
    }

    public void set_Vibrator(Vibrator v){
        this.v = v;
    }

    public void set_SharedPreferences(SharedPreferences sharedPrefs){
        this.sharedPrefs = sharedPrefs;
    }

    public void set_Stage(String str){
        tv_stage.setText(context.getResources().getString(R.string.home_Stage) + " " + str);
    }

    public void set_Level(String str){
        tv_level.setText(context.getResources().getString(R.string.home_Level) + " " + str);
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

    public void play_basicSound(){
        if(sharedPrefs.getBoolean(Settings_Values.KEY_PREF_SOUND_SWITCH, true)){
            toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 100);
        }
    }

    public void play_StartEndSound(){
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