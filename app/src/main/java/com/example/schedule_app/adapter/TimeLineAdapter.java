package com.example.schedule_app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.schedule_app.Data;
import com.example.schedule_app.Date;
import com.example.schedule_app.R;
import com.example.schedule_app.Schedule;


import java.util.ArrayList;

public class TimeLineAdapter extends ArrayAdapter<Schedule> {
    private final setText setText;

    public TimeLineAdapter(@NonNull Context context, ArrayList<Schedule> timetable, Data data) {
        super(context, R.layout.time_line_item, timetable);
        this.setText = new setText(data.getEnglishSetting());

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Schedule schedule = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.time_line_item, parent, false);
        }

        TextView date = convertView.findViewById(R.id.Date);
        TextView day = convertView.findViewById(R.id.Day);
        TextView startTime = convertView.findViewById(R.id.StartTime);
        TextView endTime = convertView.findViewById(R.id.EndTime);
        TextView course = convertView.findViewById(R.id.Course);
        TextView teacher = convertView.findViewById(R.id.Teacher);
        TextView room = convertView.findViewById(R.id.Room);
        TextView info = convertView.findViewById(R.id.Details);

        info.setVisibility(View.GONE);
        info.setText(schedule.getInfo());

        this.setText.setLanguageBasedText(schedule, date, course, null,
                                            startTime, endTime, teacher, room, day);

        setVisibility(position, convertView.findViewById(R.id.TimeLineDate));
        setDateBackground(day, date, schedule);
        onClick(convertView.findViewById(R.id.TimeLineSchedule), info);

        return convertView;
    }


    public void setVisibility(int position, ViewGroup dateLayout){

        String blockDate = getItem(position).getFullDate();

        if(position - 1 >= 0 && blockDate.equals(getItem(position-1).getFullDate())){
            dateLayout.setVisibility(View.GONE);
        }else{
            dateLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


    public void setDateBackground(TextView day, TextView dateView, Schedule schedule){
        Drawable circle = getContext().getResources().getDrawable(R.drawable.circle);
        circle.mutate().setTint(getContext().getResources().getColor(R.color.Yellow));
        int e = (int) (getContext().getResources().getDisplayMetrics().density * 17);

        if(schedule.getFullDate().equals(new Date().getTodayDate())){
            day.setTextColor(getContext().getResources().getColor(R.color.Yellow));
            dateView.setBackground(circle);
            dateView.setTextColor(getContext().getResources().getColor(R.color.black));
            dateView.setPadding(0, e, 0,e);
        }else{
            day.setTextColor(getContext().getResources().getColor(R.color.platinum));
            dateView.setTextColor(getContext().getResources().getColor(R.color.platinum));
            dateView.setBackground(null);
            dateView.setPadding(0, 0, 0,0);
        }

    }

    public void onClick(ViewGroup e, TextView info){

        e.setOnClickListener(View -> {
            if (info.getVisibility() == android.view.View.GONE) {
                info.setVisibility(android.view.View.VISIBLE);
            }else if(info.getVisibility() == android.view.View.VISIBLE){
                info.setVisibility(android.view.View.GONE);
            }
        });
    }

}
