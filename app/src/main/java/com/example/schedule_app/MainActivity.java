package com.example.schedule_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity{
    ArrayList<Schedule> classes;
    ListView listView;
    Document document;
    ScheduleAdapter adapter;
    TextView editText;
    Button button;
    String website;
    ExecutorService executor;
    Data data;
    ProgressBar bar;
    public final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        data =  Data.getInstance(getApplicationContext(), "UserData");

        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        data =  Data.getInstance(getApplicationContext(), "UserData");

        executor = Executors.newSingleThreadExecutor();
        executeSystem();
    }

    public void executeSystem(){
        if(data.getScheduleLink() == null || data.getScheduleLink().length() == 0){
            setContentView(R.layout.enter_website);
            editText = findViewById(R.id.EditText12);
            button = findViewById(R.id.buttonf);

            button.setOnClickListener(View ->{
                if(editText.getText().toString().contains("schema.hkr.se")){
                    website = editText.getText().toString();
                    executor.execute(() -> {
                        runOnUiThread(() ->{
                            bar = findViewById(R.id.loader);
                            bar.setVisibility(android.view.View.VISIBLE);

                        });
                        sortElements(website);
                        runOnUiThread(()->{
                            setAdapter(data.getEnglishSetting());
                            setUpFab(data.getEnglishSetting());
                        });
                    });
                }
            });

        }else{
            setContentView(R.layout.activity_main);
            executor.execute(() -> {
                sortElements(data.getScheduleLink());
                runOnUiThread(() ->{
                    setAdapter(data.getEnglishSetting());
                    setUpFab(data.getEnglishSetting());
                });
            });
        }


    }

    public void sortElements(String url) {
        classes = new ArrayList<>();

        try {
            document = Jsoup.connect(url).get();
            data.putScheduleLinkString(website);
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
        setContentView(R.layout.activity_main);
        adapter = new ScheduleAdapter(MainActivity.this, classes, englishSetting);
        listView = findViewById(R.id.ListView);
        listView.setAdapter(adapter);
    }

    public void setUpFab(Boolean isEnglish){
        FloatingActionButton fab = findViewById(R.id.FabInSchedule);
        fab.bringToFront();
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        });


    }

}