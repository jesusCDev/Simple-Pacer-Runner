package com.allvens.simplepacerrunner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.allvens.simplepacerrunner.permission_manager.Permission_Checker;
import com.allvens.simplepacerrunner.session_data.DataSession;
import com.allvens.simplepacerrunner.session_data.DataSession_Wrapper;
import com.allvens.simplepacerrunner.controllers.Pacer_Timer;
import com.allvens.simplepacerrunner.controllers.UI_Feedback;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout cl_home_background;
    private TextView tv_Stage;
    private TextView tv_Time;
    private TextView tv_Level;
    private Button btn_SettingsAndExit;
    private Button btn_PlayAndPause;
    private Button btn_LogAndSave;

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

        tv_Stage = findViewById(R.id.tv_Stage);
        tv_Time = findViewById(R.id.tv_Time);
        tv_Level = findViewById(R.id.tv_Level);

        btn_SettingsAndExit = findViewById(R.id.btn_Home_SettingsAndExit);
        btn_PlayAndPause = findViewById(R.id.btn_Home_PlayAndPause);
        btn_LogAndSave = findViewById(R.id.btn_Home_LogAndSave);

        cl_home_background = findViewById(R.id.cl_home_background);

        dbWrapper = new DataSession_Wrapper(this);

        ui = new UI_Feedback();
        ui.set_Labels(tv_Stage, tv_Level, tv_Time);
        ui.set_Vibrator((Vibrator) getSystemService(Context.VIBRATOR_SERVICE));
        ui.set_SharedPreferences(android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this));

        pt = new Pacer_Timer();
        pt.set_UIFeedBack(ui);
        pt.create_timer();

        btn_PlayAndPause.setOnClickListener(btnAction_StartSession());
        change_btnActionToOutOfSession();

        // TODO FIND OUT WHY
        PreferenceManager.setDefaultValues(this, R.xml.settings_screen, false);
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

    private View.OnClickListener btnAction_StartSession(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tracker_pause){
                    btn_PlayAndPause.setText("Pause");
                    pt.start_timer();
                    change_btnActionToInSession();
                }else{
                    btn_PlayAndPause.setText("Play");
                    pt.stop_timer();
                }
                tracker_pause = !tracker_pause;
            }
        };
    }

    private void change_btnActionToOutOfSession(){

        btn_LogAndSave.setText("Log");
        btn_SettingsAndExit.setText("Settings");

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
                printDatabase();
                dbWrapper.close();

                Intent intent = new Intent(MainActivity.this, LogActivity.class);
                startActivity(intent);
            }
        });
    }

    private void printDatabase(){
        for(DataSession session: dbWrapper.get_AllSessions()){
            Log.d("Database","Session Id: " + session.getId());
            Log.d("Database","Session Date: " + session.getDate());
            Log.d("Database","Session Distance: " + session.getDistance());
            Log.d("Database","Session Stage: " + session.getStage());
            Log.d("Database","Session Level: " + session.getLevel());
        }
    }


    private void change_btnActionToInSession(){

        btn_LogAndSave.setText("Save");
        btn_SettingsAndExit.setText("Exit");

        btn_SettingsAndExit.setOnClickListener(btnAction_ExitCurrentSession());
        btn_LogAndSave.setOnClickListener(btnAction_SaveCurrentSession());

        // TODO RESET CURRENT TIMER
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
        btn_PlayAndPause.setText("Play");
        tracker_pause = false;
        pt.stop_timer();
        pt.reset_timer();
        pt.create_timer();
        change_btnActionToOutOfSession();
    }
}
