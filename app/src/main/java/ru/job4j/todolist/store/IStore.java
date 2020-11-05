package ru.job4j.todolist.store;

import ru.job4j.todolist.model.Plan;

public interface IStore {
    void add(Plan plan);
    void edit(int index, Plan plan);
    void delete(int index);
    int size();
    Plan get(int index);

}
