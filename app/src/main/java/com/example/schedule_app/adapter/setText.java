package com.example.schedule_app.adapter;

import android.widget.TextView;

import com.example.schedule_app.Schedule;
import com.example.schedule_app.Translation;

import java.util.StringTokenizer;


public class setText {
    private final Translation translation;
    private final boolean englishSetting;

    public setText(Boolean englishSetting){
        this.englishSetting = englishSetting;
        this.translation =  new Translation();

    }

    public void setLanguageBasedText(Schedule schedule, TextView date, TextView course,
                                     TextView duration, TextView startTime, TextView endTime, TextView teacher,
                                     TextView room, TextView day){

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

        if(duration == null){
            if(schedule.getDuration() != null){
                StringTokenizer f = new StringTokenizer(schedule.getDuration(), "-");
                startTime.setText(f.nextToken());
                endTime.setText(f.nextToken());
            }else{
                startTime.setText("N/A");
                endTime.setText("N/A");
            }

        }else if(day == null && startTime == null && endTime == null){
            duration.setText(schedule.getDuration());
        }

        if(schedule.getRoom().length() == 0){
            room.setText("-");
        }else{
            room.setText(schedule.getRoom());
        }

        if(schedule.getTeacher().length() == 0){
            teacher.setText("-");
        }else{
            teacher.setText(schedule.getTeacher());
        }

        course.setText(schedule.getCourse());

    }

}
