package com.example.schedule_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ScheduleAdapter extends ArrayAdapter<Schedule> {
    private final Translation translation;
    private final boolean englishSetting;
    private final int timetableLength ;

    public ScheduleAdapter(@NonNull Context context, ArrayList<Schedule> timetable, boolean englishSetting) {
        super(context, R.layout.schedule_item, timetable);
        this.englishSetting = englishSetting;
        this.timetableLength  = timetable.size();
        this.translation = new Translation();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        final String dot = "...";
        Schedule schedule = getItem(position);

        if(convertView == null){
            convertView =  LayoutInflater.from(getContext()).inflate(R.layout.schedule_item,parent,false);
        }

        TextView date = convertView.findViewById(R.id.Date);
        TextView duration = convertView.findViewById(R.id.Duration);
        TextView course = convertView.findViewById(R.id.Course);
        TextView teacher = convertView.findViewById(R.id.Teacher);
        TextView info = convertView.findViewById(R.id.Info);
        TextView room = convertView.findViewById(R.id.Room);

        setLanguageBasedText(schedule, date, course, duration, teacher, room);
        setInfo(schedule, info, dot);
        setBackgrounds(position, convertView, date);
        setOnClick(convertView, info, schedule, dot);

        return convertView;
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    public void setBackgrounds(int position, @Nullable View convertView, TextView list_date){
        String currentDate;
        String previousDate;
        String nextBlockDate;
        Date date = new Date("d MMM");
        Shape shape = new Shape(getContext());
        String blockDate = list_date.getText().toString().substring(0, list_date.getText().length()-4);

        if(blockDate.equals(date.getTodayDate())){
            shape.toRed();
        }if(blockDate.equals(date.getTomorrowDate())){
            shape.toGrey();
        }

        //upper, lower, middle, blue
        if(convertView != null && position < this.timetableLength-1) {
            currentDate = getItem(position).getDate();
            previousDate = (position-1 < 0)? null: getItem(position-1).getDate();
            nextBlockDate = getItem(position + 1).getDate();

            if (!currentDate.equals(previousDate) && currentDate.equals(nextBlockDate)) {
                convertView.setBackground(shape.getShape("upper"));
            } else if (!currentDate.equals(nextBlockDate) && currentDate.equals(previousDate)) {
                convertView.setBackground(shape.getShape("lower"));
            } else if (currentDate.equals(nextBlockDate)) {
                convertView.setBackground(shape.getShape("middle"));
            }else{
                convertView.setBackground(shape.getShape("regular"));
            }
        }

    }

    public void setOnClick(@Nullable View convertView, TextView info, Schedule schedule, String dot){
        // on-click, the entire information is displayed.
        if(convertView != null){
            convertView.setOnClickListener(View ->{
                if(info.getText().toString().substring(info.getText().length()-3).equals(dot)) {
                    info.setText(schedule.getInfo());
                }
            });

        }
    }

    public void setInfo(Schedule schedule, TextView info, String dot){
        if(schedule.getInfo().length() <= 36){
            info.setText(schedule.getInfo());
        }else{
            info.setText(String.format("%s%s", schedule.getInfo().substring(0,36), dot));
        }
    }

    public void setLanguageBasedText(Schedule schedule, TextView date, TextView course, TextView duration, TextView teacher, TextView room){
        String translatedDate = String.format("%s %s", translation.getTranslated(schedule.getDate()), translation.getTranslated(schedule.getDay()));
        String normalDateText = String.format("%s %s", schedule.getDate(), schedule.getDay());
        String translatedCourse = translation.getTranslated(schedule.getCourse());

        date.setText((this.englishSetting)? translatedDate : normalDateText);
        course.setText((this.englishSetting)? translatedCourse : schedule.getCourse());
        duration.setText(schedule.getDuration());
        teacher.setText(schedule.getTeacher());
        room.setText(schedule.getRoom());

    }
}