package ru.job4j.todolist;

import android.support.v4.app.Fragment;

public class AddCategoryDialogActivator extends AddCategoryDialogActivity {
    @Override
    public Fragment loadFrg() {
        return AddCategoryDialogFragment.of(getIntent()
                .getIntExtra(AddCategoryDialogActivity.ADD_CATEGORY_FOR, 0));
    }
}
