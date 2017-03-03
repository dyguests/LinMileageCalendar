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
import com.fanhl.linmileagecalendar.common.OnRcvScrollListener;
import com.fanhl.linmileagecalendar.constant.Constant;
import com.fanhl.linmileagecalendar.model.MileageDay;
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
    public static final int DEFAULT_LOAD_COUNT = Constant.PAGE_SIZE_DEFAULT;

    private android.widget.TextView dateTv;
    private RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private MonthAdapter adapter;

    private Date selectedDate;

    private Callback callback;
    private MonthAdapter.OnDayClickListener onDayClickListener;

    public static MileageCalendarDialogFragment newInstance(@NonNull Date selectedDate, @NonNull MonthAdapter.OnDayClickListener onDayClickListener, Callback callback) {

        Bundle args = new Bundle();

        MileageCalendarDialogFragment fragment = new MileageCalendarDialogFragment();
        fragment.setArguments(args);

        fragment.selectedDate = selectedDate;
        fragment.onDayClickListener = onDayClickListener;
        fragment.callback = callback;

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
        adapter.setOnDayClickListener(new MonthAdapter.OnDayClickListener() {
            @Override public void onDayClick(Date date) {
                if (DateUtil.isAfterByDay(date, new Date())) {
                    Log.d(TAG, "不能选择当天之后的日期");
                    return;
                }
                onDayClickListener.onDayClick(date);
                dismiss();
            }
        });

        recyclerView.setAdapter(adapter);
        layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
        recyclerView.addOnScrollListener(new OnRcvScrollListener() {
            @Override public void onScrollTop() {
                List<MonthData> list = adapter.getList();
                if (list != null && !list.isEmpty()) {
                    Date month = list.get(0).getMonth();
                    Log.d(TAG, "onScrollTop month:" + month);
                    loadData(month, -1);
                }
            }

            @Override
            public void onScrollBottom() {
                List<MonthData> list = adapter.getList();
                if (list != null && !list.isEmpty()) {
                    Date month = list.get(list.size() - 1).getMonth();
                    Log.d(TAG, "onScrollBottom month:" + month);
                    loadData(month, 1);
                }
            }
        });

        if (selectedDate == null) {
            selectedDate = new Date();
        }
    }

    private void refreshData() {
        loadData(selectedDate, 0);
    }

    /**
     * @param baseDate
     * @param offset   【0:第一次加载数据，-1:向前加载，1:向后加载】
     */
    private void loadData(Date baseDate, int offset) {
        //不能超过当前月
        if (DateUtil.compareMonth(baseDate, new Date()) > 0) {
            baseDate = new Date();
        }

        if (offset == 0) {
            long compareMonth = DateUtil.compareMonth(baseDate, new Date());
            if (compareMonth == 0) {
                List<MonthData> monthDatas = buildMonthDatas(baseDate, 1 - DEFAULT_LOAD_COUNT, 0);
                adapter.replaceItems(monthDatas);
                layoutManager.scrollToPositionWithOffset(DEFAULT_LOAD_COUNT - 1, 0);

                refreshMonthDataMileages(monthDatas.get(DEFAULT_LOAD_COUNT - 1));
            } else if (compareMonth < 0) {
                int offsetStart = DEFAULT_LOAD_COUNT / 2 - DEFAULT_LOAD_COUNT + 1;
                int offsetEnd = DEFAULT_LOAD_COUNT / 2;

                //如果要生成的最后一个月大于当前月，则把最后一个月生成当前月。
                if (offsetEnd + compareMonth > 0) {
                    offsetEnd = (int) -compareMonth;
                }

                List<MonthData> monthDatas = buildMonthDatas(baseDate, offsetStart, offsetEnd);
                adapter.replaceItems(monthDatas);
                layoutManager.scrollToPositionWithOffset(-offsetStart, 0);

                refreshMonthDataMileages(monthDatas.get(-offsetStart));
            } else {
                throw new RuntimeException("baseDate不可能超过当前月。");
            }
        } else if (offset < 0) {
            List<MonthData> monthDatas = buildMonthDatas(baseDate, -DEFAULT_LOAD_COUNT, -1);
            adapter.addFirstItems(monthDatas);
        } else {
            long compareMonth = DateUtil.compareMonth(baseDate, new Date());
            //未超过当前月
            if (compareMonth < 0) {
                List<MonthData> monthDatas;
                if (DEFAULT_LOAD_COUNT + compareMonth >= 0) {
                    monthDatas = buildMonthDatas(baseDate, 1, DEFAULT_LOAD_COUNT);
                } else {
                    monthDatas = buildMonthDatas(baseDate, 1, (int) -compareMonth);
                }
                adapter.addItems(monthDatas);
            }
        }
    }

    /**
     * 根据 monthData 中的 month 取得 List<Mileage/>
     *
     * @param monthData
     */
    private void refreshMonthDataMileages(MonthData monthData) {
        callback.refreshMonthDataMileages(monthData);
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

    public interface Callback {
        void refreshMonthDataMileages(MonthData monthData);

        interface RefreshMonthDataMileagesCallback {
            void onResponse(MonthData monthData, List<MileageDay> mileageDays);
        }
    }
}
