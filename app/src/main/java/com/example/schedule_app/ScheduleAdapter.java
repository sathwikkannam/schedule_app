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

    public ScheduleAdapter(@NonNull Context context, ArrayList<Schedule> timetable) {
        super(context, R.layout.object_list, timetable);
        this.translation = new Translation();
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
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

        date.setText(translation.getTranslated(schedule.getDate()) + " " + translation.getTranslated(schedule.getDay()));
        duration.setText(schedule.getDuration());
        course.setText(translation.getTranslated(schedule.getCourse()));
        teacher.setText(schedule.getTeacher());
        room.setText(schedule.getRoom());


        if(schedule.getInfo().length() <= 35){
            info.setText(schedule.getInfo());
        }else{
            info.setText(schedule.getInfo().substring(0, 36) + "...");
        }
        convertView.setBackground(getContext().getResources().getDrawable(R.drawable.rectangle));

        return convertView;
    }

}
