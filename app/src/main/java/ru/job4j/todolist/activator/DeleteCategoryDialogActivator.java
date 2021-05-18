package ru.job4j.todolist.activator;

import android.support.v4.app.Fragment;

import ru.job4j.todolist.activity.DialogActivity;
import ru.job4j.todolist.dialog.DeleteCategoryDialogFragment;

public class DeleteCategoryDialogActivator extends DialogActivity {
    @Override
    public Fragment loadFrg() {
        return DeleteCategoryDialogFragment.of(getIntent()
                .getIntExtra(DialogActivity.DIALOG_FOR, 0));
    }
}
