package com.example.schedule_app;

import java.util.Hashtable;
public class Translation {
    private final Hashtable<String, String> translate;

    public Translation(){
        this.translate = new Hashtable<>();
        sortTranslateDays();

    }

    public void sortTranslateDays(){
        translate.put("Mån", "Mon");
        translate.put("Tis", "Tue");
        translate.put("Ons", "Wed");
        translate.put("Tor", "Thur");
        translate.put("Fre", "Fri");
        translate.put("Lör", "Sat");
        translate.put("Sön", "Sun");
        translate.put("Maj", "May");

    }

    public String getTranslated(String key){
        return (this.translate.get(key) != null)? this.translate.get(key): key;

    }


}
