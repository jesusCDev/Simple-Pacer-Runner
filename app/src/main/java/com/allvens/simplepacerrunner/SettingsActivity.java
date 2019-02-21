package com.allvens.simplepacerrunner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.allvens.simplepacerrunner.data_manager.Prefs_Values;
import com.allvens.simplepacerrunner.settings_manager.Documentation.Constants_OpenDocumentation;
import com.allvens.simplepacerrunner.settings_manager.Settings_Manager;

public class SettingsActivity extends AppCompatActivity{

    private Settings_Manager manager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch sVibrate = findViewById(R.id.s_settings_Vibrate);
        Switch sSound = findViewById(R.id.s_settings_Sound);
        Switch sNotification = findViewById(R.id.s_settings_Notification);

        TextView tvTimeDisplay = findViewById(R.id.tv_settings_Time);

        Button btnSu = findViewById(R.id.btn_settings_notificationDaySU);
        Button btnM = findViewById(R.id.btn_settings_notificationDayM);
        Button btnTu = findViewById(R.id.btn_settings_notificationDayTU);
        Button btnW = findViewById(R.id.btn_settings_notificationDayW);
        Button btnTh = findViewById(R.id.btn_settings_notificationDayTH);
        Button btnF = findViewById(R.id.btn_settings_notificationDayF);
        Button btnSa = findViewById(R.id.btn_settings_notificationDaySA);

        manager = new Settings_Manager(this);
        manager.setUp_SettingsValues(sVibrate, sSound, sNotification);
        manager.setUp_TimeDisplay(tvTimeDisplay);
        manager.setUP_DailyNotificationBtns(btnSu, btnM, btnTu, btnW, btnTh, btnF, btnSa);

        sVibrate.setOnCheckedChangeListener(manager.update_PrefSettings(Prefs_Values.VIBRATE_ON));
        sSound.setOnCheckedChangeListener(manager.update_PrefSettings(Prefs_Values.SOUND_ON));

        sNotification.setOnCheckedChangeListener(manager.update_NotfiSettings(Prefs_Values.NOTIFICATION_ON));
    }

    /****************************************
     /**** BUTTON ACTIONS
     ****************************************/

    public void btnAction_ShowDocumentation(View view){
        String value;
        switch (view.getId()){
            case R.id.btn_settings__openSource:
                value = Constants_OpenDocumentation.OPEN_SOURCE;
                break;

            default:
                value = Constants_OpenDocumentation.TERMS_OF_USE;
                break;
        }
        Intent intent = new Intent(this, Settings_DocActivity.class);
        intent.putExtra(Constants_OpenDocumentation.CHOSEN_DOCUMENTATION, value);
        startActivity(intent);
    }

    public void btnAction_SetNotificationTime(View view){
        manager.update_NotificationTime(view);
    }

    public void btnAction_setDayNotifications(View view){
        manager.update_DayOfNotification(((Button)view));
    }
}
