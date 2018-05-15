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

class Tiast(val ctx: Context,val anchor:View) {

    companion object {

        fun make(ctx: Context, str: String, anchor:View,mills: Int = 4000): Tiast {
            return Tiast(ctx,anchor).apply {
                setContent(str)
                duration = mills
            }
        }

    }


//    private val anchor: View? = null
    private var tip: SimpleTipView? = null

    var layoutRule: Int = RelativeLayout.ABOVE //上下左右都支持
    private val wm: WindowManager by lazy {
        ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    // 支持RelativeLayout的Rule , above , left of , below ,right of
    fun addRule(rule:Int):Tiast {
        this.layoutRule = rule
        return this
    }


    init {
    }

    private var duration = 2000
    private var content: String? = null


    private var textColor = Color.BLACK
    private var backGroundColor = Color.WHITE

    fun textColor(color: Int): Tiast {
        this.textColor = color
        return this
    }

    fun backGroundColor(color: Int): Tiast {
        this.backGroundColor = color
        return this
    }


    fun setContent(str: String) {
        content = str
        tip = SimpleTipView(ctx).apply {
            setText(str)
        }
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



    fun show(): Tiast {
        if (tip == null && content != null) {
            setContent(content!!)
        }

        (tip as SimpleTipView)
                .apply {
                    tipBackGroundColor(backGroundColor)
                    tipTextColor(this@Tiast.textColor)
                    anchor(anchor)
                    anchor.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
                        //  Log.d("Tiast", "anchor change")
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


    private fun params(): WindowManager.LayoutParams {
        val anchorSize = calanchorPosition() //锚点的左上角相对于屏幕的位置
        val selfSize = (tip as SimpleTipView).calculateTipSize() //控件自己占据的宽度和高度
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
                lp.x = anchorSize[0]+anchor.measuredWidth/2-lp.width/2 //默认对齐
                lp.y = anchorSize[1]-lp.height
            }

            RelativeLayout.BELOW -> {
                lp.x = anchorSize[0]+anchor.measuredWidth/2-lp.width/2 //默认对齐
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

    fun dismiss(): Tiast {
        tip?.let {
            if (it.isAttachedToWindow)
                wm.removeView(it)
        }
        return this
    }


}