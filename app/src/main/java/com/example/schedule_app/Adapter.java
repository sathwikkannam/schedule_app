package com.example.schedule_app;

import android.widget.TextView;


public class Adapter {
    private final Translation translation;
    private final boolean englishSetting;

    public Adapter(Boolean englishSetting){
        this.englishSetting = englishSetting;
        this.translation =  new Translation();

    }

    public void setLanguageBasedText(Schedule schedule, TextView date, TextView course,
                                     TextView duration, TextView teacher, TextView room, TextView day){

        if(day == null){
            if(this.englishSetting){
                date.setText(String.format("%s %s", this.translation.getTranslated(schedule.getFullDate()),
                                                    this.translation.getTranslated(schedule.getDay())));
                course.setText(this.translation.getTranslated(schedule.getCourse()));
            }else{
                date.setText(schedule.getDate());
                course.setText(schedule.getCourse());
            }
        }else{
            if(this.englishSetting){
                day.setText(this.translation.getTranslated(schedule.getDay()));
                date.setText(this.translation.getTranslated(schedule.getDate()));
                course.setText(this.translation.getTranslated(schedule.getCourse()));
            }else{
                day.setText(schedule.getDay());
                date.setText(schedule.getDate());
                course.setText(schedule.getCourse());
            }

        }

        duration.setText(schedule.getDuration());
        teacher.setText(schedule.getTeacher());
        room.setText(schedule.getRoom());

    }

    public void matchDate(Shape shape, Date date, String blockDate){
        if(blockDate.equals(date.getTodayDate())){
            shape.toRed();
        }if(blockDate.equals(date.getTomorrowDate())){
            shape.toGrey();
        }
    }



}
