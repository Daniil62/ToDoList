package ru.job4j.todolist.dao;

import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;

import java.util.List;
import ru.job4j.todolist.model.Category;
import ru.job4j.todolist.data_base.DbSchema;

@Dao
public interface CategoryDao {
    @Insert
    void addCategory(Category category);

    @RawQuery
    List<Category> getCategories(SupportSQLiteQuery query);

 //   @Query("select * from " + DbSchema.CategoryTable.TAB_NAME +
 //           " where title like '%' || :sequence || '%' order by :ord") - Так сортировка не работает, лишь поиск по секвенции.
 //   List<Category> getCategories(String sequence, String ord);

    @Query("update " + DbSchema.CategoryTable.TAB_NAME + " set "
            + DbSchema.CategoryTable.Cols.TITLE + " = :title where id = :id")
    void editCategory(int id, String title);

    @Query("update " + DbSchema.CategoryTable.TAB_NAME + " set "
            + DbSchema.CategoryTable.Cols.MARK + " = :mark where id = :id")
    void markCategory(int id, int mark);

    @Query("select mark from " + DbSchema.CategoryTable.TAB_NAME + " where mark > 0")
    int hasMarkedCategory();

    @Query("select title from " + DbSchema.CategoryTable.TAB_NAME + " where id = :id")
    String getCategoryTitle(int id);

    @Query("delete from " + DbSchema.CategoryTable.TAB_NAME + " where mark > 0")
    void deleteSelectedCategory();

    @Query("delete from " + DbSchema.CategoryTable.TAB_NAME)
    void totalDelete();
}
