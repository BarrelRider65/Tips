package com.me.harris.tipdemo.maxlines

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.me.harris.tipdemo.R
import kotlinx.android.synthetic.main.activity_maxline_text.*

class MaxLinesActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maxline_text)
        tv_target.text = "这是一门 100% 针对实际工作需要的课程，拥有 20 年一线经验的李国威，教你一次吃这是一门 100% 针对实际工作需要的课程，拥有 20 年一线经验的李国威，教你一次吃…这是一门 100% 针对实际工作需要的课程，拥有 20 年一线经验的李国威，教你一次吃…"

    }
}