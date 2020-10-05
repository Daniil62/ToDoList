package ru.job4j.todolist;

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

public class DeleteCategoryDialogFragment extends Fragment {
    private ToDoListBaseHelper helper;
    public static DeleteCategoryDialogFragment of(int value) {
        DeleteCategoryDialogFragment fragment = new DeleteCategoryDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DeleteCategoryDialogActivity.DELETE_CATEGORY_FOR, value);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delete_category_dialog, container, false);
        TextView title = view.findViewById(R.id.delete_category_title_textView);
        Button cancel = view.findViewById(R.id.delete_category_cancel_button);
        Button delete = view.findViewById(R.id.delete_category_delete_button);
        helper = new ToDoListBaseHelper(getContext());
        boolean mark = helper.hasMarkedCategory();
        if (mark) {
            title.setText(R.string.delete_selected);
        }
        cancel.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
        delete.setOnClickListener(v -> {
            if (mark) {
                helper.deleteSelectedCategory();
            } else {
                helper.totalDelete();
            }
            Objects.requireNonNull(getActivity()).onBackPressed();
        });
        return view;
    }
}
