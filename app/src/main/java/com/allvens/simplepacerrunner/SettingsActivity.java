package com.allvens.simplepacerrunner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    private static final String KEY_PREF_VIBRATE_SWITCH = "vibrate_switch";
    private static final String KEY_PREF_SOUND_SWITCH = "sound_switch";
    private static final String KEY_PREF_DARK_MODE_SWITCH = "dark_mode_switch";
    private static final String KEY_PREF_METERS_SWITCH = "meters_switch";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Boolean spVibrate = sharedPreferences.getBoolean(KEY_PREF_VIBRATE_SWITCH, true);
        Boolean spSound = sharedPreferences.getBoolean(KEY_PREF_SOUND_SWITCH, true);
        Boolean spDarkMode = sharedPreferences.getBoolean(KEY_PREF_DARK_MODE_SWITCH, false);
        Boolean spMeters = sharedPreferences.getBoolean(KEY_PREF_METERS_SWITCH, true);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Log.d("Pref", "Changed: " + s);
    }
}
