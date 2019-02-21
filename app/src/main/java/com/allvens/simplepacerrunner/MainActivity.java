package com.allvens.simplepacerrunner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.allvens.simplepacerrunner.home_manager.Home_Manager;

public class MainActivity extends AppCompatActivity {

    private Home_Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout ll_home_TimerPresentation = findViewById(R.id.ll_home_TimerPresentation);

        ImageButton btn_SettingsAndExit = findViewById(R.id.btn_Home_SettingsAndExit);
        ImageButton btn_PlayAndPause = findViewById(R.id.btn_Home_PlayAndPause);
        ImageButton btn_LogAndSave = findViewById(R.id.btn_Home_LogAndSave);

        manager = new Home_Manager(this, ll_home_TimerPresentation,
                btn_SettingsAndExit, btn_PlayAndPause, btn_LogAndSave);
    }

    @Override
    public void onBackPressed() {
        manager.cancel_CurrentSession();
    }

    @Override
    public void onResume(){
        super.onResume();
        manager.open_Database();
    }

    @Override
    public void onPause(){
        super.onPause();
        manager.close_Database();
    }
}
