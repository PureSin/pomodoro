package com.example.kelvinhanma.pomodoro;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kelvinhanma.pomodoro.data.DataUtils;
import com.example.kelvinhanma.pomodoro.data.PomoDbHelper;
import com.example.kelvinhanma.pomodoro.data.TaskListContract;

public class TasksActivity extends AppCompatActivity {

    private TaskListAdapter mAdapter;
    private SQLiteDatabase mDb;
    private final static String LOG_TAG = TasksActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        RecyclerView taskListRecyclerView;
        taskListRecyclerView = (RecyclerView) this.findViewById(R.id.content_tasks);
        taskListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        PomoDbHelper dbHelper = new PomoDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        Cursor cursor = getAllTasks();
        mAdapter = new TaskListAdapter(this, cursor);

        taskListRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tasks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true; //TODO settings activity
        }

        return super.onOptionsItemSelected(item);
    }

    public Cursor getAllTasks() {
        return mDb.query(
                TaskListContract.TaskListEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                TaskListContract.TaskListEntry.COLUMN_TIMESTAMP
        );
    }
}
