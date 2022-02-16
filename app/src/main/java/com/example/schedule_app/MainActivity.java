package com.example.schedule_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ArrayList<Schedule> classes = new ArrayList<>();
    ListView listView;
    Document document;
    ScheduleAdapter adapter;
    final String url = "https://schema.hkr.se/setup/jsp/Schema.jsp?startDatum=idag&intervallTyp=m&intervallAntal=6&sprak=SV&sokMedAND=true&forklaringar=true&resurser=p.TBIT2+2021+35+100+NML+en";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            sortElements();
            checkClasses();
            runOnUiThread(this::setAdapter);

        });
    }

    public void checkClasses(){
        for (int i = 0; i < classes.size(); i++) {
            if (classes.get(i).getDay().equals("") && classes.get(i).getDate().equals("")) {
                for (int j = i - 1; j >= 0; j--) {
                    if (!classes.get(j).getDay().equals("") && !classes.get(j).getDate().equals("")) {
                        classes.get(i).setDay(classes.get(j).getDay());
                        classes.get(i).setDate(classes.get(j).getDate());
                    }
                }

            }

        }
    }

    public void sortElements() {
        try {
            document = Jsoup.connect(url).get();
            for (Element elem : document.select("table.schemaTabell tr")) {
                String week = elem.select(".data.vecka").text();
                String day = elem.select("td.data.commonCell:nth-of-type(2)").text();
                String date = elem.select("td.data.commonCell:nth-of-type(3)").text();
                String time = elem.select("td.data.commonCell:nth-of-type(4)").text();
                String course = elem.select("td.commonCell:nth-of-type(5)").text();
                String teacher = elem.select("td.commonCell:nth-of-type(6)").text();
                String room = elem.select("td.commonCell:nth-of-type(7)").text();
                String info = elem.select("td.data.commonCell:nth-of-type(9)").text();

                classes.add(new Schedule(week, day, date, time, course, teacher, room, info));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAdapter(){
        adapter = new ScheduleAdapter(MainActivity.this, classes);
        listView = findViewById(R.id.ListView);
        listView.setAdapter(adapter);
    }
}