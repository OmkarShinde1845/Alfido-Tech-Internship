package com.example.to_dolistapplication.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.to_dolistapplication.models.Task;

@Database(entities = {Task.class}, version = 2, exportSchema = false)  // ✅ Increase version if you made changes
public abstract class TaskDatabase extends RoomDatabase {
    private static TaskDatabase instance;

    public abstract TaskDao taskDao();

    public static synchronized TaskDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            TaskDatabase.class, "task_database")
                    .fallbackToDestructiveMigration()  // ✅ Rebuilds DB on schema change
                    .allowMainThreadQueries()  // ❗ Use only for testing
                    .build();
        }
        return instance;
    }
}
