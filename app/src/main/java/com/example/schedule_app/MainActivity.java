package com.example.schedule_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ArrayList<Schedule> classes = new ArrayList<>();
    ListView listView;
    Document document;
    ScheduleAdapter adapter;
    TextView editText;
    Button button;
    String website;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        setContentView(R.layout.enter_website);
        editText = findViewById(R.id.EditText12);
        button = findViewById(R.id.buttonf);
        status = findViewById(R.id.status);

        button.setOnClickListener(View ->{
            if(editText.getText().toString().contains("schema.hkr.se")){
                status.setText("Success");
                website = editText.getText().toString();
                executor.execute(() -> {
                    sortElements(website);
                    runOnUiThread(this::setAdapter);

                });
            }else{
                status.setText("Error!");
            }
        });


    }


    public void sortElements(String url) {
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Element elem : document.select("table.schemaTabell tr")) {
            String week = elem.select(".data.vecka").text();
            String day = elem.select("td.data.commonCell:nth-of-type(2)").text();
            String date = elem.select("td.data.commonCell:nth-of-type(3)").text();
            String time = elem.select("td.data.commonCell:nth-of-type(4)").text();
            String course = elem.select("td.commonCell:nth-of-type(5)").text();
            String teacher = elem.select("td.commonCell:nth-of-type(6)").text();
            String room = elem.select("td.commonCell:nth-of-type(7)").text();
            String info = elem.select("td.data.commonCell:nth-of-type(9)").text();

            // Basically the 11 is the length of Time String eg: 10:00-15:00. So if there is a time, there is a class.
            if(time.length() >= 11){
                classes.add(new Schedule(week, day, date, time, course, teacher, room, info));
            }

        }

        for (int i = 0; i < classes.size(); i++) {
            if(classes.get(i).getDate().length() == 0 && classes.get(i).getDay().length() == 0){
                for (int j = i-1; j >=0; j--) {
                    if(classes.get(j).getDate().length() != 0 && classes.get(j).getDay().length() != 0){
                        classes.get(i).setDay(classes.get(j).getDay());
                        classes.get(i).setDate(classes.get(j).getDate());
                        break;
                    }
                }
            }
        }

    }


    public void setAdapter(){
        setContentView(R.layout.activity_main);
        adapter = new ScheduleAdapter(MainActivity.this, classes);
        listView = findViewById(R.id.ListView);
        listView.setAdapter(adapter);

    }


}