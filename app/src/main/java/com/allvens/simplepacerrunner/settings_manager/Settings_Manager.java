package com.allvens.simplepacerrunner.settings_manager;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.allvens.simplepacerrunner.R;
import com.allvens.simplepacerrunner.data_manager.Prefs_Values;
import com.allvens.simplepacerrunner.settings_manager.Notification_Manager.Notification_Controller;

import java.util.Calendar;

public class Settings_Manager {

    private Context context;

    private SettingsPrefs_Manager settingsPrefs;
    private Settings_UI_Manager ui_manager;
    private Notification_Controller notiManager;

    public Settings_Manager(Context context) {
        this.context = context;

        ui_manager = new Settings_UI_Manager(context);
        settingsPrefs = new SettingsPrefs_Manager(context);
        notiManager = new Notification_Controller(context, settingsPrefs.get_PrefSetting(Prefs_Values.NOTIFICATION_ON),
                settingsPrefs.get_NotifiHour(), settingsPrefs.get_NotifiMinute());
    }

    public void setUp_SettingsValues(Switch sVibrate, Switch sSound, Switch sNotification) {
        sVibrate.setChecked(settingsPrefs.get_PrefSetting(Prefs_Values.VIBRATE_ON));
        sSound.setChecked(settingsPrefs.get_PrefSetting(Prefs_Values.SOUND_ON));
        sNotification.setChecked(settingsPrefs.get_PrefSetting(Prefs_Values.NOTIFICATION_ON));
    }

    public void update_NotificationTime(final View view) {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                ui_manager.update_TimeStamp(((TextView)view), selectedHour, selectedMinute);
                settingsPrefs.update_NotificationTime(selectedHour, selectedMinute);
                notiManager.update_Time(selectedHour, selectedMinute);
            }
        }, hour, minute, false);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public void update_DayOfNotification(Button btn) {

        Log.d("Bug", "Clicked");

        int dayChanged;

        switch (btn.getId()){
            case R.id.btn_settings_notificationDaySU:
                dayChanged = 0;
                break;
            case R.id.btn_settings_notificationDayM:
                dayChanged = 1;
                break;
            case R.id.btn_settings_notificationDayTU:
                dayChanged = 2;
                break;
            case R.id.btn_settings_notificationDayW:
                dayChanged = 3;
                break;
            case R.id.btn_settings_notificationDayTH:
                dayChanged = 4;
                break;
            case R.id.btn_settings_notificationDayF:
                dayChanged = 5;
                break;
            default:
                dayChanged = 6;
                break;
        }
        settingsPrefs.update_NotificationDay(dayChanged);
        ui_manager.update_DailyNotificationBtnStyle(btn, settingsPrefs.get_NotificationDayValue(dayChanged));
    }

    public CompoundButton.OnCheckedChangeListener update_PrefSettings(final String prefKey){
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settingsPrefs.update_PrefSetting(prefKey, isChecked);
            }
        };
    }

    public CompoundButton.OnCheckedChangeListener update_NotfiSettings(final String prefKey){
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settingsPrefs.update_PrefSetting(prefKey, isChecked);

                notiManager.set_NotificationOn(isChecked);
                if(isChecked){
                    notiManager.create_Notification();
                }else{
                    notiManager.cancel_Notification();
                }
            }
        };
    }

    public void setUp_TimeDisplay(TextView tvTime) {
        ui_manager.update_TimeStamp(tvTime, notiManager.get_Hour(), notiManager.get_Min());
        notiManager.set_Time(notiManager.get_Hour(), notiManager.get_Min());
    }

    public void setUP_DailyNotificationBtns(Button btnSu, Button btnM, Button btnTu, Button btnW, Button btnTh, Button btnF, Button btnSa) {
        ui_manager.set_DailyNotificationBtns(btnSu, btnM, btnTu, btnW, btnTh, btnF, btnSa);
        ui_manager.update_DailyNotificationColors(settingsPrefs.get_NotificationDayValue(0), settingsPrefs.get_NotificationDayValue(1),
                settingsPrefs.get_NotificationDayValue(2), settingsPrefs.get_NotificationDayValue(3), settingsPrefs.get_NotificationDayValue(4),
                settingsPrefs.get_NotificationDayValue(5), settingsPrefs.get_NotificationDayValue(6));
    }
}
