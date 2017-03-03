package com.fanhl.linmileagecalendar;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanhl.linmileagecalendar.util.DateUtil;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * 天（包含mileage)
 * Created by fanhl on 2017/2/21.
 */
public class MileageDayView extends RelativeLayout {
    private Date date;
    private Float mileage;

    private CircleDecorator circleDecorator;
    private TextView dayTv;
    private TextView mileageTv;

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

        circleDecorator = new CircleDecorator(context);
        circleDecorator.setId(R.id.circleDecorator);
        LayoutParams layout_649 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        circleDecorator.setLayoutParams(layout_649);
        addView(circleDecorator);


        LinearLayout linearLayout_1 = new LinearLayout(context);
        linearLayout_1.setGravity(Gravity.CENTER);
        linearLayout_1.setOrientation(LinearLayout.VERTICAL);
        LayoutParams layout_847 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layout_847.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        linearLayout_1.setLayoutParams(layout_847);

        dayTv = new TextView(context);
        dayTv.setId(R.id.day);
        if (isInEditMode()) dayTv.setText("1");
        dayTv.setText("1");// FIXME: 2017/2/21 test
        if (date == null || !DateUtil.isWeekend(date)) {
            dayTv.setTextColor(getResources().getColorStateList(R.color.day_view_text));
        } else {
            dayTv.setTextColor(getResources().getColorStateList(R.color.day_view_text_secondary));
        }

        dayTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.day_view_text));
        LayoutParams layout_300 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        dayTv.setLayoutParams(layout_300);
        linearLayout_1.addView(dayTv);

        mileageTv = new TextView(context);
        mileageTv.setId(R.id.mileage);
        if (isInEditMode()) mileageTv.setText("123km");
//        mileageTv.setText("123km");// FIXME: 2017/2/21 test
        mileageTv.setTextColor(getResources().getColorStateList(R.color.day_view_text_secondary));
        mileageTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.day_view_text_secondary));
        LayoutParams layout_542 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mileageTv.setLayoutParams(layout_542);
        linearLayout_1.addView(mileageTv);
//        relativeLayout_144.addView(linearLayout_1);
        addView(linearLayout_1);
    }

    @Deprecated
    public float pixelsToSp(float px) {
        float scaledDensity = getResources().getDisplayMetrics().scaledDensity;
        return px / scaledDensity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        if (this.date == date) {
            return;
        }
        this.date = date;
        dayTv.setText(DateUtil.date2str(date, DateUtil.FORMAT_d));
        if (date == null || !DateUtil.isWeekend(date)) {
            dayTv.setTextColor(getResources().getColorStateList(R.color.day_view_text));
        } else {
            dayTv.setTextColor(getResources().getColorStateList(R.color.day_view_text_secondary));
        }
        if (DateUtil.isSameDay(date, new Date())) {
            setToday(true);
        }
    }

    public Float getMileage() {
        return mileage;
    }

    public void setMileage(Float mileage) {
        if (this.mileage == mileage) {
            return;
        }
        this.mileage = mileage;

        String mileageStr;
        if (mileage == null || mileage == 0) {
            mileageStr = "";
        } else if (mileage < 100) {
            mileageStr = new DecimalFormat("#.#").format(mileage) + "km";
        } else {
            mileageStr = new DecimalFormat("#").format(mileage) + "km";
        }

        mileageTv.setText(mileageStr);
    }

    private void setToday(boolean isToday) {
        dayTv.setEnabled(!isToday);
        mileageTv.setEnabled(!isToday);
    }

    @Override public void setSelected(boolean selected) {
        super.setSelected(selected);
        circleDecorator.setSelected(selected);
        dayTv.setSelected(selected);
        mileageTv.setSelected(selected);
        invalidate();
    }
//
//    @Override public void draw(Canvas canvas) {
//        super.draw(canvas);
////        if (isSelected()) {
//        canvas.drawColor(Color.RED);
////        }
//        canvas.drawText("Text", 0, 0, new TextPaint());
//    }
}
