package com.mickleentityltdnigeria.resturantapp.utils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    private Calendar cal;
    private int month;
    private int year;
    private int day;
    private int hour;
    private int minute;
    private int second;
    private int millisecond;

    public DateHelper(Date date) {
        this.cal = Calendar.getInstance();
        this.cal.setTime(date);
        this.year = this.cal.get(Calendar.YEAR);
        this.month = this.cal.get(Calendar.MONTH);
        this.day = this.cal.get(Calendar.DAY_OF_MONTH);
        this.hour = this.cal.get(Calendar.HOUR);
        this.minute = this.cal.get(Calendar.MINUTE);
        this.second = this.cal.get(Calendar.SECOND);
        this.millisecond = this.cal.get(Calendar.MILLISECOND);
    }

    public DateHelper(Timestamp timestamp) {
        this.cal = Calendar.getInstance();
        this.cal.setTime(timestamp);
        this.year = this.cal.get(Calendar.YEAR);
        this.month = this.cal.get(Calendar.MONTH);
        this.day = this.cal.get(Calendar.DAY_OF_WEEK);
        this.hour = this.cal.get(Calendar.HOUR);
        this.minute = this.cal.get(Calendar.MINUTE);
        this.second = this.cal.get(Calendar.SECOND);
        this.millisecond = this.cal.get(Calendar.MILLISECOND);
    }

    public Calendar getCal() {
        return cal;
    }

    public void setCal(Calendar cal) {
        this.cal = cal;
        this.year = this.cal.get(Calendar.YEAR);
        this.month = this.cal.get(Calendar.MONTH);
        this.day = this.cal.get(Calendar.DAY_OF_WEEK);
        this.hour = this.cal.get(Calendar.HOUR);
        this.minute = this.cal.get(Calendar.MINUTE);
        this.second = this.cal.get(Calendar.SECOND);
        this.millisecond = this.cal.get(Calendar.MILLISECOND);
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public int getMillisecond() {
        return millisecond;
    }
}
