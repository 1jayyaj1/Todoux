package com.jayyaj.todoister.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.jayyaj.todoister.model.Task;
import com.jayyaj.todoister.util.TaskRoomDatabase;

import java.util.List;

public class TaskRepository {
    private final TaskDao taskDao;
    private final LiveData<List<Task>> allTasks;

    public TaskRepository(Application application) {
        TaskRoomDatabase db = TaskRoomDatabase.getDatabase(application);
        taskDao = db.taskDao();
        allTasks = taskDao.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() { return allTasks; }

    public void create(Task task) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.create(task);
        });
    }

    public LiveData<Task> getTask(long id) {
        return taskDao.getTask(id);
    }

    public void update(Task task) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.update(task);
        });
    }

    public void delete(Task task) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.delete(task);
        });
    }
}
