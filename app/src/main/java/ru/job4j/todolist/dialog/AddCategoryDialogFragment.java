package ru.job4j.todolist.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Objects;

import ru.job4j.todolist.R;
import ru.job4j.todolist.activity.DialogActivity;
import ru.job4j.todolist.data_base.DbApp;
import ru.job4j.todolist.data_base.ToDoDataBase;
import ru.job4j.todolist.model.Category;

public class AddCategoryDialogFragment extends Fragment {
    private ToDoDataBase dataBase;
    private InputMethodManager imm;
    private TextView header;
    private EditText et;
    private Button cancel;
    private Button complete;
    public static AddCategoryDialogFragment of(int value) {
        AddCategoryDialogFragment fragment = new AddCategoryDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DialogActivity.DIALOG_FOR, value);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_category_dialog, container, false);
        setDataBase();
        imm = (InputMethodManager) Objects.requireNonNull(
                getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        findViews(view);
        Intent intent = Objects.requireNonNull(getActivity()).getIntent();
        boolean variant = intent.getBooleanExtra("variant_for_add", false);
        int editablePosition = intent.getIntExtra("editable_position", 0);
        if (!variant && editablePosition != -1) {
            header.setText(getString(R.string.edit_category));
            et.setText(dataBase.categoryDao().getCategoryTitle(editablePosition));
        }
        et.setSelection(et.getText().length());
        cancel.setOnClickListener(v -> onCancelClick());
        complete.setOnClickListener(v -> onCompleteClick(variant, editablePosition));
        return view;
    }
    private void findViews(View view) {
        header = view.findViewById(R.id.add_category_dialog_header);
        et = view.findViewById(R.id.add_category_editText);
        cancel = view.findViewById(R.id.add_category_cancel_button);
        complete = view.findViewById(R.id.add_category_complete_button);
    }
    private void onCompleteClick(boolean variant, int editablePosition) {
        imm.hideSoftInputFromWindow(Objects.requireNonNull(getActivity())
                .getWindow().getDecorView().getWindowToken(), 0);
        String text = et.getText().toString();
        if (!text.equals("")) {
            if (variant) {
                dataBase.categoryDao().addCategory(new Category(text, 0));
            } else {
                dataBase.categoryDao().editCategory(editablePosition, text);
            }
            Objects.requireNonNull(getActivity()).onBackPressed();
        }
    }
    private void onCancelClick() {
        Objects.requireNonNull(getActivity()).onBackPressed();
        imm.hideSoftInputFromWindow(getActivity()
                .getWindow().getDecorView().getWindowToken(), 0);
    }
    private void setDataBase() {
        dataBase = DbApp.getDatabase();
    }
}
