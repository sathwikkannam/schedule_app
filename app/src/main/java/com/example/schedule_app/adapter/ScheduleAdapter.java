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
import com.example.schedule_app.FirebaseTranslator;
import com.example.schedule_app.R;
import com.example.schedule_app.Schedule;
import com.example.schedule_app.Shape;
import com.example.schedule_app.DaysTranslation;
import com.google.mlkit.nl.translate.TranslateLanguage;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class ScheduleAdapter extends ArrayAdapter<Schedule> {
    private final int timetableLength;
    private final FirebaseTranslator firebaseTranslator;

    public ScheduleAdapter(@NonNull Context context, ArrayList<Schedule> timetable) {
        super(context, R.layout.schedule_item, timetable);
        this.timetableLength = timetable.size();
        this.firebaseTranslator = FirebaseTranslator.getInstance(TranslateLanguage.SWEDISH, TranslateLanguage.ENGLISH);

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
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

        setText(schedule, date, duration, info);
        CommonTextView.setText(schedule, course, teacher, room);
        setInfo(schedule, info);
        setBackgrounds(position, convertView, date);
        setOnClick(convertView, info, schedule);

        return convertView;
    }


    private void setBackgrounds(int position, @Nullable View convertView, TextView list_date){
        String currentDate;
        String previousDate;
        String nextBlockDate;
        Date date = new Date();
        Shape shape = new Shape(getContext());
        String blockDate = list_date.getText().toString().substring(0, list_date.getText().length()-4);

        if(blockDate.equals(date.getTodayDate())){
            shape.toRed();
        }if(blockDate.equals(date.getTomorrowDate())){
            shape.toGrey();
        }

        //upper, lower, middle, blue
        if(convertView != null && position < this.timetableLength-1) {
            currentDate = getItem(position).getFullDate();
            previousDate = (position-1 < 0)? null: getItem(position-1).getFullDate();
            nextBlockDate = getItem(position + 1).getFullDate();

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

    private void setOnClick(@Nullable View convertView, TextView info, Schedule schedule){
        // on-click, the entire information is displayed.
        if(convertView != null){
            convertView.setOnClickListener(View ->{
                if(info.getText().toString().substring(info.getText().length()-3).equals("...")) {
                    info.setText(schedule.getInfo());
                }
            });

        }
    }

    private void setInfo(Schedule schedule, TextView info){
        if(schedule.getInfo().length() <= 36){
            info.setText(schedule.getInfo());
        }else{
            info.setText(String.format("%s%s", schedule.getInfo().substring(0,36), "..."));
        }
    }


    private void setText(Schedule schedule, TextView date, TextView duration, TextView info){

        if(Data.getInstance(getContext()).getEnglishSetting()){
            Executors.newSingleThreadExecutor().execute(() -> this.firebaseTranslator.getTranslator().translate(schedule.getInfo()).addOnSuccessListener(s ->{
                if(s.length() <= 36){
                    info.setText(s);
                }else{
                    info.setText(String.format("%s %s", s.substring(0,36), "..."));
                }
            }));

            date.setText(String.format("%s %s", schedule.getFullDate(), DaysTranslation.getInstance().getTranslated(schedule.getDay())));

        }else{
            date.setText(String.format("%s %s", schedule.getFullDate(), schedule.getDay()));
            info.setText(schedule.getInfo());
        }

        duration.setText(schedule.getDuration());

    }

}