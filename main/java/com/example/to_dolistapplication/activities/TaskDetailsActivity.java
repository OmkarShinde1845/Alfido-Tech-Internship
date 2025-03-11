package com.example.to_dolistapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.to_dolistapplication.R;
import com.example.to_dolistapplication.database.TaskDatabase;
import com.example.to_dolistapplication.models.Task;

public class TaskDetailsActivity extends AppCompatActivity {
    private TextView tvTaskTitle, tvTaskDescription, tvTaskDueDate;
    private Button btnUpdate, btnDelete;
    private TaskDatabase taskDatabase;
    private int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        tvTaskTitle = findViewById(R.id.tv_task_title);
        tvTaskDescription = findViewById(R.id.tv_task_description);
        tvTaskDueDate = findViewById(R.id.tv_task_due_date);
        btnUpdate = findViewById(R.id.btn_update_task);
        btnDelete = findViewById(R.id.btn_delete_task);
        taskDatabase = TaskDatabase.getInstance(this);

        Intent intent = getIntent();
        if (intent != null) {
            taskId = intent.getIntExtra("task_id", -1);
            tvTaskTitle.setText(intent.getStringExtra("task_title"));
            tvTaskDescription.setText(intent.getStringExtra("task_description"));
            tvTaskDueDate.setText(intent.getStringExtra("task_due_date"));
        }

        // Update Task
        btnUpdate.setOnClickListener(v -> {
            Intent updateIntent = new Intent(TaskDetailsActivity.this, AddEditTaskActivity.class);
            updateIntent.putExtra("task_id", taskId);
            updateIntent.putExtra("task_title", tvTaskTitle.getText().toString());
            updateIntent.putExtra("task_description", tvTaskDescription.getText().toString());
            updateIntent.putExtra("task_due_date", tvTaskDueDate.getText().toString());
            startActivity(updateIntent);
            finish();
        });

        // Delete Task
        btnDelete.setOnClickListener(v -> {
            Task task = new Task(taskId, tvTaskTitle.getText().toString(), tvTaskDescription.getText().toString(), tvTaskDueDate.getText().toString(), false);
            taskDatabase.taskDao().delete(task);
            Toast.makeText(TaskDetailsActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
