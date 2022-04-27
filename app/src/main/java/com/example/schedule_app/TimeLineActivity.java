package com.example.schedule_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.schedule_app.adapter.TimeLineAdapter;

import java.util.Objects;

public class TimeLineActivity extends AppCompatActivity {
    Data data;
    TimeLineAdapter timeLineAdapter;
    ListView timeLineView;
    Button toSchedule, toSettings;
    Background background;
    Date deviceDate;

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

        if(data.getLastStoredDate() != null && !data.getLastStoredDate().equals(deviceDate.getTodayDate()) && data.getStoredSchedule() !=null){
            data.storeScheduleObjects(WebScraper.scrape(data.getScheduleURL()));
            data.putLastStoredDate(deviceDate.getTodayDate());
        }

        timeLineAdapter = new TimeLineAdapter(this, data.getStoredSchedule());
        timeLineView.setAdapter(timeLineAdapter);

        toSchedule.setOnClickListener(View -> startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("Navigate", true)));
        toSettings.setOnClickListener(View -> startActivity(new Intent(getApplicationContext(), SettingsActivity.class)));

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
        timeLineAdapter.getTranslator().closeTranslationModel();
    }
}
