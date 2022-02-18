package com.example.schedule_app;

import java.util.Hashtable;

public class Translation {
    private final Hashtable<String, String> translateDays;

    public Translation(){
        this.translateDays = new Hashtable<>();
        sortTranslateDays();

    }

    public void sortTranslateDays(){
        translateDays.put("Mån", "Mon");
        translateDays.put("Tis", "Tue");
        translateDays.put("Ons", "Wed");
        translateDays.put("Tor", "Thur");
        translateDays.put("Fre", "Fri");
        translateDays.put("Lör", "Sat");
        translateDays.put("Sön", "Sun");

    }

    public String getTranslatedDay(String day){
        return this.translateDays.get(day);
    }




}
