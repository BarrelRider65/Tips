package com.me.harris.tipdemo.roundcircle

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.me.harris.tipdemo.R

class RoundCircleActivity :AppCompatActivity(){

    lateinit var toolbar:Toolbar
//    lateinit var circleView2: CircularStatisticsView2
    lateinit var pancake:PanCakeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_corner)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = "RoundCircle"

        pancake = findViewById(R.id.circleView)




        pancake.sweepAngle = 0.2f
        decorateCircle()

    }



    private val runnable = Runnable {
        decorateCircle()
    }

    private fun decorateCircle() {
        pancake.sweepAngle+=1f
        pancake.postInvalidate()
        if (pancake.sweepAngle<=360){
            pancake.postDelayed(runnable,20)
        }
    }


}