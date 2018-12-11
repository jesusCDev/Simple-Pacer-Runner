package com.allvens.simplepacerrunner.log;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.allvens.simplepacerrunner.session_data.DataSession;
import com.allvens.simplepacerrunner.session_data.DataSession_Wrapper;
import com.github.mikephil.charting.charts.LineChart;

import java.util.List;

public class LogActivity_Manager {


    private DataSession_Wrapper dsWrapper;
    private LogActivity_UI_Manager ui_manager;

    private DataSession currentSelectedEntry  = null;

    public LogActivity_Manager(Context context){
        dsWrapper = new DataSession_Wrapper(context);
        open_Database();
    }

    public void setUp_LogActivity_UIManager(Context context, LineChart lc_log_Sessions, TextView bestRun, TextView totalDistanceRan, TextView totalRunsRan,
                                            TextView currentSessionDate, TextView currentSessionStage, TextView currentSessionLevel, TextView currentSessionDistance,
                                            Button selectSession){
        ui_manager = new LogActivity_UI_Manager(context, lc_log_Sessions, bestRun, totalDistanceRan, totalRunsRan,
                currentSessionDate, currentSessionStage, currentSessionLevel, currentSessionDistance, selectSession);
    }

    public void open_Database(){
        dsWrapper.open();
    }

    public void close_Database(){
        dsWrapper.close();
    }

    public void update_Screen(boolean dataChange){
        if(dataChange){
            ui_manager.set_CurrentSession(currentSelectedEntry);
            ui_manager.set_AllSessionData(get_AllSessions());

            ui_manager.update_DataSessionChanged();
        }else{
            ui_manager.update_SelectedSessionView();
        }
    }

    /****************************************
     /**** Data Management
     ****************************************/

    public void set_CurrentEntry(int pos){
        currentSelectedEntry = dsWrapper.get_AllSessions().get(pos);
    }

    public List<DataSession> get_AllSessions(){
        return dsWrapper.get_AllSessions();
    }

    public void add_Session(DataSession session){
        dsWrapper.create_Session(session);
    }

    public String[] get_AllSessionsNames(){
        String[] sessionNames = new String[get_AllSessions().size()];
        for(int i = 0; i < get_AllSessions().size(); i++){
            sessionNames[i] = get_AllSessions().get(i).getDate();
        }
        return sessionNames;
    }

    public void delete_Session(){
        dsWrapper.delete_Session(currentSelectedEntry);
    }

    public void delete_AllSessions(){
        dsWrapper.delete_AllSessions();
    }

    public DataSession get_CurrentSelectedEntry() {
        return currentSelectedEntry;
    }

    public void set_CurrentSelectedEntryToNull() {
        this.currentSelectedEntry = null;
    }
}
