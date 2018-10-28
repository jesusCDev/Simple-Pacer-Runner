package com.allvens.simplepacerrunner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.allvens.simplepacerrunner.controllers.Pacer_Timer;
import com.allvens.simplepacerrunner.controllers.UI_Feedback;

public class MainActivity extends AppCompatActivity {

    private TextView tv_Stage;
    private TextView tv_Time;
    private TextView tv_Level;
    private Button btn_SettingsAndExit;
    private Button btn_PlayAndPause;
    private Button btn_LogAndSave;

    private Pacer_Timer pt;
    private UI_Feedback ui;

    private boolean tracker_pause = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_Stage = findViewById(R.id.tv_Stage);
        tv_Time = findViewById(R.id.tv_Time);
        tv_Level = findViewById(R.id.tv_Level);

        ui = new UI_Feedback();
        ui.set_Labels(tv_Stage, tv_Level, tv_Time);

        pt = new Pacer_Timer();
        pt.set_UIFeedBack(ui);
        pt.create_timer();

        btn_SettingsAndExit = findViewById(R.id.btn_Home_SettingsAndExit);
        btn_PlayAndPause = findViewById(R.id.btn_Home_PlayAndPause);
        btn_LogAndSave = findViewById(R.id.btn_Home_LogAndSave);

        btn_PlayAndPause.setOnClickListener(btnAction_StartSession());
        change_btnActionToOutOfSession();

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

            }
        });
        btn_LogAndSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
                reset_Screen(new Intent());
            }
        };
    }

    private View.OnClickListener btnAction_SaveCurrentSession(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset_Screen(new Intent());
            }
        };
    }

    private void reset_Screen(Intent intent){
        btn_PlayAndPause.setText("Play");
        tracker_pause = false;
        pt.stop_timer();
        pt.reset_timer();
        pt.create_timer();
        change_btnActionToOutOfSession();
    }
}
