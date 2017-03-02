package com.fanhl.linmileagecalendar.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fanhl.linmileagecalendar.R;
import com.fanhl.linmileagecalendar.model.MonthData;
import com.fanhl.linmileagecalendar.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 里程日历对话框
 * <p>
 * Created by fanhl on 2017/3/1.
 */
public class MileageCalendarDialogFragment extends DialogFragment {
    public static final String TAG = MileageCalendarDialogFragment.class.getSimpleName();
    /** 每次加载数据时要刷新的条数 */
    public static final int DEFAULT_LOAD_COUNT = 5;

    private android.widget.TextView dateTv;
    private RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private MonthAdapter adapter;

    private Date selectedDate;

    public static MileageCalendarDialogFragment newInstance(@NonNull Date selectedDate) {

        Bundle args = new Bundle();

        MileageCalendarDialogFragment fragment = new MileageCalendarDialogFragment();
        fragment.setArguments(args);

        fragment.selectedDate = selectedDate;

        return fragment;
    }

    @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_mileage_calendar, null);
        assignViews(view);
        initData();
        refreshData();

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
        this.dateTv = (TextView) view.findViewById(R.id.dateTv);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    private void initData() {
        adapter = new MonthAdapter(getActivity(), recyclerView);
        recyclerView.setAdapter(adapter);
        layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
    }

    private void refreshData() {
        loadData(selectedDate, 0);
    }

    /**
     * @param baseDate
     * @param offset   【0:第一次加载数据，-1:向前加载，1:向后加载】
     */
    private void loadData(Date baseDate, int offset) {
        if (offset == 0) {
            long compareMonth = DateUtil.compareMonth(baseDate, new Date());
            if (compareMonth == 0) {
                List<MonthData> monthDatas = buildMonthDatas(baseDate, 1 - DEFAULT_LOAD_COUNT, 0);
                adapter.replaceItems(monthDatas);
                layoutManager.scrollToPositionWithOffset(adapter.getItemCount() - 1, 0);
            }
            // FIXME: 2017/3/2
        }
        // FIXME: 2017/3/2
    }

    private List<MonthData> buildMonthDatas(Date baseDate, int offsetStart, int offsetEnd) {
        Date firstDayInMonth = DateUtil.getFirstDayInMonth(baseDate);

        int length = offsetEnd - offsetStart + 1;
        Date startDate = DateUtil.addMonth(firstDayInMonth, offsetStart);

        List<MonthData> list = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            list.add(buildMonthData(startDate));

            startDate = DateUtil.addMonth(startDate, 1);
        }
        return list;
    }

    private MonthData buildMonthData(Date monthData) {
        return new MonthData(monthData, null);
    }
}