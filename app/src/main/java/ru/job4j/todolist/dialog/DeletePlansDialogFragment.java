package ru.job4j.todolist.dialog;

import android.content.Intent;
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

public class DeletePlansDialogFragment extends Fragment {
    private ToDoDataBase dataBase;
    private int position;
    public static DeletePlansDialogFragment of(int value) {
        DeletePlansDialogFragment fragment = new DeletePlansDialogFragment();
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
        TextView header = view.findViewById(R.id.delete_category_title_textView);
        Button cancel = view.findViewById(R.id.delete_category_cancel_button);
        Button delete = view.findViewById(R.id.delete_category_delete_button);
        Intent intent = Objects.requireNonNull(getActivity()).getIntent();
        position = intent.getIntExtra("category_position", 0);
        int mark = dataBase.planDao().hasMarkedPlan(position);
        if (mark > 0) {
            header.setText(R.string.delete_selected);
        }
        cancel.setOnClickListener(v -> getActivity().onBackPressed());
        delete.setOnClickListener(v -> onDeleteClick(mark));
        return view;
    }
    private void onDeleteClick(int mark) {
        if(mark > 0) {
            dataBase.planDao().deleteSelectedPlans(position);
        } else {
            dataBase.planDao().clearCategory(position);
        }
        Objects.requireNonNull(getActivity()).onBackPressed();
    }
    private void setDataBase() {
        dataBase = DbApp.getDatabase();
    }
}
