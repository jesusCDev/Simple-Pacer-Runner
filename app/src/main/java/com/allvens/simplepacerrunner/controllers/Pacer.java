package com.allvens.simplepacerrunner.controllers;

public class Pacer {

    public final static int DISTANCE_RUN = 20; // meters
    public final static int TOTAL_STAGES = 21;
    private final static int[] levels = {7,8,8,9,9,10,10,11,11,11,12,12,13,13,13,14,14,15,15,16,16};
    private final static int[] time_PerLevel = {9000, 8000, 7580, 7200, 6860, 6550, 6260, 6000, 5760, 5540, 5330, 5140, 4970, 4800, 4650, 4500, 4360, 4240, 4110, 4000, 3890};

    private int current_Stage = 0;
    private int current_LevelValue = levels[0];

    private int current_LevelTracker = 0;

    // LEVEL TRACKER
    public void set_CurrentLevelTracker(int value){
        current_LevelTracker = value;
    }

    public int get_CurrentLevelTracker(){
        return current_LevelTracker;
    }

    // LEVEL
    public int get_CurrentLevelValue(){
        return current_LevelValue;
    }

    public void set_CurrentLevelValue(int value){
        current_LevelValue = levels[value];
    }

    // STAGE
    public void set_CurrentStage(int stage){
        current_Stage = stage;
    }

    public int get_CurrentStage(){
        return current_Stage;
    }

    // TIME PER LEVEL
    public int get_CurrentTimePerLevel(){
        return time_PerLevel[current_Stage];
    }
}
