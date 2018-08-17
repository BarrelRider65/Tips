package com.me.harris.tipdemo.roundcircle

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.util.TypedValue



class PanCakeView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr){


    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val path = Path()
    val gapPaint = Paint(Paint.ANTI_ALIAS_FLAG) //用来画那两条白线的paint


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

    var firstArchColor = Color.parseColor("#02b388")//从startAngle开始，第一个圆弧的颜色
    var secondArchColor = Color.parseColor("#ff4040")//第二个圆弧的颜色

    val bgColor = Color.WHITE
    val txtColor = Color.RED

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
        gapPaint.color = bgColor


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

        //画两根横线
        gapPaint.strokeWidth = 10f
        canvas?.drawLine(centerX,centerY,startX.toFloat(),startY.toFloat(),gapPaint);
        canvas?.drawLine(centerX,centerY,endX.toFloat(),endY.toFloat(),gapPaint);
        //画内部的白色
        paint.color=bgColor
        path.addCircle(centerX,centerY,innerRadius,Path.Direction.CW)
        path.close()
        canvas?.drawPath(path,paint)
        path.reset()

        //画文字
        //画第一行
        textPaint.textSize = spToPx(14f,context).toFloat()
        textPaint.color = txtColor
        textPaint.textAlign = Paint.Align.CENTER
        val firstLine = "1-BitMEX"
        val secondLine = "27.77%"

        if (firstLineWidth==0f){
            firstLineWidth = textPaint.measureText(firstLine)
        }
        if (secondLineWidth==0f) {
            secondLineWidth = textPaint.measureText(secondLine)
        }

        if (firstLineHeight==0f){
            val textBounds = Rect()
            textPaint.getTextBounds(firstLine, 0, firstLine.length, textBounds)
            firstLineHeight = textBounds.height().toFloat()
        }

        if (secondLineHeight==0f){
            val textBounds = Rect()
            textPaint.getTextBounds(secondLine, 0, secondLine.length, textBounds)
            secondLineHeight = textBounds.height().toFloat()
        }

        canvas?.drawText(firstLine, (width/2).toFloat(),centerY-firstLineHeight,textPaint)
        canvas?.drawText(secondLine, (width/2).toFloat(),centerY+secondLineHeight,textPaint)




    }

    var firstLineWidth = 0f
    var secondLineWidth = 0f
    var firstLineHeight = 0f
    var secondLineHeight = 0f

    fun spToPx(sp: Float, context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics).toInt()
    }





}