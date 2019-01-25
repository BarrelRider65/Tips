package com.me.harris.tipdemo.addview

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.me.harris.tipdemo.R

class AddViewLinearLayoutActivity :AppCompatActivity(){

    var container:LinearLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_linear_layout)
        container = findViewById(R.id.ll_container)

        var index = 0

        val tx = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            setPadding(10,10,10,10)
               addView(TextView(this@AddViewLinearLayoutActivity).apply { text="innner"
                   isClickable = true//  a child inside viewGroup with clickable set to true will prohibit it's parent from calling onClick
                   layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                   setPadding(30,30,30,30)

               })
//            text = "this is dummy"
        }



        repeat(5) {
            val tv = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                setPadding(10,10,10,10)
                text = "第 $index 个child "
                index++
            }
            container!!.addView(tv)
        }
        container!!.addView(tx,2)
        tx.setOnClickListener {
            Toast.makeText(this,"awesome",Toast.LENGTH_SHORT).show()
        }
        container!!.addView(createView(),3)



    }

    fun createView():TextView{
        val tx = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            setPadding(10,10,10,10)
            text = "this is 2"
        }
        return tx
    }



}