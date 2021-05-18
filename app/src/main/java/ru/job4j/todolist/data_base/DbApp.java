package ru.job4j.todolist.data_base;

import android.app.Application;
import android.arch.persistence.room.Room;

public class DbApp extends Application {
    public static DbApp instance;
    private static ToDoDataBase dataBase;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        dataBase = Room.databaseBuilder(this, ToDoDataBase.class,
                "forecast_database").allowMainThreadQueries().build();
    }
    public static ToDoDataBase getDatabase() {
        return dataBase;
    }
}
