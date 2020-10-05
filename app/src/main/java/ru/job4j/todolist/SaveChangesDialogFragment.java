package ru.job4j.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

public class SaveChangesDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity()).setMessage(R.string.save_changes)
                .setPositiveButton(R.string.save, (dialog, which) ->
                        callback.positiveSaveChangesClick(
                        SaveChangesDialogFragment.this))
                .setNegativeButton(R.string.cancel, (dialog, which) ->
                        callback.negativeSaveChangesClick(
                                SaveChangesDialogFragment.this)).create();
    }
    public interface SaveChangesListener {
        void positiveSaveChangesClick(SaveChangesDialogFragment object);
        void negativeSaveChangesClick(SaveChangesDialogFragment object);
    }
    SaveChangesListener callback;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (SaveChangesListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("must implement SaveChangesListener "
                    + context.toString());
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }
}
