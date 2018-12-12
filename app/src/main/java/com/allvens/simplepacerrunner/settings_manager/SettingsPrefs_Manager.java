package com.allvens.simplepacerrunner.settings_manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.allvens.simplepacerrunner.data_manager.Prefs_Values;

public class SettingsPrefs_Manager {

    private SharedPreferences prefs;
    private SharedPreferences.Editor edit;

    private String[] notification_days;

    public SettingsPrefs_Manager(Context context){
        prefs = context.getSharedPreferences(Prefs_Values.PREFS_NAMES, Context.MODE_PRIVATE);
        edit = prefs.edit();

        notification_days = prefs.getString(Prefs_Values.NOTIFICATION_DAYS, "-1,-1,-1,-1,-1,-1,-1").split(",");
    }

    public boolean get_PrefSetting(String prefKey){
        return prefs.getBoolean(prefKey, false);
    }

    public void update_PrefSetting(String prefKey, boolean value){
        edit.putBoolean(prefKey, value);
        edit.commit();
    }

    public void update_NotificationTime(int hour, int minute) {
        edit.putInt(Prefs_Values.NOTIFICATION_TIME_HOUR, hour);
        edit.putInt(Prefs_Values.NOTIFICATION_TIME_MINUTE, minute);
        edit.commit();
    }

    public void update_NotificationDay(int i) {
        notification_days[i] = Integer.toString(Integer.parseInt(notification_days[i]) * -1);

        Log.d("Bug", "New Values: " + notification_days[i]);

        edit.putString(Prefs_Values.NOTIFICATION_DAYS, convert_ArrayToString(notification_days));
        edit.commit();
    }

    private String convert_ArrayToString(String[] notification_days){
        StringBuilder sb = new StringBuilder();

        for(String day: notification_days){
            sb.append(day);
            sb.append(",");
        }
        sb.deleteCharAt((sb.length() - 1));

        return sb.toString();
    }

    public int get_NotifiHour() {
        return prefs.getInt(Prefs_Values.NOTIFICATION_TIME_HOUR, 0);
    }

    public int get_NotifiMinute(){
        return prefs.getInt(Prefs_Values.NOTIFICATION_TIME_MINUTE, 0);
    }

    public boolean get_NotificationDayValue(int i) {
        return (Integer.parseInt(notification_days[i]) == 1);
    }
}
