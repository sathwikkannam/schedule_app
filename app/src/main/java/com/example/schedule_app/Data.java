package com.example.schedule_app;

import android.content.Context;
import android.content.SharedPreferences;


public class Data {
    private final SharedPreferences.Editor writer;
    private final SharedPreferences getPreferencesReader;
    private final String name;
    private final String defaultScheduleLinkKey;
    private final String defaultEnglishSettingKey;
    private static Data data = null;


    //singleton Data object. Big Brain alternative for passing Data object between activites.
    private Data(Context context, String name) {
        this.name =  name;
        this.defaultScheduleLinkKey = "ScheduleLink";
        this.defaultEnglishSettingKey = "EnglishSetting";
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

}