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
import android.widget.EditText;
import android.widget.TextView;
import java.util.Objects;

public class AddCategoryDialogFragment extends Fragment {
    public static AddCategoryDialogFragment of(int value) {
        AddCategoryDialogFragment acdf = new AddCategoryDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(AddCategoryDialogActivity.ADD_CATEGORY_FOR, 0);
        acdf.setArguments(bundle);
        return acdf;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_category_dialog, container, false);
        TextView header = view.findViewById(R.id.add_category_dialog_header);
        EditText et = view.findViewById(R.id.add_category_editText);
        Button cancel = view.findViewById(R.id.add_category_cancel_button);
        Button complete = view.findViewById(R.id.add_category_complete_button);
        Intent intent = Objects.requireNonNull(getActivity()).getIntent();
        boolean variant = intent.getBooleanExtra("variant_for_add", false);
        int editablePosition = intent.getIntExtra("editable_position", -1);
        if (!variant && editablePosition != -1) {
            header.setText(getString(R.string.edit_category));
            et.setText(PlanStoreStore.get(editablePosition).getTitle());
        }
        cancel.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
        complete.setOnClickListener(v -> {
            String text = et.getText().toString();
            if (variant) {
                if (!text.equals("")) {
                    PlanStore ps = new PlanStore(text, false);
                    PlanStoreStore.add(ps);
                }
            }  else {
                PlanStoreStore.get(intent.getIntExtra("editable_position",
                        0)).setTitle(text);
            }
            Objects.requireNonNull(getActivity()).onBackPressed();
        });
        return view;
    }
}
