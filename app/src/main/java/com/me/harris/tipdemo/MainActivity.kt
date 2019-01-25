package com.me.harris.tipdemo

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.me.harris.tipdemo.`interface`.ItemClickListener
import com.me.harris.tipdemo.adapter.CustomAdapter
import com.me.harris.tipdemo.addview.AddViewLinearLayoutActivity
import com.me.harris.tipdemo.maxlines.MaxLinesActivity
import com.me.harris.tipdemo.roundcircle.RoundCircleActivity
import com.me.harris.tipdemo.searchshape.SearchBarActivity
import com.me.harris.tipdemo.shimmer.ShimmerActivity
import kotlinx.android.synthetic.main.activity_recycler_view.*

class MainActivity :AppCompatActivity(), ItemClickListener {



    lateinit var mAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        initAdapter()
    }

    private fun initAdapter() {
        val userList = mutableListOf<Triple<String,Int,Class<out Activity>>>(
                Triple("GradientSample",1, GradientActivity::class.java),
                Triple("GlideSampleActivity",2,GlideSampleActivity::class.java),
                Triple("ThirdActivity",3,ThirdActivity::class.java),
                Triple("RoundCorner",4,ImageviewCornerActivity::class.java),
                Triple("RoundCircle",5, RoundCircleActivity::class.java),
                Triple("Gradient",6, GradientRotate::class.java),
                Triple("Shimmer",7, ShimmerActivity::class.java),
                Triple("Shape",8, SearchBarActivity::class.java),
                Triple("addView",9, AddViewLinearLayoutActivity::class.java),
                Triple("MaxLine",10, MaxLinesActivity::class.java)
//                Triple("ClipPath",6,CustomDrawingActivity::class.java),
//                Triple("Locale",6,UpdatingConfigActivity::class.java)

        ).apply {
            //            this.addAll(this)
//            this.addAll(this)
//            this.addAll(this)
//            this.addAll(this)
//            this.addAll(this)
        }

        mAdapter = CustomAdapter(userList,this)
        recyclerView1.adapter = mAdapter
        recyclerView1.layoutManager = LinearLayoutManager(this)
        recyclerView1.addItemDecoration(object :  RecyclerView.ItemDecoration(){
            override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect?.set(12,5,12,5)
            }
        })
    }

    override fun onItemClick(position: Int, view: View) {
        val target = mAdapter.userList[position].third
        val intent = Intent(this,target)
        startActivity(intent)
//        Toast.makeText(this,"点击了 $position 以及View的id 是 ${view.id}",Toast.LENGTH_SHORT).show()
    }

}