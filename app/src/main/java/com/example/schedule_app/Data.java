package com.example.schedule_app;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.schedule_app.utilities.Key;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class Data extends Key {
    private final SharedPreferences.Editor writer;
    private final SharedPreferences reader;
    private static Data data = null;
    private final Gson gson;

    private Data(Context context) {
        this.gson = new Gson();
        SharedPreferences preferencesWriter = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE);
        this.reader = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE);
        this.writer = preferencesWriter.edit();

    }

    public static Data getInstance(Context context){
        if(data == null){
            data = new Data(context);
        }
        return data;
    }


    public String getName(){
        return DATA_NAME;
    }

    public String getScheduleURL(){
        return this.reader.getString(SCHEDULE_WEB_LINK, "");
    }

    public void putScheduleURL(String value){
        if(!getScheduleURL().equals(value)){
            this.writer.putString(SCHEDULE_WEB_LINK, value).apply();
        }
    }

    public void removeScheduleURL(){
        this.writer.remove(SCHEDULE_WEB_LINK).apply();
    }

    public boolean getEnglishSetting(){
        return this.reader.getBoolean(ENGLISH_SETTING, false);
    }

    public void putEnglishSetting(Boolean setting){
        if(getEnglishSetting() != setting){
            this.writer.putBoolean(ENGLISH_SETTING, setting).apply();
        }
    }

    public void putLastStoredDate(String todayDate){
        if(!getLastStoredDate().equals(todayDate)){
            this.writer.putString(STORED_SCHEDULE_DATE, todayDate);
        }
    }

    public String getLastStoredDate(){
        return this.reader.getString(STORED_SCHEDULE_DATE, "");
    }

    public void storeScheduleObjects(ArrayList<Schedule> classes){
        this.writer.putString(SCHEDULE_STORE, gson.toJson(classes)).apply();
    }

    public ArrayList<Schedule> getStoredSchedule(){
        Type type = new TypeToken<ArrayList<Schedule>>() {}.getType();
        return gson.fromJson(this.reader.getString(SCHEDULE_STORE, ""), type);
    }

    public void removeStoredSchedule(){
        this.writer.remove(SCHEDULE_STORE).apply();
        this.writer.remove(STORED_SCHEDULE_DATE).apply();
    }

    public void putTheme(boolean mode){
        if(getTheme() != mode){
            this.writer.putBoolean(THEME_KEY, mode).apply();
        }
    }

    public boolean getTheme(){
        return this.reader.getBoolean(THEME_KEY, false);
    }

    public void onOpenLayout(String layout){
        if(!getOnOpenLayout().equals(layout)){
            this.writer.putString(ON_OPEN_VIEW,layout).apply();
        }

    }

    public String getOnOpenLayout(){
        return this.reader.getString(ON_OPEN_VIEW, "");
    }


    public void removeOnOpenLayout(){
        this.writer.remove(ON_OPEN_VIEW).apply();
    }

}