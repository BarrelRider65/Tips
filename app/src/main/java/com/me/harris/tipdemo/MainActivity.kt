package com.me.harris.tipdemo

import android.graphics.Color
import android.os.Bundle
import android.os.Looper
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.RelativeLayout

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    var tipViewBuilder:TipViewBuilder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        Looper.myQueue().addIdleHandler {
            tipViewBuilder = TipViewBuilder.
                    Companion.
                    make(this,"添加后有月、季度、年、永久等多个付费档供成员选择",button)
                    .addRule(RelativeLayout.BELOW)
//                    .arrowOffset(dip2px(120f))
                    .textColor(Color.WHITE)
                    .backGroundColor(Color.BLUE)
                    .show()
            false
        }




    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        tipViewBuilder?.dismiss()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
