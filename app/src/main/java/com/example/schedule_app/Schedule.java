package com.example.schedule_app;


public class Schedule {
    private String week;
    private String day;
    private String date;
    private String duration;
    private String course;
    private String teacher;
    private String room;
    private String info;


    public Schedule(String week, String day, String date, String duration, String course, String teacher, String room, String info) {
        this.week = week;
        this.day = day;
        this.date = date;
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

}

