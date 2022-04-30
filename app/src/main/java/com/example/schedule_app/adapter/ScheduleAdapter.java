package com.example.schedule_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedule_app.Data;
import com.example.schedule_app.Date;
import com.example.schedule_app.DaysTranslation;
import com.example.schedule_app.FirebaseTranslator;
import com.example.schedule_app.adapter.interfaces.ItemOnClickListener;
import com.example.schedule_app.R;
import com.example.schedule_app.Schedule;
import com.example.schedule_app.Shape;
import com.google.mlkit.nl.translate.TranslateLanguage;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> implements Shape.BackgroundColor {

    private final ArrayList<Schedule> localDataSet;
    private final Data data;
    private final FirebaseTranslator firebaseTranslator;
    private final Date date;
    private final Shape shape;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements ItemOnClickListener {
        private final TextView date;
        private final TextView duration;
        private final TextView course;
        private final TextView teacher;
        private final TextView info;
        private final TextView room;
        private final View rowView;

        public ViewHolder(View view) {
            super(view);
            rowView = view;
            date = (TextView)  view.findViewById(R.id.Date);
            duration = (TextView)  view.findViewById(R.id.Duration);
            course = (TextView)  view.findViewById(R.id.Course);
            teacher = (TextView)  view.findViewById(R.id.Teacher);
            info = (TextView)  view.findViewById(R.id.Info);
            room = (TextView)  view.findViewById(R.id.Room);

            // Define click listener for the ViewHolder's View
            onClick(view);

        }

        public TextView getDate() {
            return date;
        }

        public TextView getDuration() {
            return duration;
        }

        public TextView getCourse() {
            return course;
        }

        public TextView getTeacher() {
            return teacher;
        }

        public TextView getInfo() {
            return info;
        }

        public TextView getRoom() {
            return room;
        }

        public View getRowView(){
            return rowView;
        }

        @Override
        public void onClick(View view) {

        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet ArrayList<Schedule> containing the data to populate views to be used
     * by RecyclerView.
     */
    public ScheduleAdapter (Context context, ArrayList<Schedule> dataSet, Data data, Date date) {
        this.localDataSet = dataSet;
        this.data = data;
        this.firebaseTranslator = FirebaseTranslator.getInstance(TranslateLanguage.SWEDISH, TranslateLanguage.ENGLISH);
        this.date = date;
        this.shape = new Shape(context);
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.schedule_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Schedule schedule = localDataSet.get(position);

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        if(data.getEnglishSetting()){
            Executors.newSingleThreadExecutor().execute(() ->this.firebaseTranslator.translate(viewHolder.getInfo(), schedule.getInfo()));
            viewHolder.getDate().setText(String.format("%s %s", schedule.getFullDate(), DaysTranslation.getInstance().getTranslated(schedule.getDay())));
        }else{
            viewHolder.getDate().setText(String.format("%s %s", schedule.getFullDate(), schedule.getDay()));
            viewHolder.getInfo().setText(schedule.getInfo());
        }

        viewHolder.getDuration().setText(schedule.getDuration());
        CommonTextView.setText(schedule, viewHolder.getCourse(), viewHolder.getTeacher(), viewHolder.getRoom());
        setBackgroundColor(viewHolder.getRowView(), position, this.date, viewHolder.getDate());
    }

    @Override
    public void setBackgroundColor(View view, int position, Date date, TextView dateView) {
        String currentDate;
        String previousDate;
        String nextBlockDate;
        String blockDate = dateView.getText().toString().substring(0, dateView.getText().length()-4);
        if(blockDate.equals(date.getTodayDate())){
            shape.toRed();
        }if(blockDate.equals(date.getTomorrowDate())){
            shape.toGrey();
        }

        //upper, lower, middle, blue
        if(position < getItemCount()-1) {
            currentDate = localDataSet.get(position).getFullDate();
            previousDate = (position-1 < 0)? null: localDataSet.get(position-1).getFullDate();
            nextBlockDate = localDataSet.get(position + 1).getFullDate();

            if (!currentDate.equals(previousDate) && currentDate.equals(nextBlockDate)) {
                view.setBackground(shape.getShape("upper"));
            } else if (!currentDate.equals(nextBlockDate) && currentDate.equals(previousDate)) {
                view.setBackground(shape.getShape("lower"));
            } else if (currentDate.equals(nextBlockDate)) {
                view.setBackground(shape.getShape("middle"));
            }else{
                view.setBackground(shape.getShape("regular"));
            }
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }


}