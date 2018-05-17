package com.me.harris.tipdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DialogTitle;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class MaxLineTextView extends AppCompatTextView {

    public MaxLineTextView(Context context) {
        super(context);
    }

    public MaxLineTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MaxLineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int maxLine = 8;
    Rect rect = new Rect();



    private static final String TAG = "MaxLineTextView";
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int superWidth = getMeasuredWidth();


        int lineCount = getLineCount();
        Log.d(TAG,"height is "+lineCount);
        if (lineCount>maxLine){
            int lienEndIndex = getLayout().getLineEnd(7);
            String newContent = getText().toString().substring(0,lienEndIndex-3)+"...";
            getLineBounds(maxLine-1,rect);
            setText(newContent);
            setMeasuredDimension(superWidth,rect.bottom+getPaddingTop()+getPaddingBottom());
        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
