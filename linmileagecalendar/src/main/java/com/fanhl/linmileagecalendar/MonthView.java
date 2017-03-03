package com.fanhl.linmileagecalendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
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
    public static final boolean MONTH_HEADER_SHOWED_DEFAULT = true;
    private Date date;
    /**
     * 存当月的里程
     * <p>
     * 有序的（并不保证有每天的数据）
     */
    private List<MileageDay> data;

    private OnDayClickListener onDayClickListener;
    /** 是否显示标头（就是 日一二...六） */
    private boolean monthHeaderShowed = MONTH_HEADER_SHOWED_DEFAULT;
    /** 仅显示记住当前所选中的Day */
    private Date selectedDate;

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
        if (attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MonthView, defStyleAttr, defStyleRes);
            try {
                monthHeaderShowed = typedArray.getBoolean(R.styleable.MonthView_mc_month_header_showed, MONTH_HEADER_SHOWED_DEFAULT);
            } catch (Exception e) {
                Log.e(TAG, "get typedArray value fail", e);
            } finally {
                typedArray.recycle();
            }
        }

        setOrientation(VERTICAL);

        date = DateUtil.getFirstDayInMonth(new Date());

        resetChildViews(context);
    }

    private void resetChildViews(Context context) {
        removeAllViews();

//        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        //header
        if (monthHeaderShowed) {
            View monthHeader = LayoutInflater.from(context).inflate(R.layout.view_month_header, this, false);
            monthHeader.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            addView(monthHeader);
        }

        //weeks
        Date firstDayInMonth = DateUtil.getFirstDayInMonth(date);

        int weekCount = DateUtil.getWeekCountInMonth(date);
        for (int i = 0; i < weekCount; i++) {
            WeekView child = new WeekView(context);
            child.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));

            if (DateUtil.isInSameWeek(selectedDate, firstDayInMonth)) {
                child.setSelectedDate(selectedDate);
            }

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
            child.setOnDayClickListener(new WeekView.OnDayClickListener() {
                @Override public void onDayClick(MileageDayView dayView) {
                    if (onDayClickListener != null) {
                        onDayClickListener.onDayClick(dayView);
                    }
                }
            });

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

    public OnDayClickListener getOnDayClickListener() {
        return onDayClickListener;
    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        this.onDayClickListener = onDayClickListener;
    }

    public boolean isMonthHeaderShowed() {
        return monthHeaderShowed;
    }

    public void setMonthHeaderShowed(boolean monthHeaderShowed) {
        if (this.monthHeaderShowed == monthHeaderShowed) {
            return;
        }
        this.monthHeaderShowed = monthHeaderShowed;
        notifyDataChanged();
    }

    private void notifyDataChanged() {
        resetChildViews(getContext());
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    /**
     * 当当月某一天被点击时的处理
     */
    public interface OnDayClickListener {
        void onDayClick(MileageDayView dayView);
    }
}
