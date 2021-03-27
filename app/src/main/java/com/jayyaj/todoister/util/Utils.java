package com.jayyaj.todoister.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.jayyaj.todoister.model.Priority;
import com.jayyaj.todoister.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern("EEE, MMM d");
        return simpleDateFormat.format(date);
    }

    public static void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static int priorityColor(Task task) {
        int color;
        if (task.getPriority() == Priority.YIKES) {
            color = Color.argb(150,201, 21, 23);
        }else if (task.getPriority() == Priority.MEH) {
            color = Color.argb(150,255, 179,0);
        }else {
            color = Color.argb(150, 51, 181, 229);
        }
        return color;
    }
}
