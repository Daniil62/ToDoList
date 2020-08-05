package ru.job4j.todolist;

import android.support.v4.app.Fragment;

public class DeleteCategoryDialogActivator extends DeleteCategoryDialogActivity {
    @Override
    public Fragment loadFrg() {
        return DeleteCategoryDialogFragment.of(getIntent()
                .getIntExtra(DeleteCategoryDialogActivity.DELETE_CATEGORY_FOR, 0));
    }
}
