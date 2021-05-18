package ru.job4j.todolist.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.Objects;

import ru.job4j.todolist.R;
import ru.job4j.todolist.activity.DialogActivity;
import ru.job4j.todolist.data_base.DbApp;
import ru.job4j.todolist.data_base.ToDoDataBase;

public class DeleteCategoryDialogFragment extends Fragment {
    private ToDoDataBase dataBase;
    public static DeleteCategoryDialogFragment of(int value) {
        DeleteCategoryDialogFragment fragment = new DeleteCategoryDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DialogActivity.DIALOG_FOR, value);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delete_category_dialog, container, false);
        setDataBase();
        TextView title = view.findViewById(R.id.delete_category_title_textView);
        Button cancel = view.findViewById(R.id.delete_category_cancel_button);
        Button delete = view.findViewById(R.id.delete_category_delete_button);
        int mark = dataBase.categoryDao().hasMarkedCategory();
        if (mark > 0) {
            title.setText(R.string.delete_selected);
        }
        cancel.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
        delete.setOnClickListener(v -> onDeleteClick(mark));
        return view;
    }
    private void onDeleteClick(int mark) {
        if (mark > 0) {
            dataBase.categoryDao().deleteSelectedCategory();
        } else {
            dataBase.categoryDao().totalDelete();
        }
        Objects.requireNonNull(getActivity()).onBackPressed();
    }
    private void setDataBase() {
        dataBase = DbApp.getDatabase();
    }
}
