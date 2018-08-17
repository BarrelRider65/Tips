package com.me.harris.tipdemo.roundcircle

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class PanCakeView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr){


    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val path = Path()


    //圆心的坐标
    var centerX = 0f
    var centerY = 0f


    // 可设置的参数
    var innerRadius = 0f //内圆半径
    var outterRadius = 0f //外圆半径

    var startAngle = 0f //顺时针画圆弧开始的角度
    var sweepAngle = 60f*Math.PI/180f //默认第一个圆弧是60度

    var firstArchColor = Color.parseColor("#F05A4A")//从startAngle开始，第一个圆弧的颜色
    var secondArchColor = Color.parseColor("#29AB91")//第二个圆弧的颜色





    fun initPaint(){
        paint.style=Paint.Style.FILL
        paint.color = firstArchColor
        textPaint.strokeWidth=2f
    }



    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        initPaint()
        centerX = width /2f
        centerY = height /2f
        if (centerX<centerY){
            outterRadius = centerX/2f
        }else {
            outterRadius = centerY/2f
        }

        if (innerRadius<=0){
            innerRadius = outterRadius/2f
        }



        //画外部的圆
        path.addCircle(centerX,centerY,outterRadius,Path.Direction.CW)
        path.close()
        canvas?.drawPath(path,paint)
        path.reset()

        //画绿色的圆环

        paint.color = secondArchColor
        val secondRadius = outterRadius+20
        val startX = centerX-secondRadius*Math.sin((sweepAngle/2)*Math.PI/180)
        val startY = centerY-secondRadius*Math.cos((sweepAngle/2)*Math.PI/180)


        val endX = centerX+secondRadius*Math.sin((sweepAngle/2)*Math.PI/180)
        val endY = centerY+secondRadius*Math.cos((sweepAngle/2)*Math.PI/180)

        path.moveTo(centerX,centerY)
        path.lineTo(startX.toFloat(), startY.toFloat())
        path.lineTo(startX.toFloat(), startY.toFloat())
        path.close()
        val recf = RectF(centerX-secondRadius,centerY-secondRadius,
                centerX+secondRadius,centerY+secondRadius)
        path.addArc(recf,-120f,90f)
        canvas?.drawPath(path,paint)
        path.reset()


        //画内部的白色
        paint.color=Color.WHITE
        path.addCircle(centerX,centerY,innerRadius,Path.Direction.CW)
        path.close()
        canvas?.drawPath(path,paint)
        path.reset()




    }





}