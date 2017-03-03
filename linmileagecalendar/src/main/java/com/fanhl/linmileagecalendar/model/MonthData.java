package com.fanhl.linmileagecalendar.model;

import android.util.Log;

import java.util.Date;
import java.util.List;
import java.util.Observable;

/**
 * 存放一个月的里程数据，等
 * <p>
 * Created by fanhl on 2017/3/2.
 */
public class MonthData extends Observable{
    public static final String TAG = MonthData.class.getSimpleName();

    Date month;
    List<MileageDay> mileageDays;

    public MonthData() {
    }

    public MonthData(Date month, List<MileageDay> mileageDays) {
        this.month = month;
        this.mileageDays = mileageDays;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public List<MileageDay> getMileageDays() {
        return mileageDays;
    }

    public void setMileageDays(List<MileageDay> mileageDays) {
        if (this.mileageDays == mileageDays) {
            return;
        }
        this.mileageDays = mileageDays;
        Log.d(TAG, "setMileageDays mileageDays:" + mileageDays);
        setChanged();
        notifyObservers();
    }
}
