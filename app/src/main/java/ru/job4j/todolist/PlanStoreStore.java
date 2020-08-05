package ru.job4j.todolist;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class PlanStoreStore {
    private static List<PlanStore> planStores = new ArrayList<>();
    static List<PlanStore> getPlanStores() {
        return planStores;
    }
    static void add(PlanStore ps) {
        planStores.add(ps);
    }
    static void addAll(List<PlanStore> list) {
        planStores.addAll(list);
    }
    static int size() {
        return planStores.size();
    }
    static PlanStore get(int i) {
        return planStores.get(i);
    }
    static void clear() {
        planStores.clear();
    }
}
