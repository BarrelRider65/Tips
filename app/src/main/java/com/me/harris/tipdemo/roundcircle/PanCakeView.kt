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

    val offset = 30f//伸出的距离

    var startAngle = 0f //顺时针画圆弧开始的角度
    var sweepAngle = 60f //默认第一个圆弧是60度
    val degreeGap = 5f//不同圆弧之间的间隔，默认隔开5度

    var firstArchColor = Color.parseColor("#29AB91")//从startAngle开始，第一个圆弧的颜色
    var secondArchColor = Color.parseColor("#F05A4A")//第二个圆弧的颜色



    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {

        paint.style=Paint.Style.FILL
        paint.color = firstArchColor
        textPaint.strokeWidth=2f
        centerX = width /2f
        centerY = height /2f
        if (centerX<centerY){
            outterRadius = centerX
        }else {
            outterRadius = centerY
        }

        outterRadius -= offset


        if (innerRadius<=0){
            innerRadius = outterRadius/2f
        }



        //画大的那部分圆
        path.addCircle(centerX,centerY,outterRadius,Path.Direction.CW)
        path.close()
        canvas?.drawPath(path,paint)
        path.reset()

        // 画一个角度比小的那部分大一点的白色的饼

        paint.color = Color.WHITE
        val sweepAngle_1 = sweepAngle+degreeGap*2
        val secondRadius_1 = outterRadius+offset/2
        val startX_1 = centerX-secondRadius_1*Math.sin((sweepAngle_1/2)*Math.PI/180)
        val startY_1 = centerY-secondRadius_1*Math.cos((sweepAngle_1/2)*Math.PI/180)
        val endX_1 = centerX+secondRadius_1*Math.sin((sweepAngle_1/2)*Math.PI/180)
        val endY_1 = startY_1
        path.moveTo(centerX,centerY)
        path.lineTo(startX_1.toFloat(),startY_1.toFloat())
        path.lineTo(endX_1.toFloat(), endY_1.toFloat())
        path.close()
        val recf2 = RectF(centerX-secondRadius_1,centerY-secondRadius_1,
                centerX+secondRadius_1,centerY+secondRadius_1)
        path.addArc(recf2,-90f-(sweepAngle_1/2), sweepAngle_1)
        canvas?.drawPath(path,paint)
        path.reset()


        //画小的部分
        paint.color = secondArchColor
        val secondRadius = outterRadius+offset/2
        val startX = centerX-secondRadius*Math.sin((sweepAngle/2)*Math.PI/180)
        val startY = centerY-secondRadius*Math.cos((sweepAngle/2)*Math.PI/180)
        val endX = centerX+secondRadius*Math.sin((sweepAngle/2)*Math.PI/180)
        val endY = startY
        path.moveTo(centerX,centerY)
        path.lineTo(startX.toFloat(),startY.toFloat())
        path.lineTo(endX.toFloat(), endY.toFloat())
        path.close()
        val recf = RectF(centerX-secondRadius,centerY-secondRadius,
                centerX+secondRadius,centerY+secondRadius)
        path.addArc(recf,-90f-(sweepAngle/2), sweepAngle)
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