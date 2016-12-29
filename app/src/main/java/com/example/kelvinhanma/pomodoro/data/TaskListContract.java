package com.example.kelvinhanma.pomodoro.data;

import android.provider.BaseColumns;

/**
 * Created by kelvinhanma on 12/28/16.
 */

public class TaskListContract {
    private TaskListContract() {}

    public static final class TaskListEntry implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_TASK_NAME = "taskName", COLUMN_TIMESTAMP = "timestamp", COLUMN_POMOS = "pomos";
    }
}
