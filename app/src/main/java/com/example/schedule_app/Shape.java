package com.example.schedule_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class Shape {
    private final ArrayList<Drawable> rectangles;
    private final Context context;

    public interface BackgroundColor{
        void setBackgroundColor(View view, int position, Date date, TextView dateView);
    }

    public Shape(Context context){
        this.context = context;
        this.rectangles =  new ArrayList<>();
        addRectangles();
    }


    private void addRectangles(){
        rectangles.add(ContextCompat.getDrawable(context, R.drawable.upper_rectangle));
        rectangles.add(ContextCompat.getDrawable(context, R.drawable.lower_rectangle));
        rectangles.add(ContextCompat.getDrawable(context, R.drawable.middle_rectangle));
        rectangles.add(ContextCompat.getDrawable(context, R.drawable.blue_rectangle));
    }

    public void tintTo(final int color){
        this.rectangles.forEach(rectangle -> rectangle.getConstantState().newDrawable().setTint(this.context.getColor(color)));

    }

    public void reset(){
        this.rectangles.forEach(rectangle -> rectangle.setTint(this.context.getColor(R.color.blue)));
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
