package com.jayyaj.todoister;

import android.content.DialogInterface;
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
import com.google.android.material.snackbar.Snackbar;
import com.jayyaj.todoister.model.Priority;
import com.jayyaj.todoister.model.SharedViewModel;
import com.jayyaj.todoister.model.Task;
import com.jayyaj.todoister.model.TaskViewModel;
import com.jayyaj.todoister.util.Utils;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private EditText todoTitleEditText;
    private Button todoDateShortcut;
    private Button todoPriorityShortcut;
    private RadioGroup priorityRadioGroup;
    private Group priorityGroup;
    private ImageButton todoSaveButton;
    private CalendarView calendarView;
    private Group calendarGroup;
    private Calendar c = Calendar.getInstance();
    private Date selectedDate;
    private SharedViewModel sharedViewModel;
    private boolean isEditable;
    private Priority priority = Priority.MEH;
    private int selectedButtonId;
    private RadioButton selectedRadioButton;

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

    @Override
    public void onPause() {
        super.onPause();
        sharedViewModel.setIsEditable(false);
        todoTitleEditText.setText("");
        selectedDate = c.getTime();
        priority = Priority.MEH;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedViewModel.getIsEditable() && sharedViewModel.getSelectedItem().getValue() != null) {
            isEditable = sharedViewModel.getIsEditable();
            Task task = sharedViewModel.getSelectedItem().getValue();
            todoTitleEditText.setText(task.getName());
            selectedDate = task.dueDate;
            priority = task.priority;
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        todoDateShortcut.setOnClickListener(v -> {
            Utils.hideSoftKeyboard(v);
            calendarGroup.setVisibility(calendarGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });

        calendarView.setOnDateChangeListener((calView, year, month, day) -> {
            c.clear();
            c.set(year, month, day);
            selectedDate = c.getTime();
        });

        todoPriorityShortcut.setOnClickListener(v -> {
            Utils.hideSoftKeyboard(v);
            priorityGroup.setVisibility(priorityGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);

            priorityRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                if (priorityGroup.getVisibility() == View.VISIBLE) {
                    selectedButtonId = checkedId;
                    selectedRadioButton = view.findViewById(selectedButtonId);
                    if (selectedRadioButton.getId() == R.id.priorityYikesRadio) {
                        priority = Priority.YIKES;
                    } else if (selectedRadioButton.getId() == R.id.priorityMediumRadio) {
                        priority = Priority.MEH;
                    } else if (selectedRadioButton.getId() == R.id.priorityLowRadio) {
                        priority = Priority.CHILL;
                    } else {
                        priority = Priority.CHILL;
                    }
                } else {
                    priority = Priority.CHILL;
                }
            });
        });

        todoSaveButton.setOnClickListener(v -> {
            String task = todoTitleEditText.getText().toString().trim();

            if(!TextUtils.isEmpty(task) && selectedDate != null && priority != null) {
                Task newtask = new Task(task, priority, selectedDate, Calendar.getInstance().getTime(), false);
                if (isEditable) {
                    Task updatedTask = sharedViewModel.getSelectedItem().getValue();
                    updatedTask.setName(task);
                    updatedTask.setPriority(priority);
                    updatedTask.setDueDate(selectedDate);
                    TaskViewModel.update(updatedTask);
                    sharedViewModel.setIsEditable(false);
                } else {
                    TaskViewModel.create(newtask);
                }
                todoTitleEditText.setText("");
                if (this.isVisible()) {
                    this.dismiss();
                }
            } else {
                Snackbar.make(todoSaveButton, R.string.empty_field, Snackbar.LENGTH_SHORT).show();
            }
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