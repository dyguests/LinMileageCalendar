package com.fanhl.linmileagecalendar;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by fanhl on 2017/2/21.
 */

public class MileageDayView extends RelativeLayout {
    public MileageDayView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public MileageDayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public MileageDayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MileageDayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        assignViews(context);
    }

    private void assignViews(Context context) {
        /**
         * see http://www.xmltojava.com/
         * see R.layout.view_day
         */
//        RelativeLayout relativeLayout_144 = new RelativeLayout(this);
//        LayoutParams layout_677 = new LayoutParams();
//        layout_677.width = LayoutParams.MATCH_PARENT;
//        layout_677.height = LayoutParams.MATCH_PARENT;
//        relativeLayout_144.setLayoutParams(layout_677);

        LinearLayout linearLayout_1 = new LinearLayout(context);
        linearLayout_1.setGravity(Gravity.CENTER);
        linearLayout_1.setOrientation(LinearLayout.VERTICAL);
        LayoutParams layout_847 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layout_847.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        linearLayout_1.setLayoutParams(layout_847);

        TextView day = new TextView(context);
        day.setId(R.id.day);
        if (isInEditMode()) day.setText("1");
        day.setText("1");// FIXME: 2017/2/21 test
        day.setTextColor(getResources().getColorStateList(R.color.day_view_text));
        day.setTextSize(getResources().getDimension(R.dimen.day_view_text));
        LayoutParams layout_300 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        day.setLayoutParams(layout_300);
        linearLayout_1.addView(day);

        TextView mileage = new TextView(context);
        mileage.setId(R.id.mileage);
        if (isInEditMode()) mileage.setText("123km");
        mileage.setText("123km");// FIXME: 2017/2/21 test
        mileage.setTextColor(getResources().getColorStateList(R.color.day_view_text_secondary));
        mileage.setTextSize(getResources().getDimension(R.dimen.day_view_text_secondary));
        LayoutParams layout_542 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mileage.setLayoutParams(layout_542);
        linearLayout_1.addView(mileage);
//        relativeLayout_144.addView(linearLayout_1);
        addView(linearLayout_1);
    }
}
