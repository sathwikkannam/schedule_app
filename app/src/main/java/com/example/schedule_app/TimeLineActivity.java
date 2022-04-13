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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_time_line);

        data = Data.getInstance(getApplicationContext());
        toSchedule = findViewById(R.id.toScheduleView);
        toSettings = findViewById(R.id.toSettings);
        background = new Background(getApplicationContext(), this);


        timeLineAdapter = new TimeLineAdapter(this, data.getStoredSchedule(), data);
        timeLineView = findViewById(R.id.TimeLineListView);
        timeLineView.setAdapter(timeLineAdapter);

        toSchedule.setOnClickListener(View -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
        toSettings.setOnClickListener(View -> startActivity(new Intent(getApplicationContext(), SettingsActivity.class)));
    }
}