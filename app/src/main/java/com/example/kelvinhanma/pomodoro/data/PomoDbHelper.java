package com.example.kelvinhanma.pomodoro.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kelvinhanma on 12/29/16.
 */

public class PomoDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pomo.db";

    private static final int DATABASE_VERSION = 1;

    public PomoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + TaskListContract.TaskListEntry.TABLE_NAME + " (" +
                TaskListContract.TaskListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TaskListContract.TaskListEntry.COLUMN_TASK_NAME + " TEXT NOT NULL, " +
                TaskListContract.TaskListEntry.COLUMN_POMOS + " INTEGER DEFAULT 0, " +
                TaskListContract.TaskListEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TaskListContract.TaskListEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
