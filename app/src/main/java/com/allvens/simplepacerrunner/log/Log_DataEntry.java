package com.allvens.simplepacerrunner.log;

public class Log_DataEntry {

    private float position;
    private float value;
    private String name;

    public Log_DataEntry(int position, float value, String name){
        this.position = position;
        this.value = value;
        this.name = name;
    }


    public float getPosition() {
        return position;
    }

    public void setPosition(float position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
