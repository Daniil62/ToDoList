package ru.job4j.todolist;

class PlanStore {
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
    String getTitle() {
        return title;
    }
    boolean isMark() {
        return mark;
    }
    void setMark(boolean mark) {
        this.mark = mark;
    }
}
