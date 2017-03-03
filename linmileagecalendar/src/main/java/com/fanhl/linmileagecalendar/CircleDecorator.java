package com.fanhl.linmileagecalendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by fanhl on 2017/3/3.
 */

public class CircleDecorator extends View {

    private Paint paint;

    public CircleDecorator(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public CircleDecorator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public CircleDecorator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircleDecorator(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        paint = new Paint();
        paint.setColor(0xff303d4c);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isSelected()) {
            int width = getWidth();
            int height = getHeight();
            float radius = ((float) Math.min(width, height)) / 2;
            canvas.drawCircle(((float) width) / 2, ((float) height) / 2, radius, paint);
        }
    }
}
