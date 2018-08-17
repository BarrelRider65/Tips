package com.me.harris.tipdemo.roundcircle

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.me.harris.tipdemo.R

class RoundCircleActivity :AppCompatActivity(){

    lateinit var toolbar:Toolbar
//    lateinit var circleView2: CircularStatisticsView2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_corner)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = "RoundCircle"

//        circleView2 = findViewById(R.id.circleView)



        decorateCircle()

    }

    private fun decorateCircle() {
//        circleView2.setCircleWidth(300)
//        circleView2.setPercentage(33.3f,66.6f)
    }


}