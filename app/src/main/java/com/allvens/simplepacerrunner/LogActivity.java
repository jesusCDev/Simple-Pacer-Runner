package com.allvens.simplepacerrunner;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.allvens.simplepacerrunner.log.Log_ChartManager;
import com.allvens.simplepacerrunner.log.Log_DataManager;
import com.github.mikephil.charting.charts.LineChart;

public class LogActivity extends AppCompatActivity {

    private TextView tv_Log_BestRun;
    private TextView tv_Log_TotalRuns;
    private Button btn_Log_SelectSession;

    private Log_DataManager ldM;
    private int currentSessionID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        tv_Log_BestRun = findViewById(R.id.tv_Log_BestRun);
        tv_Log_TotalRuns = findViewById(R.id.tv_Log_TotalRuns);
        btn_Log_SelectSession = findViewById(R.id.btn_Log_SelectSelection);

        ldM = new Log_DataManager();
        ldM.set_DaraSessionContext(this);
        ldM.set_SharedPreferences(android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this));

        // TODO MIGHT JUST CHANGE IT TO THIS WEEKS VALUES
        LineChart lc_log_Sessions = findViewById(R.id.lc_log_Sessions);
        Log_ChartManager lcM = new Log_ChartManager(lc_log_Sessions, this);
        lcM.setUp_ChartValues();
        lcM.create_Chart(ldM.get_ChartData());

        update_LogUI();

//        lcM.set_CurrentSession(ldM.get_AllSessions().size());
    }

    private void update_LogUI(){
        tv_Log_BestRun.setText("Best Run: " + ldM.get_BestRun());
        tv_Log_BestRun.setText("Total Run: " + ldM.get_TotalRun());
    }

    private void reset_ButtonToLatestSession(){
        btn_Log_SelectSession.setText("Hello");
    }

    private boolean check_ifEmptySessionsLoged(){
        return ldM.get_AllSessions().size() > 0;
    }

    public void btnAction_SelectSession(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(LogActivity.this);

        builder.setTitle("Select Session");
        builder.setItems(ldM.get_AllSessionsNames(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // The 'which' argument contains the index position
                // of the selected item
                Log.d("Bug", " ");
                Log.d("Bug", "Which Position: " + which);
                Log.d("Bug", "Selected: " + ldM.get_AllSessionsNames()[which]);
            }
        });
        builder.create().show();
    }

    public void btnAction_EditSelectedSelection(View view){
        if(check_ifEmptySessionsLoged()){
            ldM.edit_Session(ldM.get_Session(currentSessionID));
            reset_ButtonToLatestSession();
            update_LogUI();
        }
    }

    public void btnAction_DeleteSelectedSection(View view) {
        if(check_ifEmptySessionsLoged()){
            ldM.delete_Session(ldM.get_Session(currentSessionID));
            reset_ButtonToLatestSession();
            update_LogUI();
        }
    }
}
