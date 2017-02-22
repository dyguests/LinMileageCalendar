package com.fanhl.linmileagecalendar.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by fanhl on 2017/2/21.
 */

public class DateUtil {
    public static final String FORMAT_TIMESTAMP = "MMM dd, yyyy HH:mm:ss a";
    public static final String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_yMdHm = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_SHORT = "yyyy-MM-dd";
    public static final String FORMAT_SHORT2 = "yyyy/MM/dd";
    public static final String FORMAT_NUMBER = "yyyyMMdd";
    public static final String FORMAT_CN = "yyyy年MM月dd日 HH:mm:ss";
    public static final String FORMAT_CN_SHORT = "yyyy年MM月dd日";
    public static final String FORMAT_TIME = "HH:mm";
    public static final String FORMAT_yyyyMM = "yyyy-MM";
    public static final String FORMAT_hms = "HH:mm:ss";
    public static final String FORMAT_CN_YM = "yyyy年MM月";
    public static final String FORMAT_CN_YMD = "yyyy年MM月dd日";
    public static final String FORMAT_CN_d_Hm = "dd日 HH:mm";
    public static final String FORMAT_MdHm = "MM-dd HH:mm";
    public static final String FORMAT_Md = "MM-dd";
    public static final String FORMAT_Md2 = "MM/dd";
    public static final String FORMAT_CN_M = "MM月";
    public static final String FORMAT_d = "d";

    /**
     * templete: Date -> FORMAT_STR
     *
     * @param date
     * @param format
     * @return
     */
    public static String date2str(Date date, String format) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(format);
        return sdf.format(date);
    }

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

    public static Date addDay(Date date, int step) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, step);
        return calendar.getTime();
    }
}
