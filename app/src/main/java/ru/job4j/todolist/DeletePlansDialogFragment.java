package ru.job4j.todolist;

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

import ru.job4j.todolist.store.ToDoListBaseHelper;

public class DeletePlansDialogFragment extends Fragment {
    private ToDoListBaseHelper helper;
    private int position;
    public static DeletePlansDialogFragment of(int value) {
        DeletePlansDialogFragment fragment = new DeletePlansDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DeletePlansDialogActivity.DELETE_PLANS_FOR, value);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delete_category_dialog, container, false);
        helper = new ToDoListBaseHelper(getContext());
        TextView header = view.findViewById(R.id.delete_category_title_textView);
        Button cancel = view.findViewById(R.id.delete_category_cancel_button);
        Button delete = view.findViewById(R.id.delete_category_delete_button);
        Intent intent = Objects.requireNonNull(getActivity()).getIntent();
        position = intent.getIntExtra("category_position", 0);
        boolean isMark = helper.hasMarkedPlan(position);
        if (isMark) {
            header.setText(R.string.delete_selected);
        }
        cancel.setOnClickListener(v -> getActivity().onBackPressed());
        delete.setOnClickListener(v -> {
            if(isMark) {
                helper.deleteSelectedPlans(position);
            } else {
                helper.clearCategory(position);
            }
            getActivity().onBackPressed();
        });
        return view;
    }
}
