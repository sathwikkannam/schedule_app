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
        translate.put("Programmering för inbyggda system", "Programming for embedded systems");
        translate.put("Databasteknik", "Database technology");
        translate.put("Objektorienterad programmering", "Object-oriented programming");
        translate.put("Analys och algebra", "Analysis and algebra");
        translate.put("Matematik för ingenjörer", "Mathematics for engineers");

    }

    public String getTranslated(String key){
        return (this.translate.get(key) != null)? this.translate.get(key): key;

    }


}
