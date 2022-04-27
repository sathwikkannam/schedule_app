package com.example.schedule_app.adapter;

import android.widget.TextView;

import com.example.schedule_app.Schedule;

public class CommonTextView {

    public static void setText(Schedule schedule, TextView course, TextView teacher, TextView room){
        course.setText((schedule.getCourse().length() != 0)? schedule.getCourse() : "-");
        teacher.setText((schedule.getTeacher().length() != 0)? schedule.getTeacher() : "-");
        room.setText((schedule.getRoom().length() != 0)? schedule.getRoom() : "-");
    }


}
