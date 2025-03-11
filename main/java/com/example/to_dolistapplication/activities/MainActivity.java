package com.example.to_dolistapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_dolistapplication.R;
import com.example.to_dolistapplication.adapters.TaskAdapter;
import com.example.to_dolistapplication.database.TaskDatabase;
import com.example.to_dolistapplication.models.Task;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TaskDatabase taskDatabase;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private RecyclerView recyclerView;
    private Button btnAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskDatabase = TaskDatabase.getInstance(this);
        recyclerView = findViewById(R.id.recycler_view);
        btnAddTask = findViewById(R.id.btn_add_task);

        taskList = taskDatabase.taskDao().getAllTasks();
        taskAdapter = new TaskAdapter(this, taskList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        btnAddTask.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddEditTaskActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskList.clear();
        taskList.addAll(taskDatabase.taskDao().getAllTasks());
        taskAdapter.notifyDataSetChanged();
    }

}
