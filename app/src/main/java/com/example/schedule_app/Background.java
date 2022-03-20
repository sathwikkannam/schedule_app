package com.example.schedule_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.RelativeLayout;

public class Background {
    private final Context context;

    public Background(Context context){
        this.context = context;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void setRelativeLayoutBackground(RelativeLayout relativeLayout, int drawable, int color){
        Drawable shape = this.context.getResources().getDrawable(drawable);
        shape.mutate().setTint(this.context.getResources().getColor(color));
        relativeLayout.setBackground(shape);

    }


}
