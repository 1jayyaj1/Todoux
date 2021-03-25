package com.jayyaj.todoister.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.jayyaj.todoister.R;
import com.jayyaj.todoister.model.Task;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private OnTaskClickListener onTaskClickListener;
    private List<Task> taskList;
    private Context context;

    public RecyclerViewAdapter(List<Task> taskList, Context context, OnTaskClickListener onTaskClickListener) {
        this.taskList = taskList;
        this.context = context;
        this.onTaskClickListener = onTaskClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_row, parent, false);
        return new ViewHolder(view, onTaskClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Task task = Objects.requireNonNull(taskList.get(position));
        holder.name.setText(task.getName());
        holder.isDone.setChecked(task.getDone());
        holder.dueDate.setText(task.getDueDate().toString().trim());
    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(taskList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnTaskClickListener onTaskClickListener;
        public TextView name;
        public RadioButton isDone;
        public Chip dueDate;

        public ViewHolder(@NonNull View itemView, OnTaskClickListener onTaskClickListener) {
            super(itemView);
            name = itemView.findViewById(R.id.todo_row_todo);
            isDone = itemView.findViewById(R.id.todo_radio_button);
            dueDate = itemView.findViewById(R.id.todo_row_chip);
            this.onTaskClickListener = onTaskClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onTaskClickListener.onTaskClick(getAdapterPosition());
        }
    }

    public interface OnTaskClickListener {
        void onTaskClick(int position);
    }
}
