package com.example.to_dolistapplication.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.to_dolistapplication.R;
import com.example.to_dolistapplication.database.TaskDatabase;
import com.example.to_dolistapplication.models.Task;
import java.util.Calendar;

public class AddEditTaskActivity extends AppCompatActivity {
    private EditText etTaskTitle, etTaskDescription, etTaskDueDate;
    private Button btnSaveTask;
    private TaskDatabase taskDatabase;
    private Calendar calendar;
    private int taskId = -1;  // Store task ID for update

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        etTaskTitle = findViewById(R.id.edit_task_title);
        etTaskDescription = findViewById(R.id.edit_task_description);
        etTaskDueDate = findViewById(R.id.edit_task_due_date);
        btnSaveTask = findViewById(R.id.btn_save_task);
        taskDatabase = TaskDatabase.getInstance(this);
        calendar = Calendar.getInstance();

        // Check if updating an existing task
        Intent intent = getIntent();
        if (intent.hasExtra("task_id")) {
            taskId = intent.getIntExtra("task_id", -1);
            etTaskTitle.setText(intent.getStringExtra("task_title"));
            etTaskDescription.setText(intent.getStringExtra("task_description"));
            etTaskDueDate.setText(intent.getStringExtra("task_due_date"));
        }

        etTaskDueDate.setOnClickListener(v -> showDatePicker());
        btnSaveTask.setOnClickListener(v -> saveTask());
    }

    private void showDatePicker() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            String formattedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
            etTaskDueDate.setText(formattedDate);
        }, year, month, day);

        datePickerDialog.show();
    }

    private void saveTask() {
        String taskTitle = etTaskTitle.getText().toString().trim();
        String taskDescription = etTaskDescription.getText().toString().trim();
        String taskDueDate = etTaskDueDate.getText().toString().trim();

        if (taskTitle.isEmpty()) {
            Toast.makeText(this, "Please enter a task title", Toast.LENGTH_SHORT).show();
            return;
        }

        if (taskId == -1) {
            // Insert new task
            Task newTask = new Task(taskTitle, taskDescription, taskDueDate, false);
            taskDatabase.taskDao().insert(newTask);
            Toast.makeText(this, "Task added successfully!", Toast.LENGTH_SHORT).show();
        } else {
            // Update existing task
            Task updatedTask = new Task(taskId, taskTitle, taskDescription, taskDueDate, false);
            taskDatabase.taskDao().update(updatedTask);
            Toast.makeText(this, "Task updated successfully!", Toast.LENGTH_SHORT).show();
        }

        finish(); // Close the activity
    }
}
