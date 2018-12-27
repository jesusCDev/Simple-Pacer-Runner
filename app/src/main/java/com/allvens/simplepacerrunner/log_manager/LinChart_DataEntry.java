package com.allvens.simplepacerrunner.log_manager;

/**
 * Line Chart - Data Entry
 */
public class LinChart_DataEntry {

    private float position;
    private float value;

    public LinChart_DataEntry(int position, float value){
        this.position = position;
        this.value = value;
    }


    public float getPosition() {
        return position;
    }

    public void setPosition(float position) {
        this.position = position;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
