package com.fanhl.linmileagecalendar;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fanhl.linmileagecalendar.model.MileageDay;
import com.fanhl.linmileagecalendar.util.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * 一周的视图
 * <p>
 * Created by fanhl on 2017/2/28.
 */
public class WeekView extends LinearLayout {
    public static final int DAY_IN_WEEK = 7;
    private Date date;
    /**
     * 存当月的里程
     * <p>
     * 有序的（并不保证有每天的数据）
     */
    private List<MileageDay> data;

    private OnDayClickListener onDayClickListener;

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

        Date startDate = DateUtil.getFirstDayInWeek(date);
        //Date lastDayInWeek = DateUtil.getLastDayInWeek(date);

        for (int i = 0; i < DAY_IN_WEEK; i++) {
            if (DateUtil.isInSameWeek(date, startDate)) {
                addView(context, startDate);
            } else {
                addView(context, null);
            }

            startDate = DateUtil.addDay(startDate, 1);
        }
    }

    private void addView(Context context, final Date date) {
        if (date != null) {
            MileageDayView view = new MileageDayView(context);
            view.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

            view.setDate(date);

            if (data != null) {
                for (MileageDay mileageDay : data) {
                    if (DateUtil.isSameDay(date, mileageDay.getDate())) {
                        view.setMileage(mileageDay.getMileage());
                        break;
                    }
                }
            }
            view.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View v) {
                    if (onDayClickListener != null) {
                        onDayClickListener.onDayClick(date);
                    }
                }
            });

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

    public OnDayClickListener getOnDayClickListener() {
        return onDayClickListener;
    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        this.onDayClickListener = onDayClickListener;
    }

    private void notifyDataChanged() {
        resetChildViews(getContext());
    }

    /**
     * 当当月某一天被点击时的处理
     */
    public interface OnDayClickListener {
        void onDayClick(Date date);
    }
}
