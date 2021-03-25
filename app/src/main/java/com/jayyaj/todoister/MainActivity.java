package com.jayyaj.todoister;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jayyaj.todoister.databinding.ActivityMainBinding;
import com.jayyaj.todoister.model.Priority;
import com.jayyaj.todoister.model.Task;
import com.jayyaj.todoister.model.TaskViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

        taskViewModel = new ViewModelProvider.AndroidViewModelFactory(
                MainActivity.this
                .getApplication())
                .create(TaskViewModel.class);

        taskViewModel.getAllTasks().observe(MainActivity.this, tasks -> {
            for (Task task : tasks)
                Log.d("Tasks", task.getName());
        });

        binding.fab.setOnClickListener(view -> {
            Task task = new Task("To do", Priority.CHILL, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), false);
            TaskViewModel.create(task);
        });
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}