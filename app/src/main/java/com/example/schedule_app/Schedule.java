package com.example.schedule_app;

import java.util.ArrayList;

public class Schedule {
    private final String week;
    private String day;
    private String date;
    private final String duration;
    private final String course;
    private final String teacher;
    private final String room;
    private String info;
    private String month;

    public Schedule(String week, String day, String date, String month, String duration,
                    String course, String teacher, String room, String info) {

        this.date = date;
        this.month = month;
        this.week = week;
        this.day = day;
        this.duration = duration;
        this.course = course;
        this.teacher = teacher;
        this.room = room;
        this.info = info;
    }


    public String getWeek() {
        return this.week;
    }

    public String getDay() {
        return this.day;
    }

    public String getDate() {
        return this.date;
    }

    public String getDuration() {
        return this.duration;
    }

    public String getCourse() {
        return this.course;
    }

    public String getTeacher() {
        return this.teacher;
    }

    public String getRoom() {
        return this.room;
    }

    public String getInfo() {
        return this.info;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMonth(String month) {this.month = month;}

    public String getMonth(){
        return this.month;
    }

    public void setInfo(String info){
        this.info = info;
    }

    public String getFullDate(){
        return String.format("%s %s", getDate(), getMonth());
    }

    public static void sortDates(ArrayList<Schedule> classes){
        classes.forEach(lecture ->{
            if(lecture.getFullDate().length() == 1 && lecture.getDay().length() == 0){
                for (int j = classes.indexOf(lecture)-1; j >=0; j--) {
                    if(classes.get(j).getFullDate().length() != 1 && classes.get(j).getDay().length() != 0){
                        lecture.setDay(classes.get(j).getDay());
                        lecture.setDate(classes.get(j).getDate());
                        lecture.setMonth(classes.get(j).getMonth());
                        break;
                    }
                }
            }
        });
    }

}

