package com.jayyaj.todoister.adapter;

import com.jayyaj.todoister.model.Task;

public interface OnTaskClickListener {
    void onTaskClick(Task task);
    void onTaskRadioButtonClick(Task task);
}
