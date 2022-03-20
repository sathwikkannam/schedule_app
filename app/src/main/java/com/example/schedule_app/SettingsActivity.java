package com.example.schedule_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {
    FloatingActionButton backToMainActivity;
    Button changeSchedule;
    Data data;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch englishSwitch;
    Intent out;
    Background background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        //initialize variables
        changeSchedule = findViewById(R.id.ChangeScheduleInSettings);
        englishSwitch = findViewById(R.id.EnglishSwitchInSettings);
        backToMainActivity = findViewById(R.id.BackInSettings);
        data = Data.getInstance(getApplicationContext());
        background = new Background(getApplicationContext());

        //set background for the settings fragment.
        RelativeLayout fragmentLayout = findViewById(R.id.FragmentSettings);
        background.setRelativeLayoutBackground(fragmentLayout, R.drawable.upper_rectangle, R.color.light_black);

        // set the switch to same setting as previous.
        englishSwitch.setChecked(data.getEnglishSetting());

        // set onclick for button to return to schedule activity.
        backToMainActivity.setOnClickListener(view ->{
            data.putEnglishSetting(englishSwitch.isChecked());
            out = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(out);
        });

        //set onclick for button to return to welcome activity.
        changeSchedule.setOnClickListener(view ->{
            data.removeDefaultScheduleLink();
            data.removeStoredSchedule();
            out = new Intent(getApplicationContext(), WelcomeActivity.class);
            startActivity(out);

        });
    }


}