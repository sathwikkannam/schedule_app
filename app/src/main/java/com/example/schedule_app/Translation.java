package com.example.schedule_app;

import java.util.Hashtable;
public class Translation {
    private final Hashtable<String, String> translate;

    public Translation(){
        this.translate = new Hashtable<>();
        translateElements();

    }

    public void translateElements(){
        translate.put("Mån", "Mon");
        translate.put("Tis", "Tue");
        translate.put("Ons", "Wed");
        translate.put("Tor", "Thu");
        translate.put("Fre", "Fri");
        translate.put("Lör", "Sat");
        translate.put("Sön", "Sun");
        translate.put("Maj", "May");
    }

    public String getTranslated(String key){
        return (this.translate.get(key) != null)? this.translate.get(key): key;

    }


}
