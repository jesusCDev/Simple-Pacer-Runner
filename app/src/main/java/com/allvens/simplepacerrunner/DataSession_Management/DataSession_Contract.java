package com.allvens.simplepacerrunner.DataSession_Management;

import android.provider.BaseColumns;

final class DataSession_Contract {

    private DataSession_Contract(){}

    static final String CREATE_SESSION_ENTRY_TABLE =
            "CREATE TABLE " + DataSession_Entry.TABLE_NAME +
                    " ( " +
                    DataSession_Entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DataSession_Entry.COLUMN_DATE + " TEXT NOT NULL, " +
                    DataSession_Entry.COLUMN_STAGE + " INTEGER NOT NULL, " +
                    DataSession_Entry.COLUMN_LEVEL + " INTEGER NOT NULL, " +
                    DataSession_Entry.COLUMN_DISTANCE + " INTEGER NOT NULL, " +
                    "UNIQUE ( " + DataSession_Entry._ID + ") ON CONFLICT REPLACE )";

    public static class DataSession_Entry implements BaseColumns{

        public static final String TABLE_NAME = "session_info";

        public static final String COLUMN_DATE = "session_date";
        public static final String COLUMN_STAGE = "session_stage";
        public static final String COLUMN_LEVEL = "session_level";
        public static final String COLUMN_DISTANCE = "session_distance"; // this will be saved in meters
    }

}
