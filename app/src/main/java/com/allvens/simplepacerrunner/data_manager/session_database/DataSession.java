package com.allvens.simplepacerrunner.data_manager.session_database;

public class DataSession {

    private int id;
    private String date;
    private long distance;
    private int stage;
    private int level;

    public DataSession(){}

    public DataSession(String date, long distance, int stage, int level){
        this.date = date;
        this.distance = distance;
        this.stage = stage;
        this.level = level;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
