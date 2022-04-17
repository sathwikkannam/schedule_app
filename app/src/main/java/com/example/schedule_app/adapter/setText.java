package com.example.schedule_app.adapter;

import android.widget.TextView;

import com.example.schedule_app.Date;
import com.example.schedule_app.Schedule;
import com.example.schedule_app.Shape;
import com.example.schedule_app.Translation;


public class setText {
    private final Translation translation;
    private final boolean englishSetting;

    public setText(Boolean englishSetting){
        this.englishSetting = englishSetting;
        this.translation =  new Translation();

    }

    public void setLanguageBasedText(Schedule schedule, TextView date, TextView course,
                                     TextView duration, TextView teacher, TextView room, TextView day){

        if(day == null){
            if(this.englishSetting){
                date.setText(String.format("%s %s", this.translation.getTranslated(schedule.getFullDate()),
                                                    this.translation.getTranslated(schedule.getDay())));
            }else{
                date.setText(String.format("%s %s", schedule.getFullDate(), schedule.getDay()));
            }
        }else{
            if(this.englishSetting){
                day.setText(this.translation.getTranslated(schedule.getDay()));
                date.setText(this.translation.getTranslated(schedule.getDate()));
            }else{
                day.setText(schedule.getDay());
                date.setText(schedule.getDate());
            }

        }

        if(schedule.getRoom().length() == 0){
            room.setText("-");
        }else{
            room.setText(schedule.getRoom());
        }

        if(schedule.getDuration().length() == 0){
            duration.setText("-");
        }else{
            duration.setText(schedule.getDuration());
        }

        if(schedule.getTeacher().length() == 0){
            teacher.setText("-");
        }else{
            teacher.setText(schedule.getTeacher());
        }

        course.setText(schedule.getCourse());

    }

}
