package com.example.schedule_app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.schedule_app.Background;
import com.example.schedule_app.Data;
import com.example.schedule_app.Date;
import com.example.schedule_app.R;
import com.example.schedule_app.Schedule;
import com.example.schedule_app.WebScraper;
import com.example.schedule_app.adapter.TimeLineAdapter;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executors;

public class TimeLineActivity extends AppCompatActivity {
    Data data;
    TimeLineAdapter timeLineAdapter;
    ListView timeLineView;
    Button toSchedule, toSettings;
    Background background;
    Date deviceDate;
    LinearLayout navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        Objects.requireNonNull(getSupportActionBar()).hide();

        data = Data.getInstance(getApplicationContext());
        deviceDate = new Date();
        toSchedule = findViewById(R.id.toScheduleView);
        toSettings = findViewById(R.id.toSettings);
        timeLineView = findViewById(R.id.TimeLineListView);
        background = new Background(getApplicationContext(), this);
        navBar = findViewById(R.id.TimeLineNavBar);

        if(data.getLastStoredDate() != null && !data.getLastStoredDate().equals(deviceDate.getTodayDate()) && data.getStoredSchedule() !=null){
            Executors.newSingleThreadExecutor().execute(() ->{
                data.removeStoredSchedule();
                data.storeScheduleObjects(WebScraper.scrape(data.getScheduleURL(), data));

                runOnUiThread(()->{
                    data.putLastStoredDate(deviceDate.getTodayDate());
                    setUpAdapter();
                });
            });
        }else{
            setUpAdapter();
        }

        toSchedule.setOnClickListener(View -> startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("Navigate", true)));
        toSettings.setOnClickListener(View -> startActivity(new Intent(getApplicationContext(), SettingsActivity.class)));

    }

    public void setUpAdapter(){
        timeLineAdapter = new TimeLineAdapter(this, data.getStoredSchedule());
        timeLineView.setAdapter(timeLineAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeLineAdapter.getTranslator().closeTranslationModel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        data.onOpenLayout(this.getClass().getSimpleName());
        //timeLineAdapter.getTranslator().closeTranslationModel();
    }
}
