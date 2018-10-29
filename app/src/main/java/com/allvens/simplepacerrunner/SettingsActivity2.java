package com.allvens.simplepacerrunner;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

public class SettingsActivity2 extends PreferenceFragmentCompat {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        addPreferencesFromResource(R.xml.settings_screen);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_screen, rootKey);
    }
}
