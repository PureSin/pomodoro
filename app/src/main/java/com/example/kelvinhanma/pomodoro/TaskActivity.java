package com.example.kelvinhanma.pomodoro;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kelvinhanma.pomodoro.data.PomoDbHelper;
import com.example.kelvinhanma.pomodoro.data.TaskListContract;

import java.util.concurrent.TimeUnit;

public class TaskActivity extends AppCompatActivity {
    private static long INIT_DURATION = TimeUnit.MINUTES.toMillis(25);
    private static final String format = "MM:ss";
    private SQLiteDatabase mDb;
    private TextView mTimeTextView;
    private CountDownTimer mTimer;
    private Button mTimerButton;
    private long savedTime;
    private boolean stopped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        int id = intent.getIntExtra(TaskListAdapter.TASK_ID, -1);
        if (id == -1) {
            //TODO error handling
            return;
        }

        PomoDbHelper dbHelper = new PomoDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
        Cursor cursor = getTask(id);
        cursor.moveToFirst();
        setTitle(cursor.getString(cursor.getColumnIndex(TaskListContract.TaskListEntry.COLUMN_TASK_NAME)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTimeTextView = (TextView) findViewById(R.id.timerView);
        mTimer = createTimer(INIT_DURATION);
        mTimer.start();

        mTimerButton = (Button) findViewById(R.id.timerButton);
        mTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!stopped) {
                    mTimer.cancel();
                } else {
                    mTimer = createTimer(savedTime);
                    mTimer.start();
                }

            }
        });
    }

    private CountDownTimer createTimer(long initDuration) {
        return new CountDownTimer(initDuration, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                savedTime = millisUntilFinished;
                long mins = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                mTimeTextView.setText(mins + ":" + TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished % TimeUnit.MINUTES.toMillis(mins)));
            }

            public void onFinish() {
                mTimeTextView.setText("Out of time!");
            }
        };
    }

    private Cursor getTask(int id) {
        String selection = TaskListContract.TaskListEntry._ID + "=" + id;
        return mDb.query(
                TaskListContract.TaskListEntry.TABLE_NAME,
                null,
                selection,
                null,
                null,
                null,
                null
        );
    }

}
