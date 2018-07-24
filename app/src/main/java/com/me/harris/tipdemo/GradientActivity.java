package com.me.harris.tipdemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ReplacementSpan;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class GradientActivity extends AppCompatActivity {

    public static final String PLACE_HOLER = "交易开放 7月16日10:00AM(EDT)BitMart交易所开放交易，17日10:00AM(EDT)开放取款";

    TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradient_curved);
        mTextView = findViewById(R.id.text);
//        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(this,R.drawable.simple_rectangle);
//        drawable.setBounds(0,0,100,100);
//        ImageSpan imageSpan = new ImageSpan(drawable, DynamicDrawableSpan.ALIGN_BOTTOM);
////        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(ContextCompat.getColor(this,R.color.colorAccent));
//        SpannableString spannableString = new SpannableString(mTextView.getText());
//
//
//        spannableString.setSpan(imageSpan,0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        mTextView.setText(spannableString);
        doStuff();

    }



    private void doStuff(){
        mTextView = findViewById(R.id.text);
        String content = PLACE_HOLER;
        SpannableString spanString = new SpannableString(content);
        int start = 0;
        int end = 4;
        // 设定渐变色
        int[] colors = {
                Color.parseColor("#a4caf9"), Color.parseColor("#4c99f5")
        };

        spanString.setSpan(new RadiusGradientBackgroundSpan(colors,this),start,end,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTextView.setText(spanString);

    }


    public class RadiusGradientBackgroundSpan extends ReplacementSpan {

        private int mSize; //这个应该是横向的宽度

        private LayerDrawable mBgDrawable;

        Context mContext;

        int[] mColors;


        Paint mLinePaint;

        Path path;


        public RadiusGradientBackgroundSpan(int[] colorArray, Context context) {
            this.mColors = colorArray;
            this.mContext = context;

            mBgDrawable = (LayerDrawable) ContextCompat.getDrawable(mContext,R.drawable.skewed_drawable);

            mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mLinePaint.setColor(Color.BLACK);
        }



        @Override
        public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
//            int rowHeight = fm.bottom-fm.top;
            int rowHeight = (int) (paint.descent()-paint.ascent()+padding*2);
            mSize = (int) ((paint.measureText(text, start, end) )+rowHeight*0.3f);
            //mSize就是span的宽度，span有多宽，开发者可以在这里随便定义规则
            //我的规则：这里text传入的是SpannableString，start，end对应setSpan方法相关参数
            //可以根据传入起始截至位置获得截取文字的宽度，最后加上左右两个圆角的半径得到span宽度
            return mSize+5; //留一点空格
        }

        int padding = 6 ;

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
            int color = paint.getColor();//保存文字颜色
            float textSize = paint.getTextSize();
           float decedent = paint.descent();
           float ascent = paint.ascent();
            float offset = (float) 0.3f*(decedent-ascent+padding*2);

            paint.setAntiAlias(true);// 设置画笔的锯齿效果
            RectF oval = new RectF(x, y + paint.ascent(), x + mSize, y + paint.descent());
            //设置文字背景矩形，x为span其实左上角相对整个TextView的x值，y为span左上角相对整个View的y值。paint.ascent()获得文字上边缘，paint.descent()获得文字下边缘
//            Rect rec = new Rect((int)x,(int)(y+paint.ascent()),(int)(x+mSize),(int)(y+paint.descent()));
            mBgDrawable = new LayerDrawable(createDrawable());
            Rect rec = new Rect((int)x,(int)(y+paint.ascent()),(int)(x+mSize),(int)(y+paint.descent())+padding*2);
            mBgDrawable.setBounds(rec);


            Drawable d=ContextCompat.getDrawable(mContext,R.drawable.simple_rectangle);
            d.setBounds(rec);


            canvas.save();
            canvas.translate(offset,-padding);
            canvas.skew(-0.3f,0); // 第一个表示x方向上倾斜角度的tan值，第二个参数表示y方向上倾斜角度的tan值
            d.draw(canvas);
            canvas.restore();

//
//            if(path==null){
//                path = new Path();
//            }
//            // 画左上角的圆弧
//             path.reset();
//            canvas.save();
//            canvas.translate(0,-padding);
//
//
//
//            float horizontalOffset = (float) (Math.tan((30.0f/180.0f)*Math.PI)*oval.height());
//             path.moveTo(rec.left+horizontalOffset-mRadius,rec.top+radius);
//             path.quadTo(rec.left+horizontalOffset-mRadius,rec.top,rec.left+horizontalOffset+mRadius,rec.top);
//             path.moveTo(rec.left+horizontalOffset+mRadius,rec.top);
//             path.lineTo(rec.left+horizontalOffset-mRadius,rec.top);
//             path.lineTo(rec.left+horizontalOffset-mRadius,rec.top+radius);
//             path.close();
//             canvas.drawPath(path,mLinePaint);
//            canvas.restore();
//
//            path.moveTo(oval.left+horizontalOffset, oval.top);
//            path.lineTo(oval.left+horizontalOffset, (float) (oval.top)+radius);
//            path.arcTo(new RectF(oval.left+horizontalOffset,
//                            oval.top,
//                            oval.left+((float) radius) * 2.0f+horizontalOffset,
//                            oval.top+((float) radius) * 2.0f),
//                    180.0f, 90.0f, true);
//            path.lineTo((float) radius+horizontalOffset, oval.top);
//            path.lineTo(oval.left+horizontalOffset, oval.top);
//            path.close();
//            canvas.drawPath(path,mLinePaint);


            //画右下角的圆弧
//            path.reset();

//            path.moveTo(oval.right-horizontalOffset,oval.bottom+radius);
//            path.lineTo(oval.right-horizontalOffset,oval.bottom);
////            path.arcTo(new RectF(oval.right-horizontalOffset-radius,
////                    oval.bottom-radius,),0.f,90.0f,true);
//
//            path.lineTo(oval.right-horizontalOffset-radius,oval.bottom+radius);
//            path.lineTo(oval.right-horizontalOffset,oval.bottom+radius);
//            path.close();
//            canvas.drawPath(path,mLinePaint);





            paint.setColor(color);//恢复画笔的文字颜色
            paint.setColor(Color.WHITE);
            paint.setTextSize(0.8f*textSize);
            canvas.drawText(text, start, end, x+offset*1.3f , y, paint);//绘制文字


//             draw overlay
//            canvas.

            paint.setTextSize(textSize);
            paint.setColor(color);//恢复画笔的文字颜色
        }


        private Drawable[] createDrawable(){
            Drawable[] layers = new Drawable[3];
            RotateDrawable drawable1 = (RotateDrawable) ContextCompat.getDrawable(GradientActivity.this,R.drawable.rotate_left);
            RotateDrawable drawable2 = (RotateDrawable) ContextCompat.getDrawable(GradientActivity.this,R.drawable.rotate_right);
            GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                    mColors);
            layers[0] = gradientDrawable;
            layers[1] = drawable1;
            layers[2] = drawable2;
            return layers;
        }



    }


//    private void drawRoundRect(float left, float top, float right, float bottom, Canvas canvas,int radius,Paint onlinePaint) {
//        Path path = new Path();
//        path.moveTo(left, top);
//        path.lineTo(right, top);
//        path.lineTo(right, bottom);
//        path.lineTo(left + radius, bottom);
//        path.quadTo(left, bottom, left, bottom - radius);
//        path.lineTo(left, top + radius);
//        path.quadTo(left, top, left + radius, top);
//        canvas.drawPath(path, onlinePaint);
//    }

    public static float dp2Pixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }



}
