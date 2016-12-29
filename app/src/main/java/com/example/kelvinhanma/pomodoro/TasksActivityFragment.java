package com.example.kelvinhanma.pomodoro;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kelvinhanma.pomodoro.data.PomoDbHelper;
import com.example.kelvinhanma.pomodoro.data.TaskListContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class TasksActivityFragment extends Fragment {
    private Activity mActivity;
    private TaskListAdapter mAdapter;
    private SQLiteDatabase mDb;

    public TasksActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tasks, container, false);
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
}
