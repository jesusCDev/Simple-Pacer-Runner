package com.allvens.simplepacerrunner.settings_manager.Documentation;

public class OrderList {

    private int orderTracker = 0;
    private char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public String get_NextAlpa(){
        orderTracker++;
        if(orderTracker > 26){
            orderTracker = 1;
        }

        return "( " + Character.toString(alphabet[orderTracker - 1]) + " ) ";
    }

    public String get_NextPos(){
        orderTracker++;
        return ((orderTracker) + ". ");
    }
}
