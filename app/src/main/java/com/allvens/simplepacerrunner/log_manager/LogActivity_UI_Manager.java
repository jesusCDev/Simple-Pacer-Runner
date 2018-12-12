package com.allvens.simplepacerrunner.log_manager;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.allvens.simplepacerrunner.R;
import com.allvens.simplepacerrunner.data_manager.session_database.DataSession;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.List;

// TODO FIX STRING PROBLEM HERE
public class LogActivity_UI_Manager {

    private Context context;
    
    private TextView bestRun;
    private TextView totalDistanceRan;
    private TextView totalRunsRan;
    private TextView currentSessionDate;
    private TextView currentSessionStage;
    private TextView currentSessionLevel;
    private TextView currentSessionDistance;
    
    private Button selectSession;
    private LineChart_Manager lineChart_Manager;

    private DataSession currentSession;
    private ArrayList<DataSession> allSessions = new ArrayList<>();

    /****************************************
     /**** SETTERS
     ****************************************/

    public LogActivity_UI_Manager(Context context, LineChart lc_log_Sessions, TextView bestRun, TextView totalDistanceRan, TextView totalRunsRan, 
                                  TextView currentSessionDate, TextView currentSessionStage, TextView currentSessionLevel, TextView currentSessionDistance,
                                  Button selectSession){
        this.context = context;
        this.bestRun = bestRun;
        this.totalDistanceRan = totalDistanceRan;
        this.totalRunsRan = totalRunsRan;
        this.currentSessionDate = currentSessionDate;
        this.currentSessionStage = currentSessionStage;
        this.currentSessionLevel = currentSessionLevel;
        this.currentSessionDistance = currentSessionDistance;
        this.selectSession = selectSession;

        lineChart_Manager = new LineChart_Manager(lc_log_Sessions, context);
    }

    public void set_AllSessionData(List<DataSession> currentWeekData){
        allSessions.clear();
        allSessions.addAll(currentWeekData);
    }

    public void set_CurrentSession(DataSession currentSession){
        this.currentSession = currentSession;
    }

    /****************************************
     /**** SCREEN UPDATER
     ****************************************/

    public void update_DataSessionChanged(){
        if(allSessions.size() > 0){
            update_SessionsView();
            if(currentSession != null) {
                update_SelectedSessionView();
            }else{
                update_NoSelectedSessionInfo();
            }
        }else{
            update_NoSelectedSessionInfo();
            update_NoSessionsInfo();
        }
        update_Chart();
    }

    /********** Setter - Info **********/

    public void update_SelectedSessionView(){
        currentSessionDate.setText(context.getResources().getString(R.string.log_SelectSessionDate) + " " + currentSession.getDate());
        currentSessionStage.setText(context.getResources().getString(R.string.log_SelectSessionStage) + " " + (currentSession.getStage() + 1));
        currentSessionLevel.setText(context.getResources().getString(R.string.log_SelectSessionLevel) + " " + (currentSession.getLevel() + 1));
        currentSessionDistance.setText(context.getResources().getString(R.string.log_SelectSessionDistance) + " " + currentSession.getDistance() + " meters");

        selectSession.setText("Sessions: " + currentSession.getDate());
    }

    private void update_SessionsView(){
        bestRun.setText(context.getResources().getString(R.string.log_BestSessions) + " " + get_BestRun());
        totalDistanceRan.setText(context.getResources().getString(R.string.log_TotalDistance) + " " + get_TotalDistance() + " meters");
        totalRunsRan.setText(context.getResources().getString(R.string.log_SessionsRan) + " " + get_TotalRun());
    }

    /********** Setter - No Info Available **********/

    private void update_NoSelectedSessionInfo(){
        currentSessionDate.setText(context.getResources().getString(R.string.log_SelectSessionDate) + " None");
        currentSessionStage.setText(context.getResources().getString(R.string.log_SelectSessionStage) + " None");
        currentSessionLevel.setText(context.getResources().getString(R.string.log_SelectSessionLevel) + " None");
        currentSessionDistance.setText(context.getResources().getString(R.string.log_SelectSessionDistance) + " : 0 meters");

        selectSession.setText(context.getResources().getString(R.string.log_SelectSession));
    }

    private void update_NoSessionsInfo(){
        bestRun.setText(context.getResources().getString(R.string.log_BestSessions) + " None");
        totalDistanceRan.setText(context.getResources().getString(R.string.log_TotalDistance) + " 0 meters");
        totalRunsRan.setText(context.getResources().getString(R.string.log_SessionsRan) + " 0");
    }

    /********** Get Total Session Info **********/

    public int get_TotalRun(){
        return allSessions.size();
    }

    public int get_TotalDistance(){
        int totalDistance = 0;
        for(DataSession session: allSessions){
            totalDistance += session.getDistance();
        }

        return totalDistance;
    }

    public String get_BestRun(){
        int bestDistance = 0;
        for(DataSession session: allSessions){
            if(session.getDistance() > bestDistance){
                bestDistance = (int)session.getDistance();
            }
        }

        return Integer.toString(bestDistance);
    }

    /****************************************
     /**** Chart Manager
     ****************************************/

    private void update_Chart(){
        lineChart_Manager.reset_Chart();
        lineChart_Manager.setUp_ChartValues();
        lineChart_Manager.create_Chart(convert_SessionsToDataEntries());
    }

    private ArrayList<Log_DataEntry> convert_SessionsToDataEntries(){
        ArrayList<Log_DataEntry> dataEntry = new ArrayList<>();

        int iter = 0;
        if(allSessions.size() > 20){
            iter = (allSessions.size() - 20);
        }

        for(int i = iter; i < allSessions.size(); i++){
            dataEntry.add(new Log_DataEntry(i, allSessions.get(i).getDistance()));
        }

        return dataEntry;
    }
}
