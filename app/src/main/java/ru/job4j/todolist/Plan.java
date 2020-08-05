package ru.job4j.todolist;

class Plan {
    private String text;
    private boolean mark;
    private long created;
    Plan(String text, boolean mark, long created) {
        this.text = text;
        this.mark = mark;
        this.created = created;
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
