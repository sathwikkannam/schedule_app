package com.example.schedule_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;


import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {
    Button changeSchedule;
    Data data;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch englishSwitch;
    Switch mode;
    Intent out;
    Background background;
    boolean isLight;
    LinearLayout settingsHeader;
    TextView headerText;
    RelativeLayout innerSettingsLayout;
    Button toTimeLine, toSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        //initialize variables
        changeSchedule = findViewById(R.id.ChangeScheduleInSettings);
        englishSwitch = findViewById(R.id.EnglishSwitchInSettings);
        mode = findViewById(R.id.ThemeSwitchInSettings);
        settingsHeader = findViewById(R.id.SettingsHeader);
        headerText = findViewById(R.id.SettingsText);
        toTimeLine = findViewById(R.id.toTimeline);
        toSchedule = findViewById(R.id.toSchedule);
        innerSettingsLayout = findViewById(R.id.FragmentSettings);
        data = Data.getInstance(getApplicationContext());
        background = new Background(getApplicationContext(), this);
        isLight = data.getTheme();

        //set background for the settings fragment.
        background.setLayoutBackground(innerSettingsLayout, R.drawable.upper_rectangle, R.color.light_black);

        background.setLightMode(settingsHeader, headerText);

        // set the switch to same setting as previous.
        englishSwitch.setChecked(data.getEnglishSetting());
        mode.setChecked(isLight);


        //set onclick for button to return to welcome activity.
        changeSchedule.setOnClickListener(view ->{
            data.removeDefaultScheduleLink();
            data.removeStoredSchedule();
            out = new Intent(getApplicationContext(), WelcomeActivity.class);
            startActivity(out);

        });

        toSchedule.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),
                MainActivity.class)));
        toTimeLine.setOnClickListener(View -> startActivity(new Intent(getApplicationContext(),
                TimeLineActivity.class)));




    }


}