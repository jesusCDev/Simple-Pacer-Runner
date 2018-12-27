package com.allvens.simplepacerrunner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.allvens.simplepacerrunner.settings_manager.Documentation.Constants_OpenDocumentation;
import com.allvens.simplepacerrunner.settings_manager.Documentation.TextDocumentation_OpenSource;
import com.allvens.simplepacerrunner.settings_manager.Documentation.TextDocumentation_PrivacyPolicy;
import com.allvens.simplepacerrunner.settings_manager.Documentation.TextDocumentation_TermsOfService;

public class Settings_DocActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_doc);

        LinearLayout llContainer = findViewById(R.id.ll_settings_doc_Container);

        switch (getIntent().getStringExtra(Constants_OpenDocumentation.CHOSEN_DOCUMENTATION)){
            case Constants_OpenDocumentation.OPEN_SOURCE:
                new TextDocumentation_OpenSource(this).show_Views(llContainer);
                break;
            case Constants_OpenDocumentation.TERMS_OF_USE:
                new TextDocumentation_TermsOfService(this).show_Views(llContainer);
                break;
            default:
                new TextDocumentation_PrivacyPolicy(this).show_Views(llContainer);
                break;
        }
    }
}
