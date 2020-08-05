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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DeletePlansDialogFragment extends Fragment {
    private int position;
    public static DeletePlansDialogFragment of(int value) {
        DeletePlansDialogFragment dpdf = new DeletePlansDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DeletePlansDialogActivity.DELETE_PLANS_FOR, 0);
        dpdf.setArguments(bundle);
        return dpdf;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delete_category_dialog, container, false);
        TextView header = view.findViewById(R.id.delete_category_title_textView);
        Button cancel = view.findViewById(R.id.delete_category_cancel_button);
        Button delete = view.findViewById(R.id.delete_category_delete_button);
        Intent intent = Objects.requireNonNull(getActivity()).getIntent();
        position = intent.getIntExtra("category_position", 0);
        boolean isMark = hasMark();
        if (isMark) {
            header.setText(R.string.delete_selected);
        }
        cancel.setOnClickListener(v -> getActivity().onBackPressed());
        delete.setOnClickListener(v -> {
            if(isMark) {
                deleteSelected();
            } else {
                PlanStoreStore.get(position).clear();
            }
            getActivity().onBackPressed();
        });
        return view;
    }
    private boolean hasMark() {
        boolean result = false;
        for (Plan plan : PlanStoreStore.get(position).getPlans()) {
            if (plan.isMark()) {
                result = true;
                break;
            }
        }
        return result;
    }
    private void deleteSelected() {
        List<Plan> temp = new ArrayList<>();
        for (int i = 0; i < PlanStoreStore.get(position).size(); i++) {
            Plan plan = PlanStoreStore.get(position).getPlans().get(i);
            if (!plan.isMark()) {
                temp.add(plan);
            }
        }
        PlanStoreStore.get(position).clear();
        PlanStoreStore.get(position).addAll(temp);
    }
}
