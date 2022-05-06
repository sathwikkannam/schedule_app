package com.example.schedule_app.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.schedule_app.Data;
import com.example.schedule_app.Date;
import com.example.schedule_app.translation.FirebaseTranslator;
import com.example.schedule_app.R;
import com.example.schedule_app.Schedule;
import com.example.schedule_app.translation.DaysTranslation;
import com.google.mlkit.nl.translate.TranslateLanguage;


import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.Executors;

public class TimeLineAdapter extends ArrayAdapter<Schedule> {
    private final FirebaseTranslator firebaseTranslator;
    private final int dataSize;

    public TimeLineAdapter(@NonNull Context context, ArrayList<Schedule> timetable) {
        super(context, R.layout.time_line_item, timetable);
        this.dataSize = timetable.size();
        this.firebaseTranslator = FirebaseTranslator.getInstance(TranslateLanguage.SWEDISH, TranslateLanguage.ENGLISH);

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
        TextView month = convertView.findViewById(R.id.WeekAndMonth);
        View line = convertView.findViewById(R.id.line1);
        info.setVisibility(View.GONE);


        setText(position, month, day, date, startTime, endTime, line, schedule, info);
        CommonTextView.setText(schedule, course, teacher, room);
        setVisibility(position, convertView.findViewById(R.id.TimeLineDate));
        setDateBackground(day, date, schedule);
        onClick(convertView.findViewById(R.id.TimeLineSchedule), info);

        convertView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));

        return convertView;
    }


    private void setVisibility(int position, ViewGroup dateLayout){
        if(position - 1 >= 0 && getItem(position).getFullDate().equals(getItem(position-1).getFullDate())){
            dateLayout.setVisibility(View.GONE);
        }else{
            dateLayout.setVisibility(View.VISIBLE);
        }
    }


    private void setDateBackground(TextView day, TextView dateView, Schedule schedule){
        Drawable circle = getContext().getDrawable(R.drawable.circle);
        circle.mutate().setTint(getContext().getColor(R.color.Yellow));
        int padding = (int) (getContext().getResources().getDisplayMetrics().density * 17);

        if(schedule.getFullDate().equals(new Date().getTodayDate())){
            day.setTextColor(getContext().getColor(R.color.Yellow));
            dateView.setBackground(circle);
            dateView.setTextColor(getContext().getColor(R.color.black));
            dateView.setPadding(0, padding, 0,padding);
        }else{
            day.setTextColor(getContext().getColor(R.color.platinum));
            dateView.setTextColor(getContext().getColor(R.color.platinum));
            dateView.setBackground(null);
            dateView.setPadding(0, 0, 0,0);
        }

    }

    private void onClick(ViewGroup view, TextView info){

        view.setOnClickListener(View -> {
            if (info.getVisibility() == android.view.View.GONE) {
                TransitionManager.beginDelayedTransition(view);
                info.setVisibility(android.view.View.VISIBLE);
                info.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bottom_to_top));
            }else if(info.getVisibility() == android.view.View.VISIBLE){
                info.setVisibility(android.view.View.GONE);
            }

        });
    }

    private void setText(int position, TextView month, TextView day, TextView date, TextView startTime,
                         TextView endTime, View line, Schedule schedule, TextView info){
        String lastDay = null;

        if(schedule.getDuration() != null){
            StringTokenizer stringTokenizer = new StringTokenizer(schedule.getDuration(), "-");
            startTime.setText(stringTokenizer.nextToken());
            endTime.setText(stringTokenizer.nextToken());
            endTime.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
        }else{
            startTime.setText("N/A");
            endTime.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        }

        if(Data.getInstance(getContext()).getEnglishSetting()){
            Executors.newSingleThreadExecutor().execute(() -> this.firebaseTranslator.translate(info, schedule.getInfo()));
            day.setText(DaysTranslation.getInstance().getTranslated(schedule.getDay()));
        }else{
            day.setText(schedule.getDay());
            info.setText(schedule.getInfo());
        }

        date.setText(schedule.getDate());

        if(position == 0 || (position - 1 >= 0 && !getItem(position).getMonth().equals(getItem(position - 1).getMonth()))){
            month.setText(DaysTranslation.getInstance().getTranslated(schedule.getMonth()));
            for (int i = position; i < dataSize; i++) {
                if(!getItem(i).getMonth().equals(schedule.getMonth())){
                    lastDay = getItem(i - 1).getDate();
                    break;
                } else if (i == dataSize - 1) {
                    lastDay = getItem(i).getDate();
                }
            }
            month.setText(String.format("%s %s ― %s", DaysTranslation.getInstance().getTranslated(schedule.getMonth()), schedule.getDate(), lastDay));
            month.setVisibility(View.VISIBLE);
        }else if (getItem(position).getMonth().equals(getItem(position - 1).getMonth())){
            month.setVisibility(View.GONE);
        }

    }

    public FirebaseTranslator getTranslator(){
        return this.firebaseTranslator;
    }

    @Override
    public int getCount() {
        return this.dataSize;
    }
}
