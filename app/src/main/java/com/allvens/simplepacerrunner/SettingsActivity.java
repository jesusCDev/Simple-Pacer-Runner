package com.allvens.simplepacerrunner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.allvens.simplepacerrunner.settings.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
