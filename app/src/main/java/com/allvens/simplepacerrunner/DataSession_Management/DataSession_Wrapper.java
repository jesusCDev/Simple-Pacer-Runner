package com.allvens.simplepacerrunner.DataSession_Management;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataSession_Wrapper {

    private static final String TAG = DataSession_Wrapper.class.getSimpleName();

    private SQLiteDatabase database;
    private Database_SQLiteOpenHelper dbHelper;

    public DataSession_Wrapper(Context context){
        this.dbHelper = new Database_SQLiteOpenHelper(context);
    }

    public void open(){
        this.database = dbHelper.getWritableDatabase();

        Log.d(TAG, "DATABASE IS OPEN");
    }

    public void close(){
        dbHelper.close();

        Log.d(TAG, "DATABASE IS CLOSE");
    }

    public void create_Session(DataSession session){
        ContentValues values = new ContentValues();
        values.put(DataSession_Contract.DataSession_Entry.COLUMN_DATE, session.getDate());
        values.put(DataSession_Contract.DataSession_Entry.COLUMN_STAGE, session.getStage());
        values.put(DataSession_Contract.DataSession_Entry.COLUMN_LEVEL, session.getLevel());
        values.put(DataSession_Contract.DataSession_Entry.COLUMN_DISTANCE, session.getDistance());

        long rowID = database.insert(DataSession_Contract.DataSession_Entry.TABLE_NAME, null, values);
        Log.d(TAG, "Session with id: " + rowID);
    }

    public List<DataSession> get_AllSessions(){
        List<DataSession> sessions = new ArrayList<>();

        String selectQuery = "SELECT * FROM session_info";
        Cursor cursor = database.rawQuery(selectQuery, null);
        try{
            while(cursor.moveToNext()){
                DataSession session = new DataSession(
                        cursor.getString(cursor.getColumnIndex(DataSession_Contract.DataSession_Entry.COLUMN_DATE)),
                        cursor.getInt(cursor.getColumnIndex(DataSession_Contract.DataSession_Entry.COLUMN_DISTANCE)),
                        cursor.getInt(cursor.getColumnIndex(DataSession_Contract.DataSession_Entry.COLUMN_STAGE)),
                        cursor.getInt(cursor.getColumnIndex(DataSession_Contract.DataSession_Entry.COLUMN_LEVEL)));

                session.setId(cursor.getInt(cursor.getColumnIndex(DataSession_Contract.DataSession_Entry._ID)));
                sessions.add(session);
            }
        } finally {
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return sessions;
    }

    public void update_Session(DataSession session){
        ContentValues values = new ContentValues();
        values.put(DataSession_Contract.DataSession_Entry.COLUMN_DATE, session.getDate());
        values.put(DataSession_Contract.DataSession_Entry.COLUMN_DISTANCE, session.getDistance());
        values.put(DataSession_Contract.DataSession_Entry.COLUMN_STAGE, session.getStage());
        values.put(DataSession_Contract.DataSession_Entry.COLUMN_LEVEL, session.getLevel());

        String selection = DataSession_Contract.DataSession_Entry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(session.getId())};

        int count = database.update(DataSession_Contract.DataSession_Entry.TABLE_NAME, values, selection, selectionArgs);
        Log.d(TAG, "Log Updated: " + count);
    }

    public void delete_Session(DataSession session){
        String selection = DataSession_Contract.DataSession_Entry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(session.getId())};

        int count = database.delete(DataSession_Contract.DataSession_Entry.TABLE_NAME, selection, selectionArgs);
        Log.d(TAG, "Log Deleted: " + count);
    }

    public void delete_AllSessions(){
        int count = database.delete(DataSession_Contract.DataSession_Entry.TABLE_NAME, null, null);
        Log.d(TAG, "Log Deleted: " + count);
    }
}
