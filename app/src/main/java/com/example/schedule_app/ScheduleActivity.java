package com.example.schedule_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScheduleActivity extends AppCompatActivity {
    ArrayList<Schedule> classes;
    ListView listView;
    Document document;
    ScheduleAdapter adapter;
    ExecutorService executor;
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.schedule);
        data =  Data.getInstance(getApplicationContext(), "UserData");

        executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            sortElements(data.getScheduleLink());
            runOnUiThread(() ->{
                setAdapter(data.getEnglishSetting());
                setUpFab();
            });
        });

    }


    public void sortElements(String url) {
        classes = new ArrayList<>();

        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
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
                classes.add(new Schedule(week, day, date, time, course, teacher, room, info));
            }

        }
        // for Schedule objects, if there are multiple classes a day.
        for (int i = 0; i < classes.size(); i++) {
            if(classes.get(i).getDate().length() == 0 && classes.get(i).getDay().length() == 0){
                for (int j = i-1; j >=0; j--) {
                    if(classes.get(j).getDate().length() != 0 && classes.get(j).getDay().length() != 0){
                        classes.get(i).setDay(classes.get(j).getDay());
                        classes.get(i).setDate(classes.get(j).getDate());
                        break;
                    }
                }
            }
        }

    }

    public void setAdapter(Boolean englishSetting){
        adapter = new ScheduleAdapter(this, classes, englishSetting);
        listView = findViewById(R.id.ListView);
        listView.setAdapter(adapter);
    }

    public void setUpFab(){
        FloatingActionButton fab = findViewById(R.id.FabInSchedule);
        fab.bringToFront();
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        });

    }

}