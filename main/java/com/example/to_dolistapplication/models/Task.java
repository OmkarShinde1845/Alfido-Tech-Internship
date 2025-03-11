
package com.example.to_dolistapplication.models;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

@Entity(tableName = "tasks")  // ✅ Ensure the table name is "tasks"
public class Task {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String description;
    public String dueDate;
    public boolean isCompleted;

    // Constructor for Room (used for updates)
    public Task(int id, String title, String description, String dueDate, boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
    }

    // Constructor for inserting new tasks (ignored by Room)
    @Ignore
    public Task(String title, String description, String dueDate, boolean isCompleted) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
    }
}
