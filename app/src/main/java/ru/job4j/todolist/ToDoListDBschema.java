package ru.job4j.todolist;

class ToDoListDBschema {
    final static class CategoryTable {
        final static String TAB_NAME = "category_table";
        final static class Cols {
            final static String TITLE = "title";
            final static String MARK = "mark";
        }
    }
    final static class PlanTable {
        final static String TAB_NAME = "plan_table";
        final static class Cols {
            final static String POSITION = "position";
            final static String TEXT = "plan_text";
            final static String MARK = "plan_mark";
            final static String CREATED = "plan_created";
            final static String FOREIGN_KEY = "plan_key";
        }
    }
}
