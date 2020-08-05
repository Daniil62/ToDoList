package ru.job4j.todolist;

import android.support.v4.app.Fragment;

public class DeletePlansDialogActivator extends DeletePlansDialogActivity {
    @Override
    public Fragment loadFrg() {
        return DeletePlansDialogFragment.of(getIntent()
                .getIntExtra(DeletePlansDialogActivity.DELETE_PLANS_FOR, 0));
    }
}
