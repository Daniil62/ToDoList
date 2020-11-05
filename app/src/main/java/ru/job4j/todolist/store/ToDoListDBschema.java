package ru.job4j.todolist.store;

public class ToDoListDBschema {
    public final static class CategoryTable {
        final static String TAB_NAME = "category_table";
        public final static class Cols {
            public final static String TITLE = "title";
            final static String MARK = "mark";
        }
    }
    public final static class PlanTable {
        final static String TAB_NAME = "plan_table";
        public final static class Cols {
            final static String POSITION = "position";
            public final static String TEXT = "plan_text";
            final static String MARK = "plan_mark";
            public final static String CREATED = "plan_created";
            final static String FOREIGN_KEY = "plan_key";
        }
    }
}
