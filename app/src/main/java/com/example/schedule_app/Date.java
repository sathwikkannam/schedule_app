package com.example.schedule_app;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Date {
    private final DateFormat dateFormat;
    private final Calendar calendar;

    public Date(){
        this.dateFormat = new SimpleDateFormat("d MMM", Locale.getDefault());
        this.calendar = Calendar.getInstance();
    }

    public String getTodayDate(){
        return this.dateFormat.format(this.calendar.getTime());
    }

    public String getTomorrowDate(){
        this.calendar.add(Calendar.DAY_OF_YEAR, 1);
        return dateFormat.format(this.calendar.getTime());
    }


    public Time getDifference(String startTime, String endTime){
        int hours = 0;
        int min = 0;


        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            java.util.Date startDate = simpleDateFormat.parse(startTime);
            java.util.Date endDate = simpleDateFormat.parse(endTime);

            long difference = endDate.getTime() - startDate.getTime();
            if(difference<0) {
                java.util.Date dateMax = simpleDateFormat.parse("24:00");
                java.util.Date dateMin = simpleDateFormat.parse("00:00");
                difference=(dateMax.getTime() -startDate.getTime() )+(endDate.getTime()-dateMin.getTime());
            }

             int days = (int) (difference / (1000*60*60*24));
             hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
             min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);


        }catch(ParseException e){
            e.printStackTrace();
        }


        return new Time(hours, min);
    }
}
