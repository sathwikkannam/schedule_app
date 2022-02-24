package com.example.schedule_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ScheduleAdapter extends ArrayAdapter<Schedule> {
    private final Translation translation;
    private final RelativeLayout layout;

    public ScheduleAdapter(@NonNull Context context, ArrayList<Schedule> timetable, RelativeLayout layout) {
        super(context, R.layout.object_list, timetable);
        this.layout = layout;
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

        // this algorithm is for grouping the courses in the same day in a same cell by creating bunch of textviews.
        if(position != 0 && getItem(position-1).getDate().equals(getItem(position).getDate())){
            TextView currentCourse;

            if(getItem(position-1).getCourse().equals(getItem(position).getCourse())){
                currentCourse = new TextView(getContext());
            }


            TextView currentDate = new TextView(getContext());
            TextView differentRoom = new TextView(getContext());
            TextView differentDuration = new TextView(getContext());
            TextView differentTeacher = new TextView(getContext());
            TextView differentInfo = new TextView(getContext());

            currentDate.setGravity(View.FOCUS_RIGHT);
            differentRoom.setGravity(View.FOCUS_LEFT);
            differentDuration.setGravity(View.FOCUS_RIGHT);
            differentTeacher.setGravity(View.FOCUS_LEFT);
            differentInfo.setGravity(View.TEXT_ALIGNMENT_CENTER);

            RelativeLayout.LayoutParams layoutDate = (RelativeLayout.LayoutParams) currentDate.getLayoutParams();
            RelativeLayout.LayoutParams layoutTeacher = (RelativeLayout.LayoutParams) differentTeacher.getLayoutParams();
            RelativeLayout.LayoutParams layoutCourse = (RelativeLayout.LayoutParams) course.getLayoutParams();
            RelativeLayout.LayoutParams layoutDuration = (RelativeLayout.LayoutParams) differentDuration.getLayoutParams();
            RelativeLayout.LayoutParams layoutInfo = (RelativeLayout.LayoutParams) differentInfo.getLayoutParams();
            RelativeLayout.LayoutParams layoutRoom = (RelativeLayout.LayoutParams) differentRoom.getLayoutParams();

            layoutDate.addRule(RelativeLayout.BELOW, info.getId());
            layoutTeacher.addRule(RelativeLayout.BELOW, differentInfo.getId());
            layoutRoom.addRule(RelativeLayout.BELOW, differentTeacher.getId());
            layoutDuration.addRule(RelativeLayout.BELOW, currentDate.getId());
            layoutInfo.addRule(RelativeLayout.BELOW, differentRoom.getId());

            currentDate.setLayoutParams(layoutDate);
            differentTeacher.setLayoutParams(layoutTeacher);
            differentRoom.setLayoutParams(layoutRoom);
            differentDuration.setLayoutParams(layoutDuration);
            differentInfo.setLayoutParams(layoutInfo);

            currentDate.setText(getItem(position).getDate());
            differentRoom.setText(getItem(position).getRoom());
            differentDuration.setText(getItem(position).getDuration());
            differentTeacher.setText(getItem(position).getTeacher());
            differentInfo.setText(getItem(position).getInfo());

            currentDate.setFontFeatureSettings(date.getFontFeatureSettings());
            differentRoom.setFontFeatureSettings(room.getFontFeatureSettings());
            differentDuration.setFontFeatureSettings(duration.getFontFeatureSettings());
            differentTeacher.setFontFeatureSettings(teacher.getFontFeatureSettings());
            differentInfo.setFontFeatureSettings(info.getFontFeatureSettings());

            //left top right bottom
            currentDate.setPadding(date.getTotalPaddingLeft(), date.getTotalPaddingTop(), date.getTotalPaddingRight(), date.getTotalPaddingBottom());
            differentRoom.setPadding(room.getTotalPaddingLeft(), room.getTotalPaddingTop(), room.getTotalPaddingRight(), room.getTotalPaddingBottom());
            differentDuration.setPadding(duration.getTotalPaddingLeft(), duration.getTotalPaddingTop(), duration.getTotalPaddingRight(), duration.getTotalPaddingBottom());
            differentTeacher.setPadding(teacher.getTotalPaddingLeft(), teacher.getTotalPaddingTop(), teacher.getTotalPaddingRight(), teacher.getTotalPaddingBottom());
            differentInfo.setPadding(info.getTotalPaddingLeft(), info.getTotalPaddingTop(), info.getTotalPaddingRight(), info.getTotalPaddingBottom());

            this.layout.addView(currentDate);
            this.layout.addView(differentRoom);
            this.layout.addView(differentDuration);
            this.layout.addView(differentTeacher);
            this.layout.addView(differentInfo);

        }else{
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

        }

        convertView.setBackground(getContext().getResources().getDrawable(R.drawable.rectangle));

        return convertView;
    }

}
