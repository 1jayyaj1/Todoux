package com.jayyaj.todoister.util;

import androidx.room.TypeConverter;

import com.jayyaj.todoister.model.Priority;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Priority toPriority(String value) {
        return value == null ? null : Priority.valueOf(value);
    }

    @TypeConverter
    public static String fromPriority(Priority priority) {
        return priority == null ? null : priority.name();
    }
}
