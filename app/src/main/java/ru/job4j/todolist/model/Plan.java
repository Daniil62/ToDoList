package ru.job4j.todolist.model;

public class Plan {
    private int id;
    private String text;
    private boolean mark;
    private long created;
    public Plan(int position, String text, boolean mark, long created) {
        this.id = position;
        this.text = text;
        this.mark = mark;
        this.created = created;
    }
    public void setText(String text) {
        this.text = text;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public boolean isMark() {
        return mark;
    }
    public void setMark(boolean mark) {
        this.mark = mark;
    }
    public long getCreated() {
        return created;
    }
    public void setCreated(long created) {
        this.created = created;
    }
}
