package com.example.kelvinhanma.pomodoro.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kelvinhanma on 12/28/16.
 */

public class DataUtils {
    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    public static void addTasks(SQLiteDatabase db) {
        if(db == null){
            return;
        }
        //create a list of fake guests
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(TaskListContract.TaskListEntry.COLUMN_TASK_NAME, "John");
        list.add(cv);

        cv = new ContentValues();
        cv.put(TaskListContract.TaskListEntry.COLUMN_TASK_NAME, "Tim");

        list.add(cv);

        cv = new ContentValues();
        cv.put(TaskListContract.TaskListEntry.COLUMN_TASK_NAME, "Jessica");

        list.add(cv);

        cv = new ContentValues();
        cv.put(TaskListContract.TaskListEntry.COLUMN_TASK_NAME, "Larry");

        list.add(cv);

        cv = new ContentValues();
        cv.put(TaskListContract.TaskListEntry.COLUMN_TASK_NAME, "Kim");

        list.add(cv);

        //insert all guests in one transaction
        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (TaskListContract.TaskListEntry.TABLE_NAME, null,null);
            //go through the list and add one by one
            for(ContentValues c:list){
                db.insert(TaskListContract.TaskListEntry.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            //too bad :(
        }
        finally
        {
            db.endTransaction();
        }
    }

    public static String formatTimestamp(Timestamp ts) {
        return FORMATTER.format(ts);
    }
}
