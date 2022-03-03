package com.example.schedule_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.enter_website);

        data = Data.getInstance(getApplicationContext(), "UserData");
        bar =  findViewById(R.id.loader);
        executor = Executors.newSingleThreadExecutor();
        button = findViewById(R.id.EnterButtonInHome);
        websiteInput = findViewById(R.id.WebsiteInputInHome);

        button.setOnClickListener(view -> {
            if (websiteInput.getText().toString().contains("schema.hkr.se")) {
                website = websiteInput.getText().toString();
                setVisibility(website);
                Intent toSchedule = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(toSchedule);
            }
        });

    }

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