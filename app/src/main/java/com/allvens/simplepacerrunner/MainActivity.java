package com.allvens.simplepacerrunner;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.allvens.simplepacerrunner.permission_manager.Permission_Checker;
import com.allvens.simplepacerrunner.session_data.DataSession_Wrapper;
import com.allvens.simplepacerrunner.home.Pacer_Timer;
import com.allvens.simplepacerrunner.home.UI_Feedback;

public class MainActivity extends AppCompatActivity {

    private LinearLayout ll_home_TimerPresentation;

    private ImageButton btn_SettingsAndExit;
    private ImageButton btn_PlayAndPause;
    private ImageButton btn_LogAndSave;

    private DataSession_Wrapper dbWrapper;

    private Pacer_Timer pt;
    private UI_Feedback ui;

    private boolean tracker_pause = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Permission_Checker pc = new Permission_Checker(this);
        pc.checkPermissionsOther();

        ll_home_TimerPresentation = findViewById(R.id.ll_home_TimerPresentation);

        btn_SettingsAndExit = findViewById(R.id.btn_Home_SettingsAndExit);
        btn_PlayAndPause = findViewById(R.id.btn_Home_PlayAndPause);
        btn_LogAndSave = findViewById(R.id.btn_Home_LogAndSave);

        dbWrapper = new DataSession_Wrapper(this);

        ui = new UI_Feedback(ll_home_TimerPresentation);
        ui.set_Context(this);
        ui.set_Vibrator((Vibrator) getSystemService(Context.VIBRATOR_SERVICE));
        ui.set_SharedPreferences(android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this));

        pt = new Pacer_Timer();
        pt.set_UIFeedBack(ui);

        change_btnActionToOutOfSession();

        PreferenceManager.setDefaultValues(this, R.xml.settings_screen, false);
    }

    private void btnAction_StartCountDown(){
        btn_PlayAndPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ui.start_CountDown(pt);

                tracker_pause = !tracker_pause;
                btn_PlayAndPause.setImageResource(R.drawable.ic_pause_circle_filled_white_24dp);
                change_btnActionToInSession();

                btnAction_StartSession();
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        dbWrapper.open();
    }

    @Override
    public void onPause(){
        super.onPause();
        dbWrapper.close();
    }

    private void btnAction_StartSession(){
        btn_PlayAndPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tracker_pause){
                    btn_PlayAndPause.setImageResource(R.drawable.ic_pause_circle_filled_white_24dp);
                    pt.start_timer();
                    change_btnActionToInSession();
                }else{
                    btn_PlayAndPause.setImageResource(R.drawable.ic_play_circle_filled_white_24dp);
                    pt.stop_timer();
                }
                tracker_pause = !tracker_pause;
            }
        });
    }

    private void change_btnActionToOutOfSession(){
        ui.cancel_CountdownTimer();
        btnAction_StartCountDown();
        ui.set_Screen(UI_Feedback.SCREEN_STARTING_COUNTDOWN);

        btn_LogAndSave.setImageResource(R.drawable.ic_log_24dp);
        btn_SettingsAndExit.setImageResource(R.drawable.ic_settings_black_24dp);

        btn_SettingsAndExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbWrapper.close();

                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        btn_LogAndSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbWrapper.close();

                Intent intent = new Intent(MainActivity.this, LogActivity.class);
                startActivity(intent);
            }
        });
    }

    private void change_btnActionToInSession(){
        btn_LogAndSave.setImageResource(R.drawable.ic_done_black_24dp);
        btn_SettingsAndExit.setImageResource(R.drawable.ic_cancel_24dp);

        btn_SettingsAndExit.setOnClickListener(btnAction_ExitCurrentSession());
        btn_LogAndSave.setOnClickListener(btnAction_SaveCurrentSession());
    }

    private View.OnClickListener btnAction_ExitCurrentSession(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset_Screen();
            }
        };
    }

    private View.OnClickListener btnAction_SaveCurrentSession(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbWrapper.create_Session(pt.get_Session());
                reset_Screen();
            }
        };
    }

    private void reset_Screen(){
        btn_PlayAndPause.setImageResource(R.drawable.ic_play_circle_filled_white_24dp);
        tracker_pause = false;
        pt.stop_timer();
        pt.reset_timer();
        pt.create_timer();
        change_btnActionToOutOfSession();
    }
}
