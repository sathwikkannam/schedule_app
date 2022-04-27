package com.example.schedule_app;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;

public class WebScraper {

    public static ArrayList<Schedule> scrape(String url){
        ArrayList<Schedule> classes = new ArrayList<>();

        try {
            Jsoup.connect(url).get().select("table.schemaTabell tr").forEach(elem ->{
                String week = elem.select(".data.vecka").text();
                String day = elem.select("td.data.commonCell:nth-of-type(2)").text();
                String date = elem.select("td.data.commonCell:nth-of-type(3)").text();
                String time = elem.select("td.data.commonCell:nth-of-type(4)").text();
                String course = elem.select("td.commonCell:nth-of-type(5)").text();
                String teacher = elem.select("td.commonCell:nth-of-type(6)").text();
                String room = elem.select("td.commonCell:nth-of-type(7)").text();
                String info = elem.select("td.data.commonCell:nth-of-type(9)").text();

                if(time.length() >= 11){
                    if(date.contains(" ")){
                        String[] splitDate = date.split(" ");
                        classes.add(new Schedule(week, day, splitDate[0], splitDate[1], time, course, teacher, room, info));
                    }else{
                        classes.add(new Schedule(week, day, date, date, time, course, teacher, room, info));
                    }

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

}
