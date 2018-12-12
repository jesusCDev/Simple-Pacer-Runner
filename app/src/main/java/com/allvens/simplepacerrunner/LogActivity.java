package com.allvens.simplepacerrunner;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allvens.simplepacerrunner.log_manager.LogActivity_Manager;
import com.allvens.simplepacerrunner.data_manager.session_database.DataSession;
import com.github.mikephil.charting.charts.LineChart;

public class LogActivity extends AppCompatActivity {

    // TODO FIX STRINGS HERE
    private LogActivity_Manager manager;
    private DataSession unchangedSession;
    private LinearLayout ll_Log_Background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ll_Log_Background = findViewById(R.id.ll_Log_Background);

        TextView tv_Log_TotalDistanceCover = findViewById(R.id.tv_Log_TotalDistanceCover);
        TextView tv_Log_TotalRuns = findViewById(R.id.tv_Log_TotalRuns);
        TextView tv_Log_BestRun = findViewById(R.id.tv_Log_BestRun);

        Button btn_Log_SelectSession = findViewById(R.id.btn_Log_SelectSelection);

        TextView tv_log_CurrentSessionDate = findViewById(R.id.tv_log_CurrentSessionDate);
        TextView tv_log_CurrentSessionStage = findViewById(R.id.tv_log_CurrentSessionStage);
        TextView tv_log_CurrentSessionLevel = findViewById(R.id.tv_log_CurrentSessionLevel);
        TextView tv_log_CurrentSessionDistance = findViewById(R.id.tv_log_CurrentSessionDistance);

        LineChart lc_log_Sessions = findViewById(R.id.lc_log_Sessions);

        /********** Manager **********/
        manager = new LogActivity_Manager(this);

        manager.set_CurrentSelectedEntryToNull();
        manager.setUp_LogActivity_UIManager(this, lc_log_Sessions, tv_Log_BestRun, tv_Log_TotalDistanceCover,
                tv_Log_TotalRuns, tv_log_CurrentSessionDate, tv_log_CurrentSessionStage, tv_log_CurrentSessionLevel, tv_log_CurrentSessionDistance,
                btn_Log_SelectSession);
    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.open_Database();
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.close_Database();
    }

    /****************************************
     /**** BUTTON ACTIONS
     ****************************************/

    public void btnAction_SelectSession(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(LogActivity.this);

        builder.setTitle(this.getResources().getString(R.string.log_SelectSession));
        builder.setItems(manager.get_AllSessionsNames(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                manager.set_CurrentEntry(which);
                manager.update_Screen(false);
            }
        });
        builder.create().show();
    }

    public void btnAction_DeleteAllSessions(View view){
        if(manager.get_AllSessions().size() > 0){
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            new Toast(LogActivity.this).makeText(LogActivity.this, "All Sessions Deleted", Toast.LENGTH_SHORT).show();
                            manager.delete_AllSessions();
                            manager.set_CurrentSelectedEntryToNull();
                            manager.update_Screen(true);
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            new Toast(LogActivity.this).makeText(LogActivity.this, "Nothing was deleted", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to delete all sessions?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }else{
            new Toast(this).makeText(this,"No Data to Delete", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnAction_DeleteSelectedSection(View view) {
        if(manager.get_CurrentSelectedEntry() != null){
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            new Toast(LogActivity.this).makeText(LogActivity.this, "Session Deleted", Toast.LENGTH_SHORT).show();
                            unchangedSession = manager.get_CurrentSelectedEntry();
                            manager.delete_Session();
                            manager.set_CurrentSelectedEntryToNull();
                            manager.update_Screen(true);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            new Toast(LogActivity.this).makeText(LogActivity.this, "Nothing was deleted", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to delete current session?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }else{
            new Toast(this).makeText(this,"No Data to Delete", Toast.LENGTH_SHORT).show();
        }


    }
}
