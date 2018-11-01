package com.allvens.simplepacerrunner.log;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class Log_ChartManager {

    private LineChart lc;
    private Context context;

    public Log_ChartManager(LineChart lc, Context context) {
        this.context = context;
        this.lc = lc;
    }

    public void setUp_ChartValues() {
        lc.setDragEnabled(true);
        lc.setScaleEnabled(false);

        lc.getAxisRight().setEnabled(false);
    }

    public void create_Chart(ArrayList<Log_DataEntry> currentWeekData){
        ArrayList<Entry> yValues = create_Entries(currentWeekData);

        lc.getXAxis().setDrawLabels(false);

        LineDataSet set = new LineDataSet(yValues, "This Weeks Sessions");

        set.setFillAlpha(110);
        set.setColor(Color.BLUE);
        set.setLineWidth(3f);
        set.setValueTextSize(10f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);

        LineData data = new LineData(dataSets);

        lc.setData(data);
    }

    private ArrayList<Entry> create_Entries(ArrayList<Log_DataEntry> currentWeekData){
        ArrayList<Entry> yValues = new ArrayList<>();
        for(Log_DataEntry entry: currentWeekData){
            yValues.add(new Entry(entry.getPosition(), entry.getValue()));
        }
        return yValues;
    }
}