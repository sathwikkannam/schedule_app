package com.example.schedule_app.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.schedule_app.Data;
import com.example.schedule_app.R;
import com.example.schedule_app.Schedule;

import java.util.ArrayList;

public class WidgetFactory implements RemoteViewsService.RemoteViewsFactory {
    private final Context context;
    private final int appWidgetId;
    private final ArrayList<Schedule> schedule;
    private final boolean englishSetting;

    public WidgetFactory(Context context, Intent intent){
        this.context = context;
        this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        Data data = Data.getInstance(this.context);
        this.englishSetting = data.getEnglishSetting();
        this.schedule = data.getStoredSchedule();
    }

    @Override
    public void onCreate() {


    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return schedule.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        // adapt
        RemoteViews views =  new RemoteViews(this.context.getPackageName(), R.layout.widget_item);
        views.setTextViewText(R.id.WidgetCourse, this.schedule.get(position).getCourse());
        views.setTextViewText(R.id.WidgetDate, this.schedule.get(position).getDate());
        views.setTextViewText(R.id.WidgetDuration, this.schedule.get(position).getDuration());
        views.setTextViewText(R.id.WidgetRoom, this.schedule.get(position).getRoom());

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
