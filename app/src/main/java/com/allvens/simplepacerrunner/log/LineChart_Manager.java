package com.allvens.simplepacerrunner.log;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.allvens.simplepacerrunner.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class LineChart_Manager {

    private LineChart lc;
    private Context context;

    public LineChart_Manager(LineChart lc, Context context) {
        this.context = context;
        this.lc = lc;
    }

    public void reset_Chart(){
        lc.invalidate();
        lc.clear();
    }

    public void setUp_ChartValues() {
        lc.setDragEnabled(false);
        lc.setScaleEnabled(false);
        lc.getAxisRight().setEnabled(false);
    }

    public void create_Chart(ArrayList<Log_DataEntry> currentWeekData){

        lc.getXAxis().setDrawLabels(false);
        lc.getXAxis().setTextColor(ContextCompat.getColor(context, R.color.focusAccent));
        lc.getAxisLeft().setTextColor(ContextCompat.getColor(context, R.color.focusAccent));

        lc.getLegend().setTextColor(ContextCompat.getColor(context, R.color.focusAccent));
        lc.getDescription().setEnabled(false);

        ArrayList<Entry> yValues = create_Entries(currentWeekData);
        LineDataSet set = new LineDataSet(yValues, "Last 20 Sessions - Meters");
        set.setCircleColor(ContextCompat.getColor(context, R.color.focusAccent));
        set.setValueTextColor(ContextCompat.getColor(context, R.color.focusAccent));
        set.setFillAlpha(110);
        set.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        set.setLineWidth(3f);
        set.setValueTextSize(10f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);

        LineData data = new LineData(dataSets);

        lc.setData(data);
    }

    /**
     * Creates Entries for chart
     * @param currentWeekData
     * @return Entry List
     */
    private ArrayList<Entry> create_Entries(ArrayList<Log_DataEntry> currentWeekData){
        ArrayList<Entry> yValues = new ArrayList<>();
        for(Log_DataEntry entry: currentWeekData){
            yValues.add(new Entry(entry.getPosition(), entry.getValue()));
        }
        return yValues;
    }
}