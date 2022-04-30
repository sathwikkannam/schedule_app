package com.example.schedule_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.schedule_app.adapter.ScheduleAdapter;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ArrayList<Schedule> classes;
    RecyclerView scheduleList;
    ScheduleAdapter adapter;
    Data data;
    Date deviceDate;
    Button toSettings, toTimeLine;
    Background background;
    LinearLayout navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //initialize variables.
        data =  Data.getInstance(getApplicationContext());

        if(data.getScheduleURL() == null || data.getScheduleURL().length() == 0){
            startActivity(new Intent(this, WelcomeActivity.class));
        }else if(data.getOnOpenLayout().equals(TimeLineActivity.class.getSimpleName()) && !getIntent().getBooleanExtra("Navigate", false)){
            startActivity(new Intent(this, TimeLineActivity.class));
        }else{
            //initialize variables.
            deviceDate = new Date();
            background =  new Background(getApplicationContext(), this);

            setContentView(R.layout.schedule);
            //basically store the schedule once everyday, then it is offline for the rest of the day.
            if(data.getLastStoredDate() != null && data.getLastStoredDate().equals(deviceDate.getTodayDate()) && data.getStoredSchedule() !=null){
                classes = data.getStoredSchedule();
                setAdapter();
            }else{
                //first time of the day.
                Executors.newSingleThreadExecutor().execute(() -> {
                    classes = WebScraper.scrape(data.getScheduleURL());
                    Schedule.sortDates(classes);
                    data.putLastStoredDate(deviceDate.getTodayDate());
                    data.storeScheduleObjects(classes);

                    runOnUiThread(() ->{
                        setAdapter();
                        navBar = findViewById(R.id.NavBar);
                        navBar.startAnimation(AnimationUtils.loadAnimation(this, R.anim.navbar));
                    });
                });

            }
            Executors.newSingleThreadExecutor().execute(this::setNavBar);
        }

    }


    //set adapter for the listview in schedule.xml
    public void setAdapter(){
        adapter = new ScheduleAdapter(getApplicationContext(), classes, data, deviceDate);
        scheduleList = findViewById(R.id.ScheduleListView);
        scheduleList.setAdapter(adapter);

    }

    //create intents for the buttons in navBar
    public void setNavBar(){
        toSettings = findViewById(R.id.toSettings);
        toTimeLine = findViewById(R.id.toTimeline);
        toSettings.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),
                                                                        SettingsActivity.class)));
        toTimeLine.setOnClickListener(View -> startActivity(new Intent(getApplicationContext(),
                                                                        TimeLineActivity.class)));

    }

    @Override
    protected void onStop() {
        super.onStop();
        data.onOpenLayout(this.getClass().getSimpleName());
    }
}
