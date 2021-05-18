package ru.job4j.todolist.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import ru.job4j.todolist.R;

public abstract class DialogActivity extends FragmentActivity {
    public static final String DIALOG_FOR = "dialog_for";
    public abstract Fragment loadFrg();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host_fragment);
        setFinishOnTouchOutside(false);
        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentById(R.id.content) == null) {
            fm.beginTransaction()
                    .add(R.id.content, loadFrg())
                    .commit();
        }
    }
}
