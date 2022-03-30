package com.example.schedule_app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;


public class Background {
    private final Context context;
    private final Window window;
    private final boolean isLight;

    public Background(Context context, Activity activity){
        this.context = context;
        this.window = activity.getWindow();
        isLight = Data.getInstance(context).getTheme();

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void setLayoutBackground(ViewGroup layout, int drawable, int color){
        Drawable shape = this.context.getResources().getDrawable(drawable);
        shape.mutate().setTint(this.context.getResources().getColor(color));
        layout.setBackground(shape);

    }

    public void setLightMode(ViewGroup header, TextView headerText){
        if(this.isLight){
            headerText.setTextColor(this.context.getResources().getColor(R.color.black));
            header.setBackgroundColor(this.context.getResources().getColor(R.color.platinum));
            setStatusBarColor();
        }

    }


    public void setStatusBarColor(){
        this.window.setStatusBarColor(this.context.getResources().getColor(R.color.platinum));
    }

}
