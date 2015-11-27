package com.example.money.widget;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;

/**
 * Created by su on 2014/7/28.
 */
public class SimpleBlockedDialogFragment extends DialogFragment {

    private CharSequence mTitle;
    private CharSequence mMessage;

    public static SimpleBlockedDialogFragment newInstance() {
        SimpleBlockedDialogFragment f = new SimpleBlockedDialogFragment();
        Bundle args = new Bundle();
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final ProgressDialog dialog = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_LIGHT);
        if (!(mTitle == null || "".equals(mTitle))) {
            dialog.setTitle(mTitle);
        }
        dialog.setMessage(mMessage);
        dialog.setIndeterminate(true);
        setCancelable(false);

        return dialog;
    }

    public void updateTitle(CharSequence title) {
        mTitle = title;
    }

    public void updateMessage(CharSequence message) {
        mMessage = message;
    }
}
