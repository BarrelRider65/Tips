package com.me.harris.tipdemo

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.RelativeLayout

class TipViewBuilder(val ctx: Context, val anchor:View) {

    companion object {

        fun make(ctx: Context, str: String, anchor:View,mills: Int = 4000): TipViewBuilder {
            return TipViewBuilder(ctx,anchor).apply {
                setContent(str)
                duration = mills
            }
        }

    }


//    private val anchor: View? = null
    private var tip: TipView? = null

    var layoutRule: Int = RelativeLayout.ABOVE //上下左右都支持
    private val wm: WindowManager by lazy {
        ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    // 支持RelativeLayout的Rule , above , left of , below ,right of
    fun addRule(rule:Int):TipViewBuilder {
        this.layoutRule = rule
        return this
    }


    //设置箭头的偏移量
    fun arrowOffset(offset:Int): TipViewBuilder {
        this.arrowOffset = offset
        return this
    }

    var arrowOffset:Int = 0

    init {
    }

    private var duration = 2000
    private var content: String = ""


    private var textColor = Color.BLACK
    private var backGroundColor = Color.WHITE

    fun textColor(color: Int): TipViewBuilder {
        this.textColor = color
        return this
    }

    fun backGroundColor(color: Int): TipViewBuilder {
        this.backGroundColor = color
        return this
    }


    fun setContent(str: String):TipViewBuilder {
        content = str
        return this
    }


    //计算锚点的左上角的x,y坐标
     fun calanchorPosition(): IntArray {

        var x = ctx.screenWidth() / 2
        var y = ctx.screenHeight() / 2

        anchor.let {
            val arr = IntArray(2)
            it.getLocationOnScreen(arr)
            x = arr[0]
            y = arr[1]
        }

        return intArrayOf(x, y)
    }



    fun show(): TipViewBuilder {
        if (tip == null) {
            tip = TipView(ctx,anchor)
        }

        (tip as TipView)
                .apply {
                    setText(content)
                    tipBackGroundColor(backGroundColor)
                    addLayoutRule(this@TipViewBuilder.layoutRule)
                    tipTextColor(this@TipViewBuilder.textColor)
                    if (this@TipViewBuilder.arrowOffset>0){
                        arrowOffset(this@TipViewBuilder.arrowOffset)
                    }
                    anchor.addOnLayoutChangeListener { v, _, _, _, _, _, _, oldRight, oldBottom ->
                        //  Log.d("TipViewBuilder", "anchor change")
                        Looper.myQueue()
                                .addIdleHandler {
                                    update()
                                    false
                                }

                    }
                }


        wm.addView(tip, params())
//        handler
//                .postDelayed(
//                        {
//                            dismiss()
//                        },
//                        duration.toLong())

        return this
    }

    var center_horizontal = true


    private fun params(): WindowManager.LayoutParams {
        val anchorSize = calanchorPosition() //锚点的左上角相对于屏幕的位置
        val selfSize = (tip as TipView).calculateMeasureSize() //控件自己占据的宽度和高度
        val lp = WindowManager.LayoutParams()
        lp.width = selfSize[0] //控件自己想要的宽度
        lp.height = selfSize[1] //控件自己想要的高度
        lp.format = PixelFormat.TRANSLUCENT
        lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
        lp.flags = (WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        lp.gravity = Gravity.TOP or Gravity.LEFT or Gravity.START


        when(layoutRule){

            RelativeLayout.ABOVE -> {
                if (center_horizontal){
                    lp.x = (ctx.screenWidth()-selfSize[0])/2
                }else{
                    lp.x = anchorSize[0]+anchor.measuredWidth/2-lp.width/2 //默认对齐
                }
                lp.y = (anchorSize[1]-lp.height)+(tip as TipView).verticalPadding()
            }

            RelativeLayout.BELOW -> {
                if (center_horizontal){ //在屏幕中间
                    lp.x = (ctx.screenWidth()-selfSize[0])/2
                }else {
                    lp.x = anchorSize[0]+anchor.measuredWidth/2-lp.width/2 //默认对齐
                }
                lp.y = anchorSize[1]+anchor.measuredHeight//在锚点下方
            }

            RelativeLayout.LEFT_OF -> {
                lp.x = anchorSize[0]-lp.width
                lp.y = anchorSize[1]+anchor.measuredHeight/2-lp.height/2// 默认纵向对齐
            }
            RelativeLayout.RIGHT_OF -> {
                lp.x = anchorSize[0]+anchor.measuredWidth
                lp.y = anchorSize[1]+anchor.measuredHeight/2-lp.height/2// 默认纵向对齐
            }
            else ->
                    IllegalArgumentException("wrong layout rule")
        }
        return lp
    }

    private fun update() {
        if (tip?.isAttachedToWindow == true)
            wm.updateViewLayout(tip, params())

    }

    private val handler = Handler()

    fun dismiss(): TipViewBuilder {
        tip?.let {
            if (it.isAttachedToWindow)
                wm.removeView(it)
        }
        return this
    }


}