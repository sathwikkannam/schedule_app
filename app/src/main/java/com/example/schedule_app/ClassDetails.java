package com.example.schedule_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

public class ClassDetails extends AppCompatActivity {
    Schedule clickedItem;
    TextView details;
    String info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_class_details);

        clickedItem = Data.getInstance(getApplicationContext())
                .getStoredSchedule().get(getIntent().getExtras().getInt("POSITION"));

        details =  findViewById(R.id.Details);
        info = String.format("%s%s", clickedItem.getInfo().substring(0, 1).toUpperCase(), clickedItem.getInfo().substring(1));

        if(info.length() >= 120){
            details.setTextSize(25);
        }else{
            details.setTextSize(40);
        }
        details.setText(info);

    }
}