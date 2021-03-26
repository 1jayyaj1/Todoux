package com.jayyaj.todoister;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jayyaj.todoister.model.Priority;
import com.jayyaj.todoister.model.Task;
import com.jayyaj.todoister.model.TaskViewModel;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;

import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    private EditText todoTitleEditText;
    private Button todoDateShortcut;
    private Button todoPriorityShortcut;
    private RadioGroup priorityRadioGroup;
    private Group priorityGroup;
    private RadioButton selectedRadioButton;
    private long selectedButtonId;
    private ImageButton todoSaveButton;
    private CalendarView calendarView;
    private Group calendarGroup;
    private long selectedDate;

    public BottomSheetFragment() {
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        calendarGroup = view.findViewById(R.id.calendarGroup);
        calendarView = view.findViewById(R.id.calendarView);
        todoDateShortcut = view.findViewById(R.id.todoDateShortcut);
        todoPriorityShortcut = view.findViewById(R.id.todoPriorityShortcut);
        todoTitleEditText = view.findViewById(R.id.todoTitleEditText);
        todoSaveButton = view.findViewById(R.id.todoSaveButton);
        priorityGroup = view.findViewById(R.id.priorityGroup);
        priorityRadioGroup = view.findViewById(R.id.priorityRadioGroup);

        //Only used inside onCreateView for now so don't need to instantiate them as instance variables yet
        Button todayButton = view.findViewById(R.id.todayButton);
        Button tomorrowButton = view.findViewById(R.id.tomorrowButton);
        Button nextWeekButton = view.findViewById(R.id.nextWeekButton);

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        todoDateShortcut.setOnClickListener(v -> {
            calendarGroup.setVisibility(calendarGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });

        todoPriorityShortcut.setOnClickListener(v -> {
            priorityGroup.setVisibility(priorityGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });

        todoSaveButton.setOnClickListener(v -> {
            String task = todoTitleEditText.getText().toString().trim();

            int priorityId = priorityRadioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) view.findViewById(priorityId);
            Priority priority = Priority.valueOf(radioButton.getText().toString().trim());

            Date dueDate = new Date(selectedDate);

            if(!TextUtils.isEmpty(task) && priority != null && dueDate != null) {
                Task newtask = new Task(task, priority, dueDate, Calendar.getInstance().getTime(), false);
                TaskViewModel.create(newtask);
            }
        });

        calendarView.setOnDateChangeListener((calView, year, month, day) -> {
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            selectedDate = c.getTimeInMillis();
        });
    }
}