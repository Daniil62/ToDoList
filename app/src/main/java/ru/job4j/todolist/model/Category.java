package ru.job4j.todolist.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import ru.job4j.todolist.data_base.DbSchema;

@Entity(tableName = DbSchema.CategoryTable.TAB_NAME)
public class Category {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private int mark;
    @Ignore
    public Category(String title, int mark) {
        this.title = title;
        this.mark = mark;
    }
    public Category(int id, String title, int mark) {
        this.id = id;
        this.title = title;
        this.mark = mark;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getMark() {
        return mark;
    }
    public void setMark(int mark) {
        this.mark = mark;
    }
}
