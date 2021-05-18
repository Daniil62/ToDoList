package ru.job4j.todolist.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import ru.job4j.todolist.data_base.DbSchema;

@Entity(tableName = DbSchema.PlanTable.TAB_NAME)
public class Plan {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ForeignKey(entity = Category.class, parentColumns = "id",
            childColumns = "foreignKey",
            onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE)
    private int foreignKey;
    private String plan_text;
    private int plan_mark;
    private long created;

    public Plan(){}

    public Plan(int key, String plan_text, int plan_mark, long created) {
        this.foreignKey = key;
        this.plan_text = plan_text;
        this.plan_mark = plan_mark;
        this.created = created;
    }
    public void setPlan_text(String plan_text) {
        this.plan_text = plan_text;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getForeignKey() {
        return foreignKey;
    }
    public void setForeignKey(int foreignKey) {
        this.foreignKey = foreignKey;
    }
    public String getPlan_text() {
        return plan_text;
    }
    public int isPlan_mark() {
        return plan_mark;
    }
    public int getPlan_mark() {
        return plan_mark;
    }
    public void setPlan_mark(int plan_mark) {
        this.plan_mark = plan_mark;
    }
    public long getCreated() {
        return created;
    }
    public void setCreated(long created) {
        this.created = created;
    }
}
