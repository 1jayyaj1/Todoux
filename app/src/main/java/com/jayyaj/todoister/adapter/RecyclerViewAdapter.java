package com.jayyaj.todoister.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
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
    private final OnTaskClickListener onTaskClickListener;
    private final List<Task> taskList;

    //TODO Add onclick listener for each row
    public RecyclerViewAdapter(List<Task> taskList, OnTaskClickListener taskClickListener) {
        this.taskList = taskList;
        this.onTaskClickListener = taskClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Task task = taskList.get(position);
        String formatted = Utils.formatDate(task.getDueDate());

        ColorStateList colorStateList = new ColorStateList(new int[][]{
                new int[] {-android.R.attr.state_enabled},
                new int[] {android.R.attr.state_enabled}
        },
                new int[]{
                        Color.LTGRAY,
                        Utils.priorityColor(task)
                });

        holder.name.setText(task.getName());
        holder.day.setText(formatted);
        holder.day.setTextColor(Utils.priorityColor(task));
        holder.day.setChipIconTint(colorStateList);
        holder.isDone.setButtonTintList(colorStateList);
    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(taskList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnTaskClickListener taskClickListener;
        public AppCompatRadioButton isDone;
        public AppCompatTextView name;
        public Chip day;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.todo_row_todo);
            this.isDone = itemView.findViewById(R.id.todo_radio_button);
            this.day = itemView.findViewById(R.id.todo_row_chip);
            this.taskClickListener = onTaskClickListener;
            itemView.setOnClickListener(this);
            this.isDone.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Task task = taskList.get(getAdapterPosition());
            int id = v.getId();
            if (id == R.id.todo_row_layout) {
                taskClickListener.onTaskClick(task);
            } else if (id == R.id.todo_radio_button) {
                taskClickListener.onTaskRadioButtonClick(task);
            }
        }
    }
}
