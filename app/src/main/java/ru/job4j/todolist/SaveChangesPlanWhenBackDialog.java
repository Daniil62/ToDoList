package ru.job4j.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

public class SaveChangesPlanWhenBackDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity()).setMessage(R.string.save_changes)
                .setPositiveButton(R.string.save, (dialog, which) ->
                        callback.positiveSaveChangesWhenBackClick(
                        SaveChangesPlanWhenBackDialog.this))
                .setNegativeButton(R.string.cancel, (dialog, which) ->
                        callback.negativeSaveChangesWhenBackClick(
                                SaveChangesPlanWhenBackDialog.this)).create();
    }
    public interface SaveChangesPlanWhenBackListener {
        void positiveSaveChangesWhenBackClick(SaveChangesPlanWhenBackDialog object);
        void negativeSaveChangesWhenBackClick(SaveChangesPlanWhenBackDialog object);
    }
    SaveChangesPlanWhenBackListener callback;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (SaveChangesPlanWhenBackListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("must implement SaveChangesPlanWhenBackListener "
                    + context.toString());
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }
}
