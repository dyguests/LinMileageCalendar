package com.fanhl.linmileagecalendar.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.fanhl.linmileagecalendar.R;

import java.util.Date;

/**
 * 里程日历对话框
 * <p>
 * Created by fanhl on 2017/3/1.
 */
public class MileageCalendarDialogFragment extends DialogFragment {
    public static final String TAG = MileageCalendarDialogFragment.class.getSimpleName();

    public static MileageCalendarDialogFragment newInstance(@NonNull Date selectedDate) {

        Bundle args = new Bundle();

        MileageCalendarDialogFragment fragment = new MileageCalendarDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_mileage_calendar, null);
        assignViews(view);
        initData();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(R.string.button_close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "点击关闭按钮");
                    }
                });

        return builder.create();
    }

    private void assignViews(View view) {

    }

    private void initData() {

    }
}
