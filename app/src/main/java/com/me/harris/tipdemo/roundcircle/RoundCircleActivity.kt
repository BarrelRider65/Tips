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


//        pancake.firstLine = ""
        pancake.updateData(0f,"")
//        decorateCircle()

    }



    private val runnable = Runnable {
        decorateCircle()
    }

    var lastPercentage = 0.2232f

    private fun decorateCircle() {
        lastPercentage+=0.003123f
        pancake.updateData(lastPercentage,"火币交易所")
        if (lastPercentage<=1f){
            pancake.postDelayed(runnable,20)
        }
    }


}