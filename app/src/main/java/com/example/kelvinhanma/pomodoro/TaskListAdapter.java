package com.example.kelvinhanma.pomodoro;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kelvinhanma.pomodoro.data.DataUtils;
import com.example.kelvinhanma.pomodoro.data.TaskListContract;

import org.w3c.dom.Text;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by kelvinhanma on 12/28/16.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {
    private Context mContext;
    private Cursor mCursor;
    private ListItemClickListener mListItemClickListener;

    public interface ListItemClickListener {
        void onListItemClick(long clickedItemIndex);
    }

    public TaskListAdapter(Context context, Cursor cursor, ListItemClickListener listItemClickListener) {
        this.mContext = context;
        this.mCursor = cursor;
        this.mListItemClickListener = listItemClickListener;
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

        Timestamp timestamp = Timestamp.valueOf(mCursor.getString(mCursor.getColumnIndex(TaskListContract.TaskListEntry.COLUMN_TIMESTAMP)));
        holder.dateTextView.setText("(" + DataUtils.formatTimestamp(timestamp) + ")");
        holder.itemView.setTag(mCursor.getLong(mCursor.getColumnIndex(TaskListContract.TaskListEntry._ID)));

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor cursor) {
        if (mCursor != null) mCursor.close();
        mCursor = cursor;
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
    }


    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameTextView, pomosTextView, dateTextView;

        public TaskViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.tv_name);
            pomosTextView = (TextView) itemView.findViewById(R.id.tv_pomos);
            dateTextView = (TextView) itemView.findViewById(R.id.tv_task_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickPosition = getAdapterPosition();
            mCursor.moveToPosition(clickPosition);
            mListItemClickListener.onListItemClick(mCursor.getLong(mCursor.getColumnIndex(TaskListContract.TaskListEntry._ID)));
        }
    }
}
