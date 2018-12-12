package com.allvens.simplepacerrunner.home_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.allvens.simplepacerrunner.LogActivity;
import com.allvens.simplepacerrunner.R;
import com.allvens.simplepacerrunner.SettingsActivity;
import com.allvens.simplepacerrunner.home_manager.Pacer_Manager.Pacer_Timer;
import com.allvens.simplepacerrunner.data_manager.session_database.DataSession_Wrapper;

public class Home_Manager {

    private Context context;
    private Pacer_Timer pt;
    private Home_UI_Manager ui_manager;
    private DataSession_Wrapper dbWrapper;
    private boolean tracker_pause = false;

    private ImageButton btnSettingsAndExit;
    private ImageButton btnPlayAndPause;
    private ImageButton btnLogAndSave;

    /****************************************
     /**** SETTER
     ****************************************/

    public Home_Manager(Context context, LinearLayout ll_home_TimerPresentation,
                        ImageButton btnSettingsAndExit, ImageButton btnPlayAndPause, ImageButton btnLogAndSave){

        this.context = context;
        this.btnSettingsAndExit = btnSettingsAndExit;
        this.btnPlayAndPause = btnPlayAndPause;
        this.btnLogAndSave = btnLogAndSave;

        ui_manager = new Home_UI_Manager(context, ll_home_TimerPresentation);
        ui_manager.set_Vibrator((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE));
        ui_manager.set_SharedPreferences(android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(context));

        dbWrapper = new DataSession_Wrapper(context);
        dbWrapper.open();

        pt = new Pacer_Timer();
        pt.set_UIFeedBack(ui_manager);

        change_btnActionToOutOfSession();
    }

    /****************************************
     /**** BUTTON ACTIONS
     ****************************************/

    private void btnAction_StartCountDown(){
        btnPlayAndPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ui_manager.start_CountDown(pt, btnLogAndSave, btnPlayAndPause);
                btnLogAndSave.setEnabled(false);
                btnPlayAndPause.setEnabled(false);

                tracker_pause = !tracker_pause;
                btnPlayAndPause.setImageResource(R.drawable.ic_pause_circle_filled_white_24dp);
                change_btnActionToInSession();

                btnAction_StartSession();
            }
        });
    }

    private void btnAction_StartSession(){
        btnPlayAndPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tracker_pause){
                    btnPlayAndPause.setImageResource(R.drawable.ic_pause_circle_filled_white_24dp);
                    pt.start_timer();
                    change_btnActionToInSession();
                }else{
                    btnPlayAndPause.setImageResource(R.drawable.ic_play_circle_filled_white_24dp);
                    pt.stop_timer();
                }
                tracker_pause = !tracker_pause;
            }
        });
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

    /********** Change Button Actions **********/

    private void change_btnActionToInSession(){
        btnLogAndSave.setImageResource(R.drawable.ic_done_black_24dp);
        btnSettingsAndExit.setImageResource(R.drawable.ic_cancel_24dp);

        btnSettingsAndExit.setOnClickListener(btnAction_ExitCurrentSession());
        btnLogAndSave.setOnClickListener(btnAction_SaveCurrentSession());
    }

    private void change_btnActionToOutOfSession(){

        btnPlayAndPause.setEnabled(true);
        btnLogAndSave.setEnabled(true);
        ui_manager.cancel_CountdownTimer();
        btnAction_StartCountDown();
        ui_manager.set_Screen(Home_UI_Manager.SCREEN_STARTING_COUNTDOWN);

        btnLogAndSave.setImageResource(R.drawable.ic_log_24dp);
        btnSettingsAndExit.setImageResource(R.drawable.ic_settings_black_24dp);

        btnSettingsAndExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbWrapper.close();

                Intent intent = new Intent(context, SettingsActivity.class);
                context.startActivity(intent);
            }
        });

        btnLogAndSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbWrapper.close();

                Intent intent = new Intent(context, LogActivity.class);
                context.startActivity(intent);
            }
        });
    }

    /****************************************
     /**** EXTRA METHODS
     ****************************************/

    private void reset_Screen(){
        btnPlayAndPause.setImageResource(R.drawable.ic_play_circle_filled_white_24dp);
        tracker_pause = false;
        pt.stop_timer();
        pt.reset_timer();
        change_btnActionToOutOfSession();
    }

    public void open_Database(){
        dbWrapper.open();
    }

    public void close_Database(){
        dbWrapper.close();
    }
}
