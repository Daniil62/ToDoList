package ru.job4j.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

public class OrderDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity()).setMessage(getString(R.string.order_by))
                .setPositiveButton(getString(R.string.by_alphabet), (dialog, which) -> callback
                        .positiveOrderClick(OrderDialogFragment.this))
                .setNegativeButton(getString(R.string.by_date), (dialog, which) -> callback
                        .negativeOrderClick(OrderDialogFragment.this)).create();
    }
    public interface OrderDialogListener {
        void positiveOrderClick(OrderDialogFragment object);
        void negativeOrderClick(OrderDialogFragment object);
    }
    OrderDialogListener callback;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (OrderDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("must implement OrderDialogListener "
                    + context.toString());
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }
}
