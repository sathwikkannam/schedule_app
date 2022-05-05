package com.example.schedule_app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.schedule_app.Background;
import com.example.schedule_app.Data;
import com.example.schedule_app.R;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WelcomeActivity extends AppCompatActivity{
    ImageButton toMain;
    TextView websiteInput;
    String website;
    ExecutorService executor;
    ProgressBar bar;
    Data data;
    Background background;
    String domain = "schema.hkr.se";
    LinearLayout welcomeHeader;
    TextView headerText;
    boolean isLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.welcome);

        //initialize variables
        bar =  findViewById(R.id.loader);
        toMain = findViewById(R.id.EnterButtonInHome);
        websiteInput = findViewById(R.id.WebsiteInputInHome);
        welcomeHeader = findViewById(R.id.WelcomeHeader);
        headerText = findViewById(R.id.WelcomeText);
        background = new Background(getApplicationContext(), this);
        data = Data.getInstance(getApplicationContext());
        executor = Executors.newSingleThreadExecutor();
        isLight = data.getTheme();

        //set background for welcome fragment.
        RelativeLayout fragmentLayout = findViewById(R.id.FragmentWelcome);
        background.setLayoutBackground(fragmentLayout, R.drawable.upper_rectangle, R.color.light_black);


        //set Header color
        background.setLightMode(welcomeHeader, headerText);


        //On click for the button
        toMain.setOnClickListener(view -> {
            if (websiteInput.getText().toString().contains(domain)) {
                website = websiteInput.getText().toString();
                setVisibility(website);
                Intent toSchedule = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(toSchedule);
            }
        });

    }


    // set everything in the welcome screen invisible except the progressbar
    public void setVisibility(String website){
        executor.execute(() -> runOnUiThread(() ->{
            data.putScheduleURL(website);
            bar = findViewById(R.id.loader);
            toMain.setVisibility(android.view.View.INVISIBLE);
            websiteInput.setVisibility(android.view.View.INVISIBLE);
            bar.setVisibility(android.view.View.VISIBLE);
        }));

    }

}