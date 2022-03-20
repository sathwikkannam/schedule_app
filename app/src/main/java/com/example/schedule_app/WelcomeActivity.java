package com.example.schedule_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WelcomeActivity extends AppCompatActivity{
    Button button;
    TextView websiteInput;
    String website;
    ExecutorService executor;
    ProgressBar bar;
    Data data;
    Background background;
    String domain = "schema.hkr.se";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.welcome);

        //initialize variables
        data = Data.getInstance(getApplicationContext());
        bar =  findViewById(R.id.loader);
        executor = Executors.newSingleThreadExecutor();
        button = findViewById(R.id.EnterButtonInHome);
        websiteInput = findViewById(R.id.WebsiteInputInHome);
        background = new Background(getApplicationContext());

        //set background for welcome fragment.
        RelativeLayout fragmentLayout = findViewById(R.id.FragmentWelcome);
        background.setFragmentBackground(fragmentLayout, R.drawable.upper_rectangle, R.color.light_black);

        // set background for the button
        Drawable rectangle = getResources().getDrawable(R.drawable.rectangle);
        rectangle.mutate().setTint(getResources().getColor(R.color.red));
        button.setBackground(rectangle);


        //On click for the button
        button.setOnClickListener(view -> {
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
            data.putScheduleLinkString(website);
            bar = findViewById(R.id.loader);
            button.setVisibility(android.view.View.INVISIBLE);
            websiteInput.setVisibility(android.view.View.INVISIBLE);
            bar.setVisibility(android.view.View.VISIBLE);
        }));

    }

}