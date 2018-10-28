package com.allvens.simplepacerrunner.DataSession_Management;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database_SQLiteOpenHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "pacer_sessions.db";
    private static final int VERSION_NUMBER = 2;

    public Database_SQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(DataSession_Contract.CREATE_SESSION_ENTRY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DataSession_Contract.DataSession_Entry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
