package com.fanhl.linmileagecalendar;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.fanhl.linmileagecalendar.util.DateUtil;

import java.util.Date;

/**
 * Created by fanhl on 2017/2/21.
 */

public class MonthView extends LinearLayout {

    private LinearLayout monthHeader;
    private GridView daysGridView;

    private DayAdapter dayAdapter;

    private Date date;

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
        assignViews(context);
        initData(context);
    }

    private void assignViews(Context context) {
        /**
         * see http://www.xmltojava.com/
         * R.layout.view_month
         */
//        LinearLayout linearLayout_349 = new LinearLayout(this);
//        linearLayout_349.setOrientation(VERTICAL);
        setOrientation(VERTICAL);
//        LayoutParams layout_988 = new LayoutParams();
//        layout_988.width = LayoutParams.MATCH_PARENT;
//        layout_988.height = LayoutParams.MATCH_PARENT;
//        linearLayout_349.setLayoutParams(layout_988);

        monthHeader = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.view_month_header, this, false);
        LayoutParams layoutParams_001 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        monthHeader.setLayoutParams(layoutParams_001);
        addView(monthHeader);

        daysGridView = new GridView(context);
        daysGridView.setNumColumns(7);
        LayoutParams layout_213 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        daysGridView.setLayoutParams(layout_213);
        addView(daysGridView);
    }

    private void initData(Context context) {
        if (isInEditMode()) date = DateUtil.getFirstDayInMonth(new Date());
        date = DateUtil.getFirstDayInMonth(new Date());// FIXME: 2017/2/21 调试用
        dayAdapter = new DayAdapter(context);
        daysGridView.setAdapter(dayAdapter);
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
        // FIXME: 2017/2/22 date to MonthGridView

        dayAdapter.notifyDataSetChanged();
    }

    public class DayAdapter extends BaseAdapter {

        private final Context context;

        public DayAdapter(Context context) {
            this.context = context;
        }

        @Override public int getCount() {
            if (date == null) {
                return 0;
            }
            return DateUtil.getNumberOfDayInMonth(date);
        }

        @Override public Object getItem(int i) {
            return null;// FIXME: 2017/2/21
        }

        @Override public long getItemId(int i) {
            return 0;// FIXME: 2017/2/21 可能不需要
        }

        @Override public View getView(int i, View view, ViewGroup viewGroup) {
            MileageDayView dayView = new MileageDayView(context);
//             LayoutInflater.from(context).inflate(R.layout.view_day, null)
            return dayView;
        }
    }
}
