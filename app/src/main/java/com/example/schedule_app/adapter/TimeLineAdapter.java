package com.example.schedule_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.schedule_app.Data;
import com.example.schedule_app.Date;
import com.example.schedule_app.R;
import com.example.schedule_app.Schedule;
import com.example.schedule_app.Shape;

import java.util.ArrayList;

public class TimeLineAdapter extends ArrayAdapter<Schedule> {
    private final Adapter adapter;

    public TimeLineAdapter(@NonNull Context context, ArrayList<Schedule> timetable, Data data) {
        super(context, R.layout.time_line_item, timetable);
        this.adapter = new Adapter(data.getEnglishSetting());

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Schedule schedule = getItem(position);
        String[] testKeywords = {" omtentamen ", " test ", " Omtentamen "," Saltentamen ", " saltentamen "};

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.time_line_item, parent, false);
        }

        TextView date = convertView.findViewById(R.id.Date);
        TextView day = convertView.findViewById(R.id.Day);
        TextView duration = convertView.findViewById(R.id.Duration);
        TextView course = convertView.findViewById(R.id.Course);
        TextView teacher = convertView.findViewById(R.id.Teacher);
        TextView room = convertView.findViewById(R.id.Room);
        ViewGroup scheduleLayout = convertView.findViewById(R.id.TimeLineSchedule);
        ViewGroup dateLayout = convertView.findViewById(R.id.TimeLineDate);

        this.adapter.setLanguageBasedText(schedule, date, course, duration, teacher, room, day);
        setBackgrounds(position, scheduleLayout);
        setVisibility(position, dateLayout);

        for (String key: testKeywords) {
            if(getItem(position).getInfo().contains(key)){
                course.setTextColor(getContext().getResources().getColor(R.color.tangerine));
            }
        }

        return convertView;
    }


    public void setBackgrounds(int position, ViewGroup layout){
        Shape shape = new Shape(getContext());
        Date date = new Date("d MMM");
        String blockDate = getItem(position).getFullDate();

        this.adapter.matchDate(shape, date, blockDate);

        layout.setBackground(shape.getShape("timeline"));

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

}
