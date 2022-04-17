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
        TextView duration = convertView.findViewById(R.id.Duration);
        TextView course = convertView.findViewById(R.id.Course);
        TextView teacher = convertView.findViewById(R.id.Teacher);
        TextView room = convertView.findViewById(R.id.Room);
        ViewGroup dateLayout = convertView.findViewById(R.id.TimeLineDate);
        View view = convertView.findViewById(R.id.status);

        this.setText.setLanguageBasedText(schedule, date, course, duration, teacher, room, day);
        setBackground(view, position);
        setVisibility(position, dateLayout);

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

    public void setBackground(View view, int position){
        Date date = new Date("dd MMM");
        if(getItem(position).getFullDate().equals(date.getTodayDate())){
            view.setVisibility(View.VISIBLE);
            view.getBackground().mutate().setTint(getContext().getResources().getColor(R.color.Yellow));
        }else{
            view.setVisibility(View.GONE);
        }


    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

}
