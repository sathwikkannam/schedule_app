package com.example.schedule_app;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class Data {
    private final SharedPreferences.Editor writer;
    private final SharedPreferences getPreferencesReader;
    private final String name;
    private final String defaultScheduleLinkKey;
    private final String defaultEnglishSettingKey;
    private static Data data = null;
    private final String scheduleKey;
    private final String storedKey;
    private final Gson gson;


    //singleton Data object. Big Brain alternative for passing Data object between activites.
    private Data(Context context, String name) {
        this.name =  name;
        this.storedKey = "StoreDateKey";
        this.scheduleKey = "storedSchedule";
        this.defaultScheduleLinkKey = "ScheduleLink";
        this.defaultEnglishSettingKey = "EnglishSetting";
        gson = new Gson();
        SharedPreferences preferencesWriter = context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
        this.getPreferencesReader = context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
        this.writer = preferencesWriter.edit();

    }

    public static Data getInstance(Context context, String name){
        if(data == null){
            data = new Data(context, name);
        }
        return data;
    }


    public String getName(){
        return this.name;
    }

    public String getScheduleLink(){
        return this.getPreferencesReader.getString(this.defaultScheduleLinkKey, "");
    }

    public void putScheduleLinkString(String value){
        if(getScheduleLink() == null || getScheduleLink().length() == 0){
            this.writer.putString(this.defaultScheduleLinkKey, value).apply();
        }
    }

    public void removeDefaultScheduleLink(){
        this.writer.remove(this.defaultScheduleLinkKey).apply();
    }


    public boolean getEnglishSetting(){
        return this.getPreferencesReader.getBoolean(this.defaultEnglishSettingKey, false);
    }

    public void putEnglishSetting(Boolean setting){
        this.writer.putBoolean(this.defaultEnglishSettingKey, setting).apply();
    }

    public void putLastStoredDate(String todayDate){
        this.writer.putString(this.storedKey, todayDate);
    }

    public String getLastStoredDate(){
        return this.getPreferencesReader.getString(this.storedKey, "");
    }

    public void storeScheduleObjects(ArrayList<Schedule> classes){
        this.writer.putString(this.scheduleKey, gson.toJson(classes)).apply();
    }

    public ArrayList<Schedule> getStoredSchedule(){
        Type type = new TypeToken<ArrayList<Schedule>>() {}.getType();
        return gson.fromJson(this.getPreferencesReader.getString(this.scheduleKey, ""), type);
    }

    public void removeStoredSchedule(){
        this.writer.remove(this.scheduleKey).apply();
        this.writer.remove(this.storedKey).apply();
    }

}