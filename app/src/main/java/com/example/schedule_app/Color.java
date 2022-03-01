package com.example.schedule_app;

public class Color {
    private final int red;
    private final int kindaBlue;

    public Color (){
        red = android.graphics.Color.parseColor("#D56170");
        kindaBlue = android.graphics.Color.parseColor("#46A2B7");
    }

    public int getRed(){
        return this.red;
    }

    public int getKindaBlue(){
        return this.kindaBlue;
    }
}
