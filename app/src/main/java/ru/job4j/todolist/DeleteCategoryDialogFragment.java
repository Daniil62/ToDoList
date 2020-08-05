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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DeleteCategoryDialogFragment extends Fragment {
    public static DeleteCategoryDialogFragment of(int value) {
        DeleteCategoryDialogFragment dcdf = new DeleteCategoryDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DeleteCategoryDialogActivity.DELETE_CATEGORY_FOR, 0);
        dcdf.setArguments(bundle);
        return dcdf;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delete_category_dialog, container, false);
        TextView title = view.findViewById(R.id.delete_category_title_textView);
        Button cancel = view.findViewById(R.id.delete_category_cancel_button);
        Button delete = view.findViewById(R.id.delete_category_delete_button);
        if (hasMark()) {
            title.setText(R.string.delete_selected);
        }
        cancel.setOnClickListener(v -> {
            Objects.requireNonNull(getActivity()).onBackPressed();
        });
        delete.setOnClickListener(v -> {
            if (hasMark()) {
                deleteSelected();
            } else {
                PlanStoreStore.clear();
            }
            Objects.requireNonNull(getActivity()).onBackPressed();
        });
        return view;
    }
    private boolean hasMark() {
        boolean result = false;
        for (PlanStore ps : PlanStoreStore.getPlanStores()) {
            if (ps.isMark()) {
                result = true;
                break;
            }
        }
        return result;
    }
    private void deleteSelected() {
        List<PlanStore> temp = new ArrayList<>();
        for (int i = 0; i < PlanStoreStore.size(); i++) {
            PlanStore ps = PlanStoreStore.get(i);
            if (!ps.isMark()) {
                temp.add(ps);
            }
        }
        PlanStoreStore.clear();
        PlanStoreStore.addAll(temp);
    }
}
