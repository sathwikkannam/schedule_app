package com.example.schedule_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.schedule_app.adapter.TimeLineAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class TimeLineActivity extends AppCompatActivity {
    Data data;
    TimeLineAdapter timeLineAdapter;
    ListView timeLineView;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_time_line);

        data = Data.getInstance(getApplicationContext());
        fab = findViewById(R.id.FabInTimeLine);

        timeLineAdapter = new TimeLineAdapter(this, data.getStoredSchedule(), data);
        timeLineView = findViewById(R.id.TimeLineListView);
        timeLineView.setAdapter(timeLineAdapter);

        fab.setOnClickListener(View -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
    }
}