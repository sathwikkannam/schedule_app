package com.example.schedule_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class Shape {
    private final ArrayList<Drawable> rectangles;
    private final Context context;

    public Shape(Context context){
        this.context = context;
        this.rectangles =  new ArrayList<>();
        addRectangles();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void addRectangles(){
        rectangles.add(this.context.getDrawable(R.drawable.upper_rectangle));
        rectangles.add(this.context.getDrawable(R.drawable.lower_rectangle));
        rectangles.add(this.context.getDrawable(R.drawable.middle_rectangle));
        rectangles.add(this.context.getDrawable(R.drawable.blue_rectangle));
    }

    public void toRed(){
        this.rectangles.forEach(rectangle -> rectangle.mutate().setTint(this.context.getColor(R.color.red)));

    }

    public void toGrey(){
        this.rectangles.forEach(rectangle -> rectangle.mutate().setTint(this.context.getColor(R.color.grey)));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public Drawable getShape(String name){
        Drawable sendShape = null;
        if(name.contains("upper")){
            sendShape = rectangles.get(0);
        }else if(name.contains("lower")){
            sendShape =  rectangles.get(1);
        }else if (name.contains("middle")){
            sendShape = rectangles.get(2);
        }else if(name.contains("regular")) {
            sendShape = rectangles.get(3);
        }

        return sendShape;
    }



}
