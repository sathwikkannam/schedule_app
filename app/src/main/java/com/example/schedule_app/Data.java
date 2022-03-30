package com.example.schedule_app;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class Data {
    private final SharedPreferences.Editor writer;
    private final SharedPreferences reader;
    private final String name;
    private final String defaultScheduleLinkKey;
    private final String defaultEnglishSettingKey;
    private static Data data = null;
    private final String scheduleKey;
    private final String storedKey;
    private final String themeKey;
    private final Gson gson;


    //singleton Data object. Big Brain alternative for passing Data object between activites.
    private Data(Context context) {
        this.name =  "UserData";
        this.storedKey = "StoreDateKey";
        this.scheduleKey = "storedSchedule";
        this.defaultScheduleLinkKey = "ScheduleLink";
        this.defaultEnglishSettingKey = "EnglishSetting";
        this.themeKey = "theme";
        this.gson = new Gson();
        SharedPreferences preferencesWriter = context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
        this.reader = context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
        this.writer = preferencesWriter.edit();

    }

    public static Data getInstance(Context context){
        if(data == null){
            data = new Data(context);
        }
        return data;
    }


    public String getName(){
        return this.name;
    }

    public String getScheduleLink(){
        return this.reader.getString(this.defaultScheduleLinkKey, "");
    }

    public void putScheduleLinkString(String value){
        if(!getScheduleLink().equals(value)){
            this.writer.putString(this.defaultScheduleLinkKey, value).apply();
        }
    }

    public void removeDefaultScheduleLink(){
        this.writer.remove(this.defaultScheduleLinkKey).apply();
    }


    public boolean getEnglishSetting(){
        return this.reader.getBoolean(this.defaultEnglishSettingKey, false);
    }

    public void putEnglishSetting(Boolean setting){
        if(getEnglishSetting() != setting){
            this.writer.putBoolean(this.defaultEnglishSettingKey, setting).apply();
        }
    }

    public void putLastStoredDate(String todayDate){
        if(!getLastStoredDate().equals(todayDate)){
            this.writer.putString(this.storedKey, todayDate);
        }
    }

    public String getLastStoredDate(){
        return this.reader.getString(this.storedKey, "");
    }

    public void storeScheduleObjects(ArrayList<Schedule> classes){
        this.writer.putString(this.scheduleKey, gson.toJson(classes)).apply();
    }

    public ArrayList<Schedule> getStoredSchedule(){
        Type type = new TypeToken<ArrayList<Schedule>>() {}.getType();
        return gson.fromJson(this.reader.getString(this.scheduleKey, ""), type);
    }

    public void removeStoredSchedule(){
        this.writer.remove(this.scheduleKey).apply();
        this.writer.remove(this.storedKey).apply();
    }

    public void putTheme(boolean mode){
        if(getTheme() != mode){
            this.writer.putBoolean(this.themeKey, mode).apply();
        }
    }

    public boolean getTheme(){
        return this.reader.getBoolean(this.themeKey, false);
    }

}