package com.example.schedule_app;

import android.content.Context;
import android.content.SharedPreferences;


public class Data {
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
        this.writer.putString(key, value);
        this.writer.apply();
    }

    public String get(String key){
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

    public String getDefaultValue(){
        return this.getPreferencesReader.getString(this.defaultKey, "");
    }

    public void putForDefaultKey(String value){
        if(isEmpty()){
            this.writer.putString(this.defaultKey, value);
            this.writer.apply();
        }

    }

}