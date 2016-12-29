package com.example.kelvinhanma.pomodoro;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kelvinhanma.pomodoro.data.TaskListContract;

import org.w3c.dom.Text;

/**
 * Created by kelvinhanma on 12/28/16.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    public TaskListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.task_list_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) return;

        String name = mCursor.getString(mCursor.getColumnIndex(TaskListContract.TaskListEntry.COLUMN_TASK_NAME));
        holder.nameTextView.setText(name);

        int pomos = mCursor.getInt(mCursor.getColumnIndex(TaskListContract.TaskListEntry.COLUMN_POMOS));
        holder.pomosTextView.setText(String.valueOf(pomos));

        holder.itemView.setTag(mCursor.getLong(mCursor.getColumnIndex(TaskListContract.TaskListEntry._ID)));
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor cursor) {
        mCursor = cursor;
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, pomosTextView;

        public TaskViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.tv_name);
            pomosTextView = (TextView) itemView.findViewById(R.id.tv_pomos);
        }
    }
}
