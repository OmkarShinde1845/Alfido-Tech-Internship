package com.example.to_dolistapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.to_dolistapplication.R;
import com.example.to_dolistapplication.activities.AddEditTaskActivity;
import com.example.to_dolistapplication.activities.TaskDetailsActivity;
import com.example.to_dolistapplication.database.TaskDatabase;
import com.example.to_dolistapplication.models.Task;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private Context context;
    private List<Task> taskList;
    private TaskDatabase taskDatabase;

    public TaskAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
        this.taskDatabase = TaskDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.taskTitle.setText(task.title);
        holder.taskDueDate.setText(task.dueDate);

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddEditTaskActivity.class);
            intent.putExtra("task_id", task.id);
            intent.putExtra("task_title", task.title);
            intent.putExtra("task_description", task.description);
            intent.putExtra("task_due_date", task.dueDate);
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            taskDatabase.taskDao().delete(task);
            taskList.remove(position);
            notifyDataSetChanged();
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TaskDetailsActivity.class);
            intent.putExtra("task_title", task.title);
            intent.putExtra("task_description", task.description);
            intent.putExtra("task_due_date", task.dueDate);
            context.startActivity(intent);
        });

        // Open TaskDetailsActivity when task is clicked
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TaskDetailsActivity.class);
            intent.putExtra("task_id", task.id);
            intent.putExtra("task_title", task.title);
            intent.putExtra("task_description", task.description);
            intent.putExtra("task_due_date", task.dueDate);
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            taskDatabase.taskDao().delete(task);
            taskList.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle, taskDueDate;
        ImageButton btnEdit, btnDelete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.task_title);
            taskDueDate = itemView.findViewById(R.id.task_due_date);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
