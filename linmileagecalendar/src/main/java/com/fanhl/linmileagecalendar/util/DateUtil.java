package com.fanhl.linmileagecalendar.util;

import android.support.annotation.NonNull;

import java.text.ParseException;
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
    public static final String FORMAT_M = "M";
    public static final String FORMAT_d = "d";

    /**
     * templete: FORMAT_STR -> Date
     *
     * @param str
     * @param format
     * @return
     */
    public static Date str2date(String str, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

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

    public static Date addMonth(Date date, int step) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, step);
        return calendar.getTime();
    }

    /**
     * 取得一周开始的一天(周日)
     *
     * @param date
     * @return
     */
    public static Date getFirstDayInWeek(Date date) {
        Date firstDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            firstDate = calendar.getTime();
        }
        return firstDate;
    }

    /**
     * 取得一周结束的一天(周六)
     *
     * @param date
     * @return
     */
    public static Date getLastDayInWeek(Date date) {
        Date firstDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            firstDate = new Date(calendar.getTimeInMillis());
        }
        return firstDate;
    }

    /**
     * 判断两个日期是不是在同一月
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isInSameWeek(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.WEEK_OF_MONTH) == cal2.get(Calendar.WEEK_OF_MONTH);
    }

    public static int getWeekCountInMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
    }


    /**
     * 判断两个日期是不是在同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isWeekend(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
    }


    /**
     * 在月份的基础上比较大小
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long compareMonth(@NonNull Date date1, @NonNull Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        return (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR)) * 12 + cal1.get(Calendar.MONTH) - cal2.get(Calendar.MONTH);
    }


    /**
     * fromDate是否大于toDate（最小单位为天）
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    public static boolean isAfterByDay(Date fromDate, Date toDate) {
        if (fromDate == null)
            return false;
        if (toDate == null)
            return true;
        Date fromDate2 = short2date(date2short(fromDate));
        Date toDate2 = short2date(date2short(toDate));

        return fromDate2.getTime() > toDate2.getTime();

        //        if (fromDate.getYear() > toDate.getYear()) {
        //            return true;
        //        } else if (fromDate.getYear() < toDate.getYear()) {
        //            return false;
        //        }else if (fromDate.getMonth() > toDate.getMonth()) {
        //            return true;
        //        } else if (fromDate.getMonth() < toDate.getMonth()) {
        //            return false;
        //        }if (fromDate.getDay() > toDate.getDay()) {
        //            return true;
        //        } else/* if (fromDate.getDay() < toDate.getDay())*/ {
        //            return false;
        //        }
    }

    /**
     * templete: "yyyy-MM-dd" -> Date
     *
     * @param str
     * @return
     */
    public static Date short2date(String str) {
        return str2date(str, FORMAT_SHORT);
    }

    /**
     * templete: Date -> "yyyy-MM-dd"
     *
     * @param date
     * @return
     */
    public static String date2short(Date date) {
        return date2str(date, FORMAT_SHORT);
    }

    /**
     * 判断两个日期是不是在同一月
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameMonth(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
    }
}
