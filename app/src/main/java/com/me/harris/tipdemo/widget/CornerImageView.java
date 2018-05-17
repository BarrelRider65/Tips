package com.me.harris.tipdemo.widget;

import android.content.Context;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class CornerImageView extends AppCompatImageView {

    int cornerRadius = 15;


    public CornerImageView(Context context) {
        super(context);
    }

    public CornerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        BitmapShader shader;


    }

    public CornerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    RoundedBitmapDrawable drawable;

    @Override
    public void draw(Canvas canvas) {

        super.draw(canvas);


    }
}
