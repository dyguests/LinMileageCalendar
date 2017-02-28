package com.fanhl.linmileagecalendar.model;

import java.util.Date;

/**
 * Created by fanhl on 2017/2/28.
 */
public class MileageDay {
    private Date date;
    private Float mileage;

    public MileageDay() {
    }

    public MileageDay(Date date, Float mileage) {
        this.date = date;
        this.mileage = mileage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getMileage() {
        return mileage;
    }

    public void setMileage(Float mileage) {
        this.mileage = mileage;
    }
}
