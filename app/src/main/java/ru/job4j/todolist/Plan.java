package ru.job4j.todolist;

class Plan {
    private int id;
    private String text;
    private boolean mark;
    private long created;
    Plan(int position, String text, boolean mark, long created) {
        this.id = position;
        this.text = text;
        this.mark = mark;
        this.created = created;
    }
    void setText(String text) {
        this.text = text;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    String getText() {
        return text;
    }
    boolean isMark() {
        return mark;
    }
    void setMark(boolean mark) {
        this.mark = mark;
    }
    long getCreated() {
        return created;
    }
}
