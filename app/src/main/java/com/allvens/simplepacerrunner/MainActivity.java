package com.allvens.simplepacerrunner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.allvens.simplepacerrunner.timer.Pacer_Timer;
import com.allvens.simplepacerrunner.timer.UI_Feedback;

public class MainActivity extends AppCompatActivity {

    private TextView tv_Stage;
    private TextView tv_Time;
    private TextView tv_Level;
    private Pacer_Timer pt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_Stage = findViewById(R.id.tv_Stage);
        tv_Time = findViewById(R.id.tv_Time);
        tv_Level = findViewById(R.id.tv_Level);

        UI_Feedback ui = new UI_Feedback();
        ui.set_Labels(tv_Stage, tv_Level, tv_Time);

        pt = new Pacer_Timer();
        pt.set_UIFeedBack(ui);
        pt.create_Timer();
        pt.start_Timer();
    }

}
