package ru.job4j.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

public class DeleteThisPlanDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity()).setMessage(R.string.delete_this_plan)
                .setPositiveButton(R.string.delete, (dialog, which) -> callback
                        .positiveDeleteThisPlanClick(DeleteThisPlanDialog.this))
                .setNegativeButton(R.string.cancel, (dialog, which) -> callback
                        .negativeDeleteThisPlanClick(DeleteThisPlanDialog.this)).create();
    }
    public interface DeleteThisPlanDialogListener {
        void positiveDeleteThisPlanClick(DeleteThisPlanDialog object);
        void negativeDeleteThisPlanClick(DeleteThisPlanDialog object);
    }
    DeleteThisPlanDialogListener callback;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (DeleteThisPlanDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("must implement DeleteThisPlanDialogListener "
                    + context.toString());
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }
}
