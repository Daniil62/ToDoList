package ru.job4j.todolist.store;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

import ru.job4j.todolist.model.Plan;

public class ToDoListBaseHelper extends SQLiteOpenHelper {
    private static final String DB = "to_do_list.db";
    private static final int VERSION = 1;
    public ToDoListBaseHelper(Context context) {
        super (context, DB, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "
                + ToDoListDBschema.CategoryTable.TAB_NAME + " ("
                + "_id integer primary key autoincrement, "
                + ToDoListDBschema.CategoryTable.Cols.TITLE + " text, "
                + ToDoListDBschema.CategoryTable.Cols.MARK + " integer "
                + ")");
        db.execSQL("create table "
                + ToDoListDBschema.PlanTable.TAB_NAME + " ("
                + "_id integer primary key autoincrement, "
                + ToDoListDBschema.PlanTable.Cols.POSITION + " integer, "
                + ToDoListDBschema.PlanTable.Cols.TEXT + " text, "
                + ToDoListDBschema.PlanTable.Cols.MARK + " integer, "
                + ToDoListDBschema.PlanTable.Cols.CREATED + " integer, "
                + ToDoListDBschema.PlanTable.Cols.FOREIGN_KEY + " integer, "
                + " foreign key " + "(" + ToDoListDBschema.PlanTable.Cols.FOREIGN_KEY + ")"
                + " references " + ToDoListDBschema.CategoryTable.TAB_NAME + "_id"
                + " on delete cascade"
                +")");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public void addCategory(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ToDoListDBschema.CategoryTable.Cols.TITLE, title);
        values.put(ToDoListDBschema.CategoryTable.Cols.MARK, 0);
        db.insert(ToDoListDBschema.CategoryTable.TAB_NAME, null, values);
    }
    public List<PlanStore> getCategories(String sequence, String order) {
        List<PlanStore> categories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(ToDoListDBschema.CategoryTable.TAB_NAME, null,
                ToDoListDBschema.CategoryTable.Cols.TITLE + " like " + "'%" + sequence
                        + "%'", null, null, null, order);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                categories.add(new PlanStore(cursor.getInt(
                        cursor.getColumnIndex("_id")),
                        cursor.getString(
                                cursor.getColumnIndex(ToDoListDBschema.CategoryTable.Cols.TITLE)),
                        cursor.getInt(
                                cursor.getColumnIndex(
                                        ToDoListDBschema.CategoryTable.Cols.MARK)) > 0));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return categories;
    }
    public void editCategory(int id, String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ToDoListDBschema.CategoryTable.Cols.TITLE, title);
        db.update(ToDoListDBschema.CategoryTable.TAB_NAME, values,
                "_id = " + id, new String[]{});
    }
    public void markCategory(int id, boolean mark) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ToDoListDBschema.CategoryTable.Cols.MARK, mark);
        db.update(ToDoListDBschema.CategoryTable.TAB_NAME, values,
                "_id = " + id, new String[]{});
    }
    public boolean hasMarkedCategory() {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(ToDoListDBschema.CategoryTable.TAB_NAME, null,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getInt(cursor.getColumnIndex(
                    ToDoListDBschema.CategoryTable.Cols.MARK)) != 0) {
                result = true;
                break;
            }
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }
    public String getCategoryTitle(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String result = "";
        Cursor cursor = db.query(ToDoListDBschema.CategoryTable.TAB_NAME, null,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getInt(cursor.getColumnIndex("_id")) == id) {
                result = cursor.getString(
                        cursor.getColumnIndex(ToDoListDBschema.CategoryTable.Cols.TITLE));
            }
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }
    public void deleteSelectedCategory() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(ToDoListDBschema.CategoryTable.TAB_NAME, null,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getInt(cursor.getColumnIndex(
                    ToDoListDBschema.CategoryTable.Cols.MARK)) != 0) {
                db.delete(ToDoListDBschema.CategoryTable.TAB_NAME, "_id = "
                        + cursor.getInt(cursor.getColumnIndex("_id")), new String[]{});
            }
            cursor.moveToNext();
        }
        cursor.close();
    }
    public void totalDelete() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ToDoListDBschema.CategoryTable.TAB_NAME, null, null);
    }
    public int size() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "select _id from " + ToDoListDBschema.PlanTable.TAB_NAME, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    public void setPlan(int foreignKey, Plan plan) {
        foreignKey += 1;
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ToDoListDBschema.PlanTable.Cols.POSITION, plan.getId());
        values.put(ToDoListDBschema.PlanTable.Cols.FOREIGN_KEY, foreignKey);
        values.put(ToDoListDBschema.PlanTable.Cols.TEXT, plan.getText());
        values.put(ToDoListDBschema.PlanTable.Cols.MARK, plan.isMark());
        values.put(ToDoListDBschema.PlanTable.Cols.CREATED, plan.getCreated());
        db.insert(ToDoListDBschema.PlanTable.TAB_NAME, null, values);
    }
    public void editPlan(int foreignKey, int id, String text) {
        foreignKey += 1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ToDoListDBschema.PlanTable.Cols.TEXT, text);
        db.update(ToDoListDBschema.PlanTable.TAB_NAME, values,
                ToDoListDBschema.PlanTable.Cols.FOREIGN_KEY
                        + " = " + foreignKey + " and _id = " + id, new String[]{});
    }
    public void markPlan(int id, boolean mark) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ToDoListDBschema.PlanTable.Cols.MARK, mark);
        db.update(ToDoListDBschema.PlanTable.TAB_NAME, values,
                "_id = " + id, new String[]{});
    }
    public boolean hasMarkedPlan(int key) {
        key += 1;
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(ToDoListDBschema.PlanTable.TAB_NAME, null,
                ToDoListDBschema.PlanTable.Cols.FOREIGN_KEY + " = ?",
                new String[]{String.valueOf(key)}, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getInt(cursor.getColumnIndex(ToDoListDBschema.PlanTable.Cols.MARK)) != 0) {
                result = true;
                break;
            }
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }
    public List<Plan> getPlansById(int key, String sequence, String order) {
        key += 1;
        SQLiteDatabase db = this.getReadableDatabase();
        List<Plan> plans = new ArrayList<>();
        Cursor cursor = db.query(ToDoListDBschema.PlanTable.TAB_NAME, null,
                ToDoListDBschema.PlanTable.Cols.FOREIGN_KEY + " = ?" + " and "
                + ToDoListDBschema.PlanTable.Cols.TEXT + " like " + "'%" + sequence + "%'",
                new String[]{String.valueOf(key)},
                null, null, order);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            plans.add(new Plan(cursor.getInt(
                    cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex(ToDoListDBschema.PlanTable.Cols.TEXT)),
                    cursor.getInt(
                            cursor.getColumnIndex(ToDoListDBschema.PlanTable.Cols.MARK)) > 0,
                    cursor.getLong(
                            cursor.getColumnIndex(ToDoListDBschema.PlanTable.Cols.CREATED))));

            cursor.moveToNext();
        }
        cursor.close();
        return plans;
    }
    public Plan getPlan(int foreignKey, int id) {
        foreignKey += 1;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(ToDoListDBschema.PlanTable.TAB_NAME, null,
                ToDoListDBschema.PlanTable.Cols.FOREIGN_KEY + " = ?",
                new String[]{String.valueOf(foreignKey)}, null, null, null);
        Plan result = null;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String text = cursor.getString(
                    cursor.getColumnIndex(ToDoListDBschema.PlanTable.Cols.TEXT));
            boolean mark = cursor.getInt(
                    cursor.getColumnIndex(ToDoListDBschema.PlanTable.Cols.MARK)) > 0;
            long created = cursor.getLong(
                    cursor.getColumnIndex(ToDoListDBschema.PlanTable.Cols.CREATED));
            int position = cursor.getInt(cursor.getColumnIndex("_id"));
            if (position == id) {
                result = new Plan(position, text, mark, created);
                break;
            }
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }
    public void deleteSelectedPlans(int key) {
        key += 1;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(ToDoListDBschema.PlanTable.TAB_NAME, null,
                ToDoListDBschema.PlanTable.Cols.FOREIGN_KEY + " = ?",
                new String[]{String.valueOf(key)}, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getInt(cursor.getColumnIndex(ToDoListDBschema.PlanTable.Cols.MARK)) != 0) {
                db.delete(ToDoListDBschema.PlanTable.TAB_NAME, "_id = "
                        + cursor.getInt(cursor.getColumnIndex("_id")), new String[]{});
            }
            cursor.moveToNext();
        }
        cursor.close();
    }
    public void deleteThisPlan(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(ToDoListDBschema.PlanTable.TAB_NAME, null,
                 "_id = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getInt(cursor.getColumnIndex("_id")) == id) {
                db.delete(ToDoListDBschema.PlanTable.TAB_NAME, "_id = "
                        + cursor.getInt(cursor.getColumnIndex("_id")), new String[]{});
                break;
            }
            cursor.moveToNext();
        }
        cursor.close();
    }
    public void clearCategory(int key) {
        key += 1;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(ToDoListDBschema.PlanTable.TAB_NAME, null,
                ToDoListDBschema.PlanTable.Cols.FOREIGN_KEY + " = ?",
                new String[]{String.valueOf(key)}, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            db.delete(ToDoListDBschema.PlanTable.TAB_NAME, "_id = "
                    + cursor.getInt(cursor.getColumnIndex("_id")), new String[]{});
            cursor.moveToNext();
        }
        cursor.close();
    }
}
