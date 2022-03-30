package com.example.schedule_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ArrayList<Schedule> classes;
    ListView scheduleList;
    Document document;
    ScheduleAdapter adapter;
    Data data;
    Date deviceDate;
    FloatingActionButton fab, toTimeline;
    RelativeLayout scheduleBackground;
    Background background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();

        data =  Data.getInstance(getApplicationContext());
        deviceDate = new Date("d MMM");
        background =  new Background(getApplicationContext(), this);


        if(data.getScheduleLink() == null || data.getScheduleLink().length() == 0){
            startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));

        }else{
            setContentView(R.layout.schedule);
            //basically store the schedule once everyday, then it is offline for the rest of the day.
            if(data.getLastStoredDate() != null && data.getLastStoredDate().equals(deviceDate.getTodayDate()) && data.getStoredSchedule() !=null){
                classes = data.getStoredSchedule();
                setAdapter();
            }else{
                Executors.newSingleThreadExecutor().execute(() -> {
                    sortElements(data.getScheduleLink());
                    runOnUiThread(this::setAdapter);
                });

            }
        }

        Executors.newSingleThreadExecutor().execute(()->{
            setUpFab();
            scheduleBackground = findViewById(R.id.ListViewLayout);
            runOnUiThread(()-> fab.setVisibility(View.VISIBLE));

        });


    }

    public void sortElements(String url){
        classes = new ArrayList<>();

        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            if(data.getStoredSchedule() != null){
                classes = data.getStoredSchedule();
            }
            e.printStackTrace();
        }
        for (Element elem : document.select("table.schemaTabell tr")) {
            String week = elem.select(".data.vecka").text();
            String day = elem.select("td.data.commonCell:nth-of-type(2)").text();
            String date = elem.select("td.data.commonCell:nth-of-type(3)").text();
            String time = elem.select("td.data.commonCell:nth-of-type(4)").text();
            String course = elem.select("td.commonCell:nth-of-type(5)").text();
            String teacher = elem.select("td.commonCell:nth-of-type(6)").text();
            String room = elem.select("td.commonCell:nth-of-type(7)").text();
            String info = elem.select("td.data.commonCell:nth-of-type(9)").text();

            if(time.length() >= 11){
                if(date.contains(" ")){
                    String[] splitDate = date.split(" ");
                    classes.add(new Schedule(week, day, splitDate[0], splitDate[1], time, course, teacher, room, info));
                }else{
                    classes.add(new Schedule(week, day, date, date, time, course, teacher, room, info));
                }

            }

        }
        Schedule.sortDates(classes);
        data.putLastStoredDate(deviceDate.getTodayDate());
        data.storeScheduleObjects(classes);

    }

    public void setAdapter(){
        adapter = new ScheduleAdapter(this, classes, data);
        scheduleList = findViewById(R.id.ScheduleListView);
        scheduleList.setAdapter(adapter);

    }

    public void setUpFab(){
        fab = findViewById(R.id.FabInSchedule);
        toTimeline = findViewById(R.id.FabInTimeLine);
        fab.bringToFront();
        fab.setVisibility(View.INVISIBLE);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        });

        toTimeline.setOnClickListener(View -> startActivity(new Intent(getApplicationContext(),
                                                                        TimeLineActivity.class)));

    }

}
