package com.me.harris.tipdemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.util.List;

public class GlideSampleActivity extends AppCompatActivity {


    ImageView centerImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_res);
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+Environment.DIRECTORY_PICTURES);
        File[] files = file.listFiles();
        String target = null;
        File[] subFiles = files[0].listFiles();
        target = subFiles[0].getAbsolutePath();
                Glide.with(this)
                .asBitmap().load(target)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        centerImage = findViewById(R.id.imageView);
                        centerImage.setImageBitmap(resource);
                    }
                });


    }
}
