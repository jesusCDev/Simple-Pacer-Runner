package com.allvens.simplepacerrunner;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.allvens.simplepacerrunner.log.Log_ChartManager;
import com.allvens.simplepacerrunner.log.LogActivity_Manager;
import com.allvens.simplepacerrunner.session_data.DataSession;
import com.github.mikephil.charting.charts.LineChart;

public class LogActivity extends AppCompatActivity {

    private LinearLayoutCompat ll_Log_Background;
    private TextView tv_Log_TotalDistanceCover;
    private TextView tv_Log_TotalRuns;
    private TextView tv_Log_BestRun;
    private Button btn_Log_SelectSession;
    private Button btn_Log_DeleteAllSessions;
    private Button btn_Log_DeleteSelection;

    private TextView tv_log_CurrentSessionDate;
    private TextView tv_log_CurrentSessionStage;
    private TextView tv_log_CurrentSessionLevel;
    private TextView tv_log_CurrentSessionDistance;

    private LogActivity_Manager logActivityManager;
    private int currentSessionID;

    private LineChart lc_log_Sessions;
    private Log_ChartManager lineChartManager;

    private DataSession unchangedSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        ll_Log_Background = findViewById(R.id.ll_Log_Background);

        tv_Log_TotalDistanceCover = findViewById(R.id.tv_Log_TotalDistanceCover);
        tv_Log_TotalRuns = findViewById(R.id.tv_Log_TotalRuns);
        tv_Log_BestRun = findViewById(R.id.tv_Log_BestRun);
        btn_Log_SelectSession = findViewById(R.id.btn_Log_SelectSelection);
        btn_Log_DeleteAllSessions = findViewById(R.id.btn_Log_DeleteAllSessions);
        btn_Log_DeleteSelection = findViewById(R.id.btn_Log_DeleteSelection);

        tv_log_CurrentSessionDate = findViewById(R.id.tv_log_CurrentSessionDate);
        tv_log_CurrentSessionStage = findViewById(R.id.tv_log_CurrentSessionStage);
        tv_log_CurrentSessionLevel = findViewById(R.id.tv_log_CurrentSessionLevel);
        tv_log_CurrentSessionDistance = findViewById(R.id.tv_log_CurrentSessionDistance);

        /********** Data Management **********/
        logActivityManager = new LogActivity_Manager();
        logActivityManager.set_DataSessionContext(this);
        logActivityManager.set_SharedPreferences(android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this));


        /********** Line Chart **********/
        lc_log_Sessions = findViewById(R.id.lc_log_Sessions);
        lineChartManager = new Log_ChartManager(lc_log_Sessions, this);
        lineChartManager.setUp_ChartValues();
        lineChartManager.create_Chart(logActivityManager.get_ChartData());


        /********** UI Look **********/
        currentSessionID = logActivityManager.set_CurrentSession(logActivityManager.get_AllSessions().size());

        update_CurrentSessionUI(currentSessionID - 1);
    }

    private void update_Totals(){
        if(logActivityManager.check_ifEmptySessionsLogged()) {
            tv_Log_BestRun.setText(this.getResources().getString(R.string.log_BestSessions) + " " + logActivityManager.get_BestRun());
            tv_Log_TotalDistanceCover.setText(this.getResources().getString(R.string.log_TotalDistance) + " " + logActivityManager.get_TotalDistance() + " meters");
            tv_Log_TotalRuns.setText(this.getResources().getString(R.string.log_SessionsRan) + " " + logActivityManager.get_TotalRun());
        }
    }

    private void update_CurrentSessionUI(int currentSessionID){
        this.currentSessionID = currentSessionID;

        lineChartManager.reset_Chart();
        lineChartManager.setUp_ChartValues();
        lineChartManager.create_Chart(logActivityManager.get_ChartData());

        if(logActivityManager.check_ifEmptySessionsLogged()) {
            DataSession currentDataSession = logActivityManager.get_Session(currentSessionID);

            tv_log_CurrentSessionDate.setText(this.getResources().getString(R.string.log_SelectSessionDate) + " " + currentDataSession.getDate());
            tv_log_CurrentSessionStage.setText(this.getResources().getString(R.string.log_SelectSessionStage) + " " + (currentDataSession.getStage() + 1));
            tv_log_CurrentSessionLevel.setText(this.getResources().getString(R.string.log_SelectSessionLevel) + " " + (currentDataSession.getLevel() + 1));
            tv_log_CurrentSessionDistance.setText(this.getResources().getString(R.string.log_SelectSessionDistance) + " " + currentDataSession.getDistance() + " meters");

            btn_Log_SelectSession.setText(currentDataSession.getDate());

            update_Totals();
        }else{
            reset_AllValues();
        }
    }

    private void reset_AllValues(){
        btn_Log_SelectSession.setEnabled(false);
        btn_Log_DeleteAllSessions.setEnabled(false);
        btn_Log_DeleteSelection.setEnabled(false);


        tv_Log_BestRun.setText(this.getResources().getString(R.string.log_BestSessions) + " None");
        tv_Log_TotalDistanceCover.setText(this.getResources().getString(R.string.log_TotalDistance) + " 0 meters");
        tv_Log_TotalRuns.setText(this.getResources().getString(R.string.log_SessionsRan) + " 0");


        tv_log_CurrentSessionDate.setText(this.getResources().getString(R.string.log_SelectSessionDate) + " None");
        tv_log_CurrentSessionStage.setText(this.getResources().getString(R.string.log_SelectSessionStage) + " None");
        tv_log_CurrentSessionLevel.setText(this.getResources().getString(R.string.log_SelectSessionLevel) + " None");
        tv_log_CurrentSessionDistance.setText(this.getResources().getString(R.string.log_SelectSessionDistance) + " : 0 meters");
    }

    /****************************************
     /**** Button Actions
     ****************************************/
    public void btnAction_SelectSession(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(LogActivity.this);

        builder.setTitle(this.getResources().getString(R.string.log_SelectSession));
        builder.setItems(logActivityManager.get_AllSessionsNames(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                currentSessionID = logActivityManager.set_CurrentSession(which);
                update_CurrentSessionUI(currentSessionID);
            }
        });
        builder.create().show();
    }

    public void btnAction_DeleteAllSessions(View view){
        if(logActivityManager.check_ifEmptySessionsLogged()){
            for(DataSession session: logActivityManager.get_AllSessions()){
                logActivityManager.delete_Session(session);
            }
            update_CurrentSessionUI(currentSessionID);
        }
    }

    public void btnAction_DeleteSelectedSection(View view) {
        if(logActivityManager.check_ifEmptySessionsLogged()){

            unchangedSession = logActivityManager.get_Session(currentSessionID);

            logActivityManager.delete_Session(logActivityManager.get_Session(currentSessionID));
            update_CurrentSessionUI(0);

            Snackbar snackbar = Snackbar
                    .make(ll_Log_Background, unchangedSession.getDate() + " Session Deleted.", Snackbar.LENGTH_LONG)
                    .setAction("undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            logActivityManager.add_Session(unchangedSession);
                            update_CurrentSessionUI(0);
                        }
                    });
            snackbar.show();
        }
    }
}
