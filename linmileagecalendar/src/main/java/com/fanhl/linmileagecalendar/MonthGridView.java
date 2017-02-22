package com.fanhl.linmileagecalendar;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.fanhl.linmileagecalendar.util.DateUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by fanhl on 2017/2/22.
 */
public class MonthGridView extends ViewGroup {
    public static final String TAG = MonthGridView.class.getSimpleName();

    private Date date;

    public MonthGridView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public MonthGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public MonthGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MonthGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        date = DateUtil.getFirstDayInMonth(new Date());

        // FIXME: 2017/2/22 eidtMode
        if (date != null) {
            for (int i = 0; i < DateUtil.getNumberOfDayInMonth(date); i++) {
                MileageDayView child = new MileageDayView(context);
                child.setDate(DateUtil.addDay(date, i));
                addView(child);
            }
        }
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();
        int specSizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specSizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(specSizeWidth, specSizeHeight);

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            this.measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "l:" + l + " t:" + t + " r:" + r + " b" + b);

        if (date == null) {
            return;
        }

        int width = getWidth();
        int height = getHeight();
        Log.d(TAG, "onLayout width:" + width + " height:" + height);

//        int width = r - l;
//        int height = b - t;

        int mLeft = 0;
        int mTop = 0;
        int mRight = width;
        int mBottom = height;

        int childCount = getChildCount();
//        if(true)throw new RuntimeException("childCount:" + childCount);

        int firstDayOffset = getFirstDayOffset(date);
//        if(true)throw new RuntimeException("firstDayOffset:" + firstDayOffset);
        int rowCount = getRowCount(date);
//        if(true)throw new RuntimeException("rowCount:" + rowCount);

        int childWidth = width / 7;
        int childWidthRemainder = width % 7;//在整数除法中，多余的余数也不能不管
        int childHeight = height / rowCount;
        int childHeightRemainder = height % rowCount;//在整数除法中，多余的余数也不能不管

        int oneChildLeft = mLeft;
        int oneChildTop = mTop;

        for (int i = 0; i < firstDayOffset + childCount; i++) {
            int oneChildWidthRemainder = (childWidthRemainder > (i % 7)) ? 1 : 0;
            int oneChildHeightRemainder = (childHeightRemainder > (i / 7)) ? 1 : 0;

            if (i >= firstDayOffset) {
                View child = getChildAt(i - firstDayOffset);
                child.layout(oneChildLeft, oneChildTop, oneChildLeft + childWidth + oneChildWidthRemainder, oneChildTop + childHeight + oneChildHeightRemainder);
            }

            oneChildLeft += childWidth + oneChildWidthRemainder;
            if (i % 7 == 6) {
                oneChildLeft = mLeft;
                oneChildTop += childHeight + oneChildHeightRemainder;
            }
        }
    }

    /**
     * 取得第一天的偏移值
     *
     * @param date
     * @return
     */
    private int getFirstDayOffset(Date date) {
        Date firstDayInMonth = DateUtil.getFirstDayInMonth(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(firstDayInMonth);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        // sunday 返回的是1，对应偏移值是0
        return day - 1;
    }

    /**
     * 取得当前月在日历中会显示多少行
     *
     * @param date
     * @return
     */
    private int getRowCount(Date date) {
        int firstDayOffset = getFirstDayOffset(date);
        int numberOfDayInMonth = DateUtil.getNumberOfDayInMonth(date);

        int gridCount = firstDayOffset + numberOfDayInMonth;

        int row = gridCount / 7;
        if (gridCount % 7 > 0) {
            row += 1;
        }

        return row;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
