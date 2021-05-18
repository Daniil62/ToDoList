package ru.job4j.todolist.data_base;

public class DbSchema {
    public final static class CategoryTable {
        public final static String TAB_NAME = "category_table";
        public final static class Cols {
            public final static String TITLE = "title";
            public final static String MARK = "mark";
        }
    }
    public final static class PlanTable {
        public final static String TAB_NAME = "plan_table";
        public final static class Cols {
            public final static String TEXT = "plan_text";
            public final static String MARK = "plan_mark";
            public final static String CREATED = "created";
        }
    }
}
