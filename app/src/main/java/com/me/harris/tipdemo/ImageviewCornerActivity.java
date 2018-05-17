package com.me.harris.tipdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;

import com.me.harris.tipdemo.widget.CornerImageView;

public class ImageviewCornerActivity extends AppCompatActivity {

    CornerImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_corner);
        mImageView = findViewById(R.id.image);


    }


}
