package com.example.schedule_app;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Date {
    private final DateFormat dateFormat;
    private final Calendar calendar;

    public Date(String pattern){
        this.dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        this.calendar = Calendar.getInstance();
    }

    public String getTodayDate(){
        return this.dateFormat.format(this.calendar.getTime());
    }

    public String getTomorrowDate(){
        this.calendar.add(Calendar.DAY_OF_YEAR, 1);
        return dateFormat.format(this.calendar.getTime());
    }
}
