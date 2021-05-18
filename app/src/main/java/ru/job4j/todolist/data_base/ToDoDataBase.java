package ru.job4j.todolist.data_base;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import ru.job4j.todolist.dao.CategoryDao;
import ru.job4j.todolist.dao.PlanDao;
import ru.job4j.todolist.model.Category;
import ru.job4j.todolist.model.Plan;

@Database(entities = {Category.class, Plan.class}, version = 1, exportSchema = false)
public abstract class ToDoDataBase extends RoomDatabase {
    public abstract CategoryDao categoryDao();
    public abstract PlanDao planDao();
}
