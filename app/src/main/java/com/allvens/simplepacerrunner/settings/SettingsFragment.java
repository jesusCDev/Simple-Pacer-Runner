package com.allvens.simplepacerrunner.settings;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.allvens.simplepacerrunner.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_screen, rootKey);
    }
}
