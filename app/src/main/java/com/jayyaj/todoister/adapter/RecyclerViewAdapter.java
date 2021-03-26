package com.jayyaj.todoister.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.jayyaj.todoister.R;
import com.jayyaj.todoister.databinding.TaskRowBinding;
import com.jayyaj.todoister.model.Task;
import com.jayyaj.todoister.util.Utils;

import java.util.List;
import java.util.Objects;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private OnTaskClickListener onTaskClickListener;
    private List<Task> taskList;
    private Context context;

    //TODO Add onclick listener for each row
    public RecyclerViewAdapter(List<Task> taskList, Context context) {
        this.taskList = taskList;
        this.context = context;
        //this.onTaskClickListener = onTaskClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_row, parent, false);
        return new ViewHolder(view, onTaskClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Task task = Objects.requireNonNull(taskList.get(position));
        holder.name.setText(task.getName());
        holder.isDone.setChecked(task.getDone());
        holder.day.setText(Utils.formatDate(task.getDueDate()));
    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(taskList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnTaskClickListener onTaskClickListener;
        public AppCompatRadioButton isDone;
        public AppCompatTextView name;
        public Chip day;

        public ViewHolder(@NonNull View itemView, OnTaskClickListener onTaskClickListener) {
            super(itemView);
            name = itemView.findViewById(R.id.todo_row_todo);
            isDone = itemView.findViewById(R.id.todo_radio_button);
            day = itemView.findViewById(R.id.todo_row_chip);
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
