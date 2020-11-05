package ru.job4j.todolist.store;

public class PlanStore {
    private int id;
    private String title;
    private boolean mark;
    PlanStore(int id, String title, boolean mark) {
        this.id = id;
        this.title = title;
        this.mark = mark;
    }
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public boolean isMark() {
        return mark;
    }
    public void setMark(boolean mark) {
        this.mark = mark;
    }
}
