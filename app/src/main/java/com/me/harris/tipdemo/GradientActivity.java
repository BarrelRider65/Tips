package com.me.harris.tipdemo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ReplacementSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.me.harris.tipdemo.widget.EmulatorDetector;

import java.util.logging.Logger;

public class GradientActivity extends AppCompatActivity {

    public static final String PLACE_HOLER = "交易开放 7月16日10:00AM(EDT)BitMart交易所开放交易，17日10:00AM(EDT)开放取款";

    TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_gradient_curved);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        relativeLayout.setLayoutParams(params);
        setContentView(relativeLayout);
        mTextView = new TextView(this);
        mTextView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        relativeLayout.addView(mTextView);
//        test();

        testEmulator();

        mTextView.postDelayed(() -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Intent intent  = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                startActivity(intent);
            }
        }, 1_000);




    }

  

    private void testEmulator() {
        boolean isEmulator = EmulatorDetector.isEmulator();
        String str = EmulatorDetector.getDeviceListing();
        Log.e("TAG","是不是模拟器"+isEmulator);
        Log.e("TAG",str);
        EmulatorDetector.logcat();

    }


    private void test(){
        SpannableString spanString = new SpannableString(PLACE_HOLER);
        int start = 0;
        int end = 4;
        // 设定渐变色
        int[] colors = {
                Color.parseColor("#a4caf9"), Color.parseColor("#4c99f5")
        };

        spanString.setSpan(new RadiusGradientBackgroundSpan(colors,this),start,end,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTextView.setText(spanString);

        Button button =  findViewById(R.id.mbtn);
        if (button!=null){
            findViewById(R.id.mbtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String content = getString(R.string.sample_text2);
                    SpannableString  spanString = new SpannableString(content);
                    int[] colors = {
                            Color.parseColor("#ffb4b5"), Color.parseColor("#f96264")
                    };
                    spanString.setSpan(new RadiusGradientBackgroundSpan(colors,GradientActivity.this),0,6,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    mTextView.setText(spanString);
                }
            });
        }
    }


    public class RadiusGradientBackgroundSpan extends ReplacementSpan {

        private int mSize; //这个应该是横向的宽度


        Context mContext;

        int[] mColors;


        Paint mPaint;

        Path path;

        int padding = 6 ;//Drawable内部的纵向padding

        float degree = (float) (Math.PI/12); //扭转15度


        public RadiusGradientBackgroundSpan(int[] colorArray, Context context) {
            this.mColors = colorArray;
            this.mContext = context;

            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(Color.WHITE); //TextView的背景色
//            mLinePaint.setColor(Color.BLACK);
//            mLinePaint.setStyle(Paint.Style.STROKE);
//            mLinePaint.setStrokeWidth(1f);
        }



        @Override
        public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            int rowHeight = (int) (paint.descent()-paint.ascent()+padding*2);
            mSize = (int) ((paint.measureText(text, start, end) )+rowHeight*Math.tan(degree));
            return mSize+20; //与右侧的文字之间保留一点空格
        }



        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
            int color = paint.getColor();//保存文字颜色
            float original_textSize = paint.getTextSize();
           float decedent = paint.descent();
           float ascent = paint.ascent();
            float offset = (float) (Math.tan(degree) *(decedent-ascent+padding*2));

            paint.setAntiAlias(true);// 设置画笔的锯齿效果
//            RectF oval = new RectF(x, y + paint.ascent(), x + mSize, y + paint.descent());
            //设置文字背景矩形，x为span其实左上角相对整个TextView的x值，y为span左上角相对整个View的y值。paint.ascent()获得文字上边缘，paint.descent()获得文字下边缘
//            Rect rec = new Rect((int)x,(int)(y+paint.ascent()),(int)(x+mSize),(int)(y+paint.descent()));

            Rect rec = new Rect((int)x,(int)(y+paint.ascent()),(int)(x+mSize),(int)(y+paint.descent())+padding*2);



            Drawable gradientDrawable= new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                    mColors);
            gradientDrawable.setBounds(rec);

            canvas.save();
            canvas.translate(offset+2,-padding); //因为设计图上的背景的顶部比后面的文字顶部要高一点
            canvas.skew((float) -Math.tan(degree),0); // 第一个表示x方向上倾斜角度的tan值，第二个参数表示y方向上倾斜角度的tan值
            gradientDrawable.draw(canvas);


            if(path==null){
                path = new Path();
            }
            path.reset();

            //开始画左上角的圆弧
            int radius = 10;
            path.moveTo(rec.left,rec.top);//挪到左上角
            path.rLineTo(radius,0); //往右挪10像素
            //以左上角为控制点画贝塞尔曲线
            path.quadTo(rec.left,rec.top, (float) (rec.left-radius*Math.sin(degree)), (float) (rec.top+radius*Math.cos(degree)));
            path.lineTo(rec.left,rec.top);//回到左上角
            path.close();


            //画右下角的圆弧
            path.moveTo(rec.right,rec.bottom);//挪到最右下角
            path.rLineTo(-radius,0);//开始顺时针画
            //以右下角为控制点画贝塞尔曲线
            path.quadTo(rec.right,rec.bottom, (float) (rec.right+radius*Math.sin(degree)), (float) (rec.bottom-radius*Math.cos(degree)));
            path.lineTo(rec.right,rec.bottom);//回到右下角
            path.close();

            canvas.drawPath(path,mPaint);
            canvas.restore();

            paint.setColor(Color.WHITE);
            paint.setTextSize(0.8f*original_textSize);
            canvas.drawText(text, start, end, x+offset*1.5f , y, paint);//绘制文字

            paint.setTextSize(original_textSize);
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
