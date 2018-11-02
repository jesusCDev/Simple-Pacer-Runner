package com.allvens.simplepacerrunner.log;

import android.content.Context;
import android.content.SharedPreferences;

import com.allvens.simplepacerrunner.session_data.DataSession;
import com.allvens.simplepacerrunner.session_data.DataSession_Wrapper;

import java.util.ArrayList;
import java.util.List;

public class LogActivity_Manager {

    private DataSession_Wrapper dsWrapper;
    private SharedPreferences sharedPrefs;

    public int set_CurrentSession(int sessionID){
        return sessionID;
    }

    public void set_DataSessionContext(Context context){
        dsWrapper = new DataSession_Wrapper(context);;
        dsWrapper.open();
    }

    public void set_SharedPreferences(SharedPreferences sharedPrefs){
        this.sharedPrefs = sharedPrefs;
    }

    /****************************************
     /**** Data Management
     ****************************************/
    public ArrayList<Log_DataEntry> get_ChartData(){
        ArrayList<Log_DataEntry> dataEntry = new ArrayList<>();

        int iter = 0;
        if(dsWrapper.get_AllSessions().size() > 20){
            iter = (dsWrapper.get_AllSessions().size() - 20);
        }
        for(int i = iter; i < dsWrapper.get_AllSessions().size(); i++){
            dataEntry.add(new Log_DataEntry(i, dsWrapper.get_AllSessions().get(i).getDistance()));
        }
        return dataEntry;
    }

    public List<DataSession> get_AllSessions(){
        return dsWrapper.get_AllSessions();
    }

    public void add_Session(DataSession session){
        dsWrapper.create_Session(session);
    }

    public DataSession get_Session(int sessionID){
        return dsWrapper.get_AllSessions().get(sessionID);
    }

    public String[] get_AllSessionsNames(){
        String[] sessionNames = new String[get_AllSessions().size()];
        for(int i = 0; i < get_AllSessions().size(); i++){
            sessionNames[i] = get_AllSessions().get(i).getDate();
        }
        return sessionNames;
    }

    public boolean check_ifEmptySessionsLogged(){
        return dsWrapper.get_AllSessions().size() > 0;
    }

    public void delete_Session(DataSession session){
        dsWrapper.delete_Session(session);
    }

    /****************************************
     /**** UI DATA
     ****************************************/
    public int get_TotalRun(){
        return dsWrapper.get_AllSessions().size();
    }

    public int get_TotalDistance(){
        int totalDistance = 0;
        for(DataSession session: dsWrapper.get_AllSessions()){
            totalDistance += session.getDistance();
        }

        return totalDistance;
    }

    public String get_BestRun(){

        int bestDistance = 0;
        DataSession sessionSaved = dsWrapper.get_AllSessions().get(0);

        for(DataSession session: dsWrapper.get_AllSessions()){
            if(session.getDistance() > bestDistance){
                bestDistance = (int)session.getDistance();
                sessionSaved = session;
            }
        }

        return sessionSaved.getDate();
    }
}
