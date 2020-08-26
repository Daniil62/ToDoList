package ru.job4j.todolist;

import android.database.AbstractCursor;
import java.util.List;

public class StoreCursor extends AbstractCursor {
    private final List<PlanStore> store;
    StoreCursor(List<PlanStore> store) {
        this.store = store;
    }
    @Override
    public int getCount() {
        return PlanStoreStore.size();
    }
    @Override
    public String[] getColumnNames() {
        return new String[]{"POSITION", "TITLE"};
    }
    @Override
    public String getString(int column) {
        String value = null;
        if (store.size() != 0 && store.get(column).getPlans().size() != 0) {
            Plan plan = store.get(getPosition()).getPlans().get(column);
            if (column == 1 && plan != null) {
                value = plan.getText();
            }
        }
        return value;
    }
    @Override
    public short getShort(int column) {
        return 0;
    }
    @Override
    public int getInt(int column) {
        return 0;
    }
    @Override
    public long getLong(int column) {
        long result = 0;
        if (store.size() != 0 && store.get(column).getPlans().size() != 0) {
            Plan plan = store.get(getPosition()).getPlans().get(column);
            if (plan != null) {
                result = plan.getCreated();
            }
        }
        return result;
    }
    @Override
    public float getFloat(int column) {
        return 0;
    }
    @Override
    public double getDouble(int column) {
        return 0;
    }
    @Override
    public boolean isNull(int column) {
        boolean result = false;
        if (store.size() != 0 && store.get(column).getPlans().size() != 0) {
            Plan plan = store.get(getPosition()).getPlans().get(column);
            if (column == 1 && plan != null) {
                result = plan.isMark();
            }
        }
        return result;
    }
}
