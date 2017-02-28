package com.fanhl.linmileagecalendar;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fanhl.linmileagecalendar.util.DateUtil;

import java.util.Date;

/**
 * 一周的视图
 * <p>
 * Created by fanhl on 2017/2/28.
 */
public class WeekView extends LinearLayout {
    public static final int DAY_IN_WEEK = 7;
    private Date date;

    public WeekView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public WeekView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public WeekView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WeekView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        setOrientation(HORIZONTAL);

        date = new Date();
        resetChildViews(context);

    }

    private void resetChildViews(Context context) {
        removeAllViews();

        Date firstDayInWeek = DateUtil.getFirstDayInWeek(date);
        //Date lastDayInWeek = DateUtil.getLastDayInWeek(date);

        for (int i = 0; i < DAY_IN_WEEK; i++) {
            if (DateUtil.isSameWeek(date, firstDayInWeek)) {
                addView(context, firstDayInWeek);
            } else {
                addView(context, null);
            }

            firstDayInWeek = DateUtil.addDay(firstDayInWeek, 1);
        }
    }

    private void addView(Context context, Date date) {
        if (date != null) {
            MileageDayView view = new MileageDayView(context);
            view.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

            view.setDate(date);
            addView(view);
        } else {
            View view = new View(context);
            view.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

            addView(view);
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

    private void notifyDataChanged() {
        resetChildViews(getContext());
    }
}
