package ru.job4j.todolist.dao;

import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import java.util.List;
import ru.job4j.todolist.model.Plan;
import ru.job4j.todolist.data_base.DbSchema;

@Dao
public interface PlanDao {
    @Insert
    void addPlan(Plan plan);

    @RawQuery
    List<Plan> getPlans(SupportSQLiteQuery query);

 //   @Query("select * from " + DbSchema.PlanTable.TAB_NAME + " where foreignKey = "   - Так работает лишь поиск по секвенции,
 //           + ":key and plan_text like " + "'%' || :sequence || '%' order by :ord")      а сортировка не работает.
 //   List<Plan> getPlans(int key, String sequence, String ord);

    @Query("select * from " + DbSchema.PlanTable.TAB_NAME +
            " where foreignKey = :key and id = :id")
    Plan getPlan(int key, int id);

    @Query("update " + DbSchema.PlanTable.TAB_NAME + " set "
            + DbSchema.PlanTable.Cols.TEXT + " = :text where foreignKey = :key and id = :id")
    void editPlan(int key, int id, String text);

    @Query("update " + DbSchema.PlanTable.TAB_NAME + " set "
            + DbSchema.PlanTable.Cols.MARK + " = :mark where id = :id")
    void markPlan(int id, int mark);

    @Query("select plan_mark from " + DbSchema.PlanTable.TAB_NAME
            + " where foreignKey = :key and plan_mark > 0")
    int hasMarkedPlan(int key);

    @Query("delete from " + DbSchema.PlanTable.TAB_NAME
            + " where foreignKey = :key and plan_mark > 0")
    void deleteSelectedPlans(int key);

    @Query("delete from " + DbSchema.PlanTable.TAB_NAME + " where id = :id")
    void deleteThisPlan(int id);

    @Query("delete from " + DbSchema.PlanTable.TAB_NAME + " where foreignKey = :key")
    void clearCategory(int key);
}
