package com.me.harris.tipdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.me.harris.tipdemo.R;

public class RotateGradientTextView extends AppCompatTextView {
    public RotateGradientTextView(Context context) {
        super(context);
    }

    public RotateGradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RotateGradientTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    Drawable mDrawable;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.rotate(45f);
        if (mDrawable==null){
            mDrawable = ContextCompat.getDrawable(getContext(), R.drawable.bg_candy_box_signed);
            mDrawable.setBounds(getLeft(),getTop(),getRight(),getBottom());
        }
        mDrawable.draw(canvas);
        canvas.restore();

    }
}
