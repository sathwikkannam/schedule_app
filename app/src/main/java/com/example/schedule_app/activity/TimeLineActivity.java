package com.example.schedule_app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.schedule_app.Background;
import com.example.schedule_app.Data;
import com.example.schedule_app.Date;
import com.example.schedule_app.R;
import com.example.schedule_app.WebScraper;
import com.example.schedule_app.adapter.TimeLineAdapter;

import java.util.Objects;
import java.util.concurrent.Executors;

public class TimeLineActivity extends AppCompatActivity {
    Data data;
    TimeLineAdapter timeLineAdapter;
    ListView timeLineView;
    LinearLayout toSchedule, toSettings;
    Background background;
    Date deviceDate;
    LinearLayout navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide the status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_time_line);

        //Hide action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        //Initialize variables
        data = Data.getInstance(getApplicationContext());
        deviceDate = new Date();
        toSchedule = findViewById(R.id.toScheduleView);
        toSettings = findViewById(R.id.toSettings);
        timeLineView = findViewById(R.id.TimeLineListView);
        background = new Background(getApplicationContext(), this);
        navBar = findViewById(R.id.TimeLineNavBar);

        //Similar logic to MainActivity
        if(data.getLastStoredDate() != null && !data.getLastStoredDate().equals(deviceDate.getTodayDate()) && data.getStoredSchedule() !=null){
            Executors.newSingleThreadExecutor().execute(() ->{
                data.removeStoredSchedule();
                data.storeScheduleObjects(WebScraper.scrape(data.getScheduleURL(), data));

                runOnUiThread(()->{
                    data.putLastStoredDate(deviceDate.getTodayDate());
                    setUpAdapter();
                });
            });
        }else{
            setUpAdapter();
        }

        //Set intents for the corresponding button.
        toSchedule.setOnClickListener(View -> startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("Navigate", true)));
        toSettings.setOnClickListener(View -> startActivity(new Intent(getApplicationContext(), SettingsActivity.class)));

        timeLineView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private final ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) timeLineView.getLayoutParams();
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE &&
                        (timeLineView.getLastVisiblePosition() - timeLineView.getHeaderViewsCount() - timeLineView.getFooterViewsCount()) >= (timeLineView.getAdapter().getCount() - 1)) {
                    //Reached bottom.

                    timeLineView.smoothScrollToPosition(timeLineView.getAdapter().getCount() - 1 );
                    timeLineView.setSelection(timeLineView.getAdapter().getCount());
                    mlp.setMargins(0, 0, 0, 400);
                }else{
                    timeLineView.clearFocus();
                    mlp.setMargins(0, 0, 0, 0);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    public void setUpAdapter(){
        timeLineAdapter = new TimeLineAdapter(this, data.getStoredSchedule());
        timeLineView.setAdapter(timeLineAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeLineAdapter.getTranslator().closeTranslationModel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        data.onOpenLayout(this.getClass().getSimpleName());
    }
}
