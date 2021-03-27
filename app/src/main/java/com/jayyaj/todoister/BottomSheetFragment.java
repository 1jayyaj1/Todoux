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
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jayyaj.todoister.model.Priority;
import com.jayyaj.todoister.model.Task;
import com.jayyaj.todoister.model.TaskViewModel;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;

import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {
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
    private Calendar c = Calendar.getInstance();
    private Date selectedDate;

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
        todayButton.setOnClickListener(this);
        Button tomorrowButton = view.findViewById(R.id.tomorrowButton);
        tomorrowButton.setOnClickListener(this);
        Button nextWeekButton = view.findViewById(R.id.nextWeekButton);
        nextWeekButton.setOnClickListener(this);

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

            if(!TextUtils.isEmpty(task) && priority != null && selectedDate != null) {
                Task newtask = new Task(task, priority, selectedDate, Calendar.getInstance().getTime(), false);
                TaskViewModel.create(newtask);
            } else {
                //Toast.makeText(this, "Please specify the task", Toast.LENGTH_SHORT).show();
            }
        });

        calendarView.setOnDateChangeListener((calView, year, month, day) -> {
            c.clear();
            c.set(year, month, day);
            selectedDate = c.getTime();
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.todayButton) {
            c.add(Calendar.DAY_OF_YEAR, 0);
            selectedDate = c.getTime();
        } else if (id == R.id.tomorrowButton) {
            c.add(Calendar.DAY_OF_YEAR, 1);
            selectedDate = c.getTime();
        } else if (id == R.id.nextWeekButton) {
            c.add(Calendar.DAY_OF_YEAR, 7);
            selectedDate = c.getTime();
        }
    }
}