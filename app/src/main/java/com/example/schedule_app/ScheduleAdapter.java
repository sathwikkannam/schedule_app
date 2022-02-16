package com.example.schedule_app;

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

    public ScheduleAdapter(@NonNull Context context, ArrayList<Schedule> timetable) {
        super(context, R.layout.object_list, timetable);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        Schedule schedule = getItem(position);

        if(convertView == null){
            convertView =  LayoutInflater.from(getContext()).inflate(R.layout.object_list,parent,false);
        }

        TextView date = convertView.findViewById(R.id.Date);
        TextView day = convertView.findViewById(R.id.Day);
        TextView duration = convertView.findViewById(R.id.Duration);
        TextView course = convertView.findViewById(R.id.Course);
        TextView teacher = convertView.findViewById(R.id.Teacher);
        TextView info = convertView.findViewById(R.id.Info);
        TextView room = convertView.findViewById(R.id.Room);

        date.setText(schedule.getDate());
        day.setText(schedule.getDay());
        duration.setText(schedule.getDuration());
        course.setText(schedule.getCourse());
        teacher.setText(schedule.getTeacher());
        info.setText(schedule.getInfo());
        room.setText(schedule.getRoom());

        return convertView;
    }

}
