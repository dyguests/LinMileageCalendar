package com.fanhl.linmileagecalendar;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fanhl.linmileagecalendar.model.MileageDay;
import com.fanhl.linmileagecalendar.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fanhl on 2017/2/28.
 */
public class MonthView extends LinearLayout {
    public static final String TAG = MonthView.class.getSimpleName();
    private Date date;
    /**
     * 存当月的里程
     * <p>
     * 有序的（并不保证有每天的数据）
     */
    private List<MileageDay> data;

    public MonthView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public MonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public MonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MonthView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        setOrientation(VERTICAL);

        date = DateUtil.getFirstDayInMonth(new Date());

        resetChildViews(context);
    }

    private void resetChildViews(Context context) {
        removeAllViews();

        //header
        View monthHeader = LayoutInflater.from(context).inflate(R.layout.view_month_header, this, false);
        monthHeader.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(monthHeader);

        //weeks
        Date firstDayInMonth = DateUtil.getFirstDayInMonth(date);

        int weekCount = DateUtil.getWeekCountInMonth(date);
        for (int i = 0; i < weekCount; i++) {
            WeekView child = new WeekView(context);
            child.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));

            child.setDate(firstDayInMonth);
            if (data != null) {
                List<MileageDay> weekData = new ArrayList<>();
                for (MileageDay mileageDay : data) {
                    if (DateUtil.isInSameWeek(firstDayInMonth, mileageDay.getDate())) {
                        weekData.add(mileageDay);
                    }
                }
                child.setData(weekData);
            }

            addView(child);

            firstDayInMonth = DateUtil.addDay(firstDayInMonth, 7);
            firstDayInMonth = DateUtil.getFirstDayInWeek(firstDayInMonth);
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        if (this.date == date) {
            return;
        }
        this.date = date;

        notifyDataChanged();
    }

    public List<MileageDay> getData() {
        return data;
    }

    public void setData(List<MileageDay> data) {
        if (this.data == data) {
            return;
        }
        this.data = data;
        notifyDataChanged();
    }

    private void notifyDataChanged() {
        resetChildViews(getContext());
    }
}
