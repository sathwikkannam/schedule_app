package com.example.schedule_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {
    FloatingActionButton backToMainActivity;
    Button changeSchedule;
    Data data;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch englishSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        changeSchedule = findViewById(R.id.ChangeScheduleInSettings);
        englishSwitch = findViewById(R.id.EnglishSwitchInSettings);
        backToMainActivity = findViewById(R.id.BackInSettings);
        data = Data.getInstance(getApplicationContext(), "UserData");

        // set the switch to same setting as previous.
        englishSwitch.setChecked(data.getEnglishSetting());


        backToMainActivity.setOnClickListener(view ->{
            data.putEnglishSetting(englishSwitch.isChecked());
            intentToMain();
        });

        changeSchedule.setOnClickListener(view ->{
            data.removeDefaultScheduleLink();
            intentToHome();

        });

    }

    public void intentToMain(){
        Intent out = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(out);
    }

    public void intentToHome(){
        Intent out = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(out);
    }



}