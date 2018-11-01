package com.allvens.simplepacerrunner.log;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.allvens.simplepacerrunner.session_data.DataSession;
import com.allvens.simplepacerrunner.session_data.DataSession_Wrapper;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.List;

public class Log_DataManager {

    private Long totalRun;
    private String bestRun;

    private DataSession_Wrapper dsWrapper;
    private SharedPreferences sharedPrefs;

    public ArrayList<Log_DataEntry> get_ChartData(){
        ArrayList<Log_DataEntry> dataEntry = new ArrayList<>();

        for(int i = 0; i < dsWrapper.get_AllSessions().size(); i++){
            dataEntry.add(new Log_DataEntry(i, dsWrapper.get_AllSessions().get(i).getDistance(),
                    "Session"));
        }
        return dataEntry;
    }

    public void set_DaraSessionContext(Context context){
        dsWrapper = new DataSession_Wrapper(context);;
        dsWrapper.open();
    }

    public void set_SharedPreferences(SharedPreferences sharedPrefs){
        this.sharedPrefs = sharedPrefs;
    }

    public List<DataSession> get_AllSessions(){
        return dsWrapper.get_AllSessions();
    }

    public void delete_Session(DataSession session){
        dsWrapper.delete_Session(session);
    }

    public DataSession get_Session(int sessionID){
        for(DataSession session: dsWrapper.get_AllSessions()){
            if(session.getId() == sessionID){
                return session;
            }
        }
        return null;
    }

    public String[] get_AllSessionsNames(){
        String[] sessionNames = new String[get_AllSessions().size()];
        for(int i = 0; i < get_AllSessions().size(); i++){
            sessionNames[i] = get_AllSessions().get(i).getDate();
        }
        return sessionNames;
    }

    public void edit_Session(DataSession session){

    }

    public void update_Session(DataSession session){
        dsWrapper.update_Session(session);
    }

    public double get_TotalRun(){
        int totalRun = 0;
        for(DataSession session: dsWrapper.get_AllSessions()){
            totalRun += session.getDistance();
        }
        return totalRun;
    }

    public String get_BestRun(){
        // get last position i checked in sharedpreferences
        int lastCheckedPos = sharedPrefs.getInt(Log_SharedPrefs.KEY_LAST_POSITION_CHECKED_DB, 0);
        String lastCheckedValue = sharedPrefs.getString(Log_SharedPrefs.KEY_LAST_VALUE_CHECKED_DB, "");

        // check it with the current sesssion if different check everything again if same start checking from there
        if(check_IfLastRunIsTheSame(lastCheckedPos, lastCheckedValue)){
            check_ForBestRun(lastCheckedPos);
        }else{
            check_ForBestRun(0);
        }
        // (store the last value checked and its value)

        return "Best Run date and values";
    }

    private Boolean check_IfLastRunIsTheSame(int pos, String value){
        return false;
    }

    private String check_ForBestRun(int startingPos){
        return "";
    }

    public void create_Graph(){

    }

    public void recreate_Graph(){

    }
}
