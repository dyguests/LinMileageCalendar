package com.fanhl.linmileagecalendar.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by fanhl on 2017/2/21.
 */

public class DateUtil {
    /**
     * 取得一个月开始的一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayInMonth(Date date) {
        Date firstDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, 1);    //设置为第一天
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            firstDate = new Date(calendar.getTimeInMillis());
        }
        return firstDate;
    }

    public static int getNumberOfDayInMonth(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
