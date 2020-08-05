package ru.job4j.todolist;

import java.util.ArrayList;
import java.util.List;

class PlanStore {
    private List<Plan> plans = new ArrayList<>();
    private String title;
    private boolean mark;
    PlanStore(String title, boolean mark) {
        this.title = title;
        this.mark = mark;
    }
    List<Plan> getPlans() {
        return plans;
    }
    String getTitle() {
        return title;
    }
    void setTitle(String title) {
        this.title = title;
    }
    boolean isMark() {
        return mark;
    }
    void setMark(boolean mark) {
        this.mark = mark;
    }
    void add(Plan plan) {
        plans.add(plan);
    }
    void addAll(List<Plan> list) {
        plans.addAll(list);
    }
    void clear() {
        plans.clear();
    }
    Plan get(int i) {
        return plans.get(i);
    }
    void set(int i, Plan plan) {
        plans.set(i, plan);
    }
    int size() {
        return plans.size();
    }
}
