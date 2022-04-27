package com.example.schedule_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {
    RelativeLayout changeSchedule;
    Data data;
    SwitchCompat englishSwitch, theme;
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
        changeSchedule = findViewById(R.id.ChangeSchedule);
        englishSwitch = findViewById(R.id.EnglishSwitchInSettings);
        theme = findViewById(R.id.ThemeSwitchInSettings);
        settingsHeader = findViewById(R.id.SettingsHeader);
        headerText = findViewById(R.id.SettingsText);
        toTimeLine = findViewById(R.id.toTimeline);
        toSchedule = findViewById(R.id.toSchedule);
        innerSettingsLayout = findViewById(R.id.FragmentSettings);
        data = Data.getInstance(this);
        background = new Background(this, this);
        isLight = data.getTheme();

        //set background for the settings fragment.
        background.setLayoutBackground(innerSettingsLayout, R.drawable.upper_rectangle, R.color.light_black);
        background.setLightMode(settingsHeader, headerText);

        // set the switch to same setting as previous.
        englishSwitch.setChecked(data.getEnglishSetting());
        theme.setChecked(isLight);

        if(englishSwitch.isChecked() && !data.getScheduleURL().contains("sprak=" + "EN")){
            data.putScheduleURL(changeURLLang(data.getScheduleURL(), "EN"));

        }else if(!englishSwitch.isChecked() && !data.getScheduleURL().contains("sprak=" + "SV")){
            data.putScheduleURL(changeURLLang(data.getScheduleURL(), "SV"));
        }


        //set onclick for button to return to welcome activity.
        changeSchedule.setOnClickListener(view ->{
            data.removeScheduleURL();
            data.removeStoredSchedule();
            data.removeOnOpenLayout();
            data.removeStoredSchedule();
            startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));

        });

        toSchedule.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("Navigate", true)));
        toTimeLine.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), TimeLineActivity.class)));

    }

    public String changeURLLang(String source, String to){
        to = to.toUpperCase();

        return source.replace(source.charAt(source.indexOf("sprak=") + 6), to.charAt(0)).
                      replace(source.charAt(source.indexOf("sprak=") + 7), to.charAt(1));
    }

    @Override
    protected void onStop() {
        super.onStop();
        data.putEnglishSetting(englishSwitch.isChecked());
        data.putTheme(theme.isChecked());

    }
}