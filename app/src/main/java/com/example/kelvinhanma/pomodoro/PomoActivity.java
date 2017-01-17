package com.example.kelvinhanma.pomodoro;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.kelvinhanma.pomodoro.data.PomoDbHelper;
import com.example.kelvinhanma.pomodoro.data.TaskListContract;

public class PomoActivity extends AppCompatActivity {
    private long mTaskId;
    private SQLiteDatabase mDb;
    private Cursor mCursor;
    private TextView mTaskName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if (!intent.hasExtra(TasksActivityFragment.TASK_ID)) {
            // TODO error
        }
        mTaskId = intent.getLongExtra(TasksActivityFragment.TASK_ID, -1);
        // handle if -1?

        PomoDbHelper dbHelper = new PomoDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
        mCursor = getTask(mTaskId);
        mCursor.moveToFirst();

        mTaskName = (TextView) findViewById(R.id.tv_task_name);
        mTaskName.setText(mCursor.getString(mCursor.getColumnIndex(TaskListContract.TaskListEntry.COLUMN_TASK_NAME)));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action: " + mTaskId, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private Cursor getTask(long taskId) {
        String where = TaskListContract.TaskListEntry._ID + "=" + taskId;
        return mDb.query(true, TaskListContract.TaskListEntry.TABLE_NAME, null, where, null, null, null, null, null);
    }

}
