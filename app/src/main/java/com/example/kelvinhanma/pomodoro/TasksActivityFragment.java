package com.example.kelvinhanma.pomodoro;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.kelvinhanma.pomodoro.data.PomoDbHelper;
import com.example.kelvinhanma.pomodoro.data.TaskListContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class TasksActivityFragment extends Fragment {
    private Activity mActivity;
    private TaskListAdapter mAdapter;
    private SQLiteDatabase mDb;
    private EditText mTaskNameEditText;

    public TasksActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tasks, container, false);

        mTaskNameEditText = (EditText) rootView.findViewById(R.id.task_edit_text);
        mTaskNameEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAddTaskClick(view);
            }
        });

        Activity activity = getActivity();
        RecyclerView taskListRecyclerView = (RecyclerView) rootView.findViewById(R.id.content_tasks);
        taskListRecyclerView.setLayoutManager(new LinearLayoutManager(activity));

        PomoDbHelper dbHelper = new PomoDbHelper(activity);
        mDb = dbHelper.getWritableDatabase();

        Cursor cursor = getAllTasks();
        mAdapter = new TaskListAdapter(activity, cursor);

        taskListRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    private long addTask(String taskName) {
        ContentValues cv = new ContentValues();
        cv.put(TaskListContract.TaskListEntry.COLUMN_TASK_NAME, taskName);
        long id = mDb.insert(TaskListContract.TaskListEntry.TABLE_NAME, null, cv);

        mAdapter.swapCursor(getAllTasks());
        return id;
    }

    private Cursor getAllTasks() {
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

    private void handleAddTaskClick(View view) {
        String taskName = mTaskNameEditText.getText().toString();
        if (taskName.length() == 0) {
            return;
        }

        addTask(taskName);
        mTaskNameEditText.clearFocus();;
        mTaskNameEditText.getText().clear();;
    }
}
