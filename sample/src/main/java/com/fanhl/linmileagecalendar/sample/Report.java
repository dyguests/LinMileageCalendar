package com.fanhl.linmileagecalendar.sample;

import java.util.Date;

/**
 * Created by fanhl on 2017/2/28.
 */
public class Report {
    private Date date;
    private double mileage;

    public Report() {
    }

    public Report(Date date, double mileage) {
        this.date = date;
        this.mileage = mileage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }
}
