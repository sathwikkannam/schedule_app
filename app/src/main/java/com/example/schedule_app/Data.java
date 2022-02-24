package com.example.schedule_app;

import android.content.Context;
import android.content.SharedPreferences;


public class Data{
    private final SharedPreferences.Editor writer;
    private final SharedPreferences getPreferencesReader;
    private final String name;
    private final String defaultKey;

    public Data(Context context, String name) {
        this.name =  name;
        this.defaultKey = "ScheduleLink";
        SharedPreferences preferencesWriter = context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
        this.getPreferencesReader = context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
        this.writer = preferencesWriter.edit();

    }

    public void put(String key, String value){
        this.writer.putString(key, value).apply();
    }

    public String getString(String key){
        return this.getPreferencesReader.getString(key, "");
    }

    public boolean isEmpty(){
        return this.getPreferencesReader.getAll().isEmpty();
    }

    public void clear(){
        this.writer.clear().apply();
    }

    public String getName(){
        return this.name;
    }

    public String getScheduleLink(){
        return this.getPreferencesReader.getString(this.defaultKey, "");
    }

    public void putScheduleLinkString(String value){
        if(getScheduleLink() == null || getScheduleLink().length() == 0){
            this.writer.putString(this.defaultKey, value).apply();

        }

    }

    public void remove(String key){
        this.writer.remove(key).apply();
    }

    public String getDefaultKey(){
        return this.defaultKey;
    }

    public void putBoolean(String key, boolean value){
        this.writer.putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key){
        return this.getPreferencesReader.getBoolean(key, false);
    }
}