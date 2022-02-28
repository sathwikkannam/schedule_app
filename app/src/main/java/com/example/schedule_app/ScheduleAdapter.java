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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ScheduleAdapter extends ArrayAdapter<Schedule> {
    private final Translation translation;
    private final boolean englishSetting;
    private final ArrayList<Schedule> classes ;

    public ScheduleAdapter(@NonNull Context context, ArrayList<Schedule> timetable, boolean englishSetting) {
        super(context, R.layout.object_list, timetable);
        this.englishSetting = englishSetting;
        this.classes  = timetable;
        this.translation = new Translation();
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        final String dot = "...";
        Schedule schedule = getItem(position);

        if(convertView == null){
            convertView =  LayoutInflater.from(getContext()).inflate(R.layout.object_list,parent,false);
        }

        TextView date = convertView.findViewById(R.id.Date);
        TextView duration = convertView.findViewById(R.id.Duration);
        TextView course = convertView.findViewById(R.id.Course);
        TextView teacher = convertView.findViewById(R.id.Teacher);
        TextView info = convertView.findViewById(R.id.Info);
        TextView room = convertView.findViewById(R.id.Room);

        setLanguageText(schedule, date, course, duration, teacher, room);
        setInfo(schedule, info, dot);
        setBackgrounds(position, convertView);
        setOnClick(convertView, info, schedule, dot);

        return convertView;
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    public void setBackgrounds(int position, @Nullable View convertView){
        boolean isOutOfIndex = position != 0 && position != this.classes.size()-1;
        String currentDate = null;
        String previousDate = null;
        String nextBlockDate = null;

        if(isOutOfIndex){
            currentDate = getItem(position).getDate();
            previousDate = getItem(position-1).getDate();
            nextBlockDate = getItem(position+1).getDate();
        }

        if(convertView != null){
            if(isOutOfIndex && currentDate.equals(previousDate) && currentDate.equals(nextBlockDate)){
                convertView.setBackground(getContext().getResources().getDrawable(R.drawable.middle_rectangle));
            }else if(isOutOfIndex && currentDate.equals(previousDate)){
                convertView.setBackground(getContext().getResources().getDrawable(R.drawable.upper_rectangle));
            }else if(isOutOfIndex && currentDate.equals(nextBlockDate)){
                convertView.setBackground(getContext().getResources().getDrawable(R.drawable.lower_rectangle));
            }else{
                convertView.setBackground(getContext().getResources().getDrawable(R.drawable.blue_recentagle));
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

    @SuppressLint("UseCompatLoadingForDrawables")
    public void matchCurrentDate(@Nullable View convertView, TextView date){
         //if the current matches the current position's date

        if(convertView != null){
            String currentDate = new SimpleDateFormat("dd MMM", Locale.getDefault()).format(new Date());
            if(date.getText().toString().substring(0, date.getText().length() - 4).equals(currentDate)){
                convertView.setBackground(getContext().getResources().getDrawable(R.drawable.outlined_rectangle));
            }else{
                convertView.setBackground(getContext().getResources().getDrawable(R.drawable.blue_recentagle));
            }

        }

    }

    public void setInfo(Schedule schedule, TextView info, String dot){
        if(schedule.getInfo().length() <= 36){
            info.setText(schedule.getInfo());
        }else{
            info.setText(schedule.getInfo().substring(0, 36) + dot);
        }
    }


    public void setLanguageText(Schedule schedule, TextView date, TextView course, TextView duration, TextView teacher, TextView room){
        if(this.englishSetting){
            date.setText(String.format("%s %s", translation.getTranslated(schedule.getDate()), translation.getTranslated(schedule.getDay())));
            course.setText(translation.getTranslated(schedule.getCourse()));
        }else{
            date.setText(String.format("%s %s", schedule.getDate(), schedule.getDay()));
            course.setText(schedule.getCourse());
        }
        duration.setText(schedule.getDuration());
        teacher.setText(schedule.getTeacher());
        room.setText(schedule.getRoom());

    }



}
