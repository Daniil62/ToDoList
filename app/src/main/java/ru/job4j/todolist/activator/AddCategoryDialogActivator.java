package ru.job4j.todolist.activator;

import android.support.v4.app.Fragment;

import ru.job4j.todolist.activity.DialogActivity;
import ru.job4j.todolist.dialog.AddCategoryDialogFragment;

public class AddCategoryDialogActivator extends DialogActivity {
    @Override
    public Fragment loadFrg() {
        return AddCategoryDialogFragment.of(getIntent()
                .getIntExtra(DialogActivity.DIALOG_FOR, 0));
    }
}
