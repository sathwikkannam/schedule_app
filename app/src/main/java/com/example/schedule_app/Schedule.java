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
    private final String info;
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

    public String getFullDate(){
        return String.format("%s %s", getDate(), getMonth());
    }

    public static void sortDates(ArrayList<Schedule> classes){
        for (int i = 0; i < classes.size(); i++) {
            if(classes.get(i).getFullDate().length() == 1 && classes.get(i).getDay().length() == 0){
                for (int j = i-1; j >=0; j--) {
                    if(classes.get(j).getFullDate().length() != 1 && classes.get(j).getDay().length() != 0){
                        classes.get(i).setDay(classes.get(j).getDay());
                        classes.get(i).setDate(classes.get(j).getDate());
                        classes.get(i).setMonth(classes.get(j).getMonth());
                        break;
                    }
                }
            }

        }
    }

}

