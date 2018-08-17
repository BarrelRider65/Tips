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

    val firstLine = "1-BitMEX"
    val secondLine = "27.77%"






    // 可设置的参数
    var innerRadius = 0f //内圆半径
    var outterRadius = 0f //外圆半径

    val offset = 30f//伸出的距离

    var startAngle = 0f //顺时针画圆弧开始的角度
    var sweepAngle = 0f
    // 这里要注意一点，假如上面的圆弧的角度超出了180度，那么底部的圆弧就要伸出来

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

        val upperLarger = sweepAngle>180//底部的Angle要大于180度的话，大一点的位置就要突出



        //画底部的那部分圆
        val bottomRadius = if (sweepAngle<=180) outterRadius else outterRadius+offset/2
        path.addCircle(centerX,centerY,bottomRadius,Path.Direction.CW)
        path.close()
        canvas?.drawPath(path,paint)
        path.reset()


        //把即将画上去的底部的圆的底部抹白
        if (sweepAngle>5f) {
            drawBellowUpper(canvas)
        }

            //画上面的那部分圆
        drawUpper(canvas)



        if (sweepAngle in 5f..355.0f){
            drawGap(canvas)
        }


        //画内部的白色
        paint.color=bgColor
        path.addCircle(centerX,centerY,innerRadius,Path.Direction.CW)
        path.close()
        canvas?.drawPath(path,paint)
        path.reset()

        //画文字
        //画第一行
        val txtSize =  spToPx(14f,context).toFloat()
        textPaint.textSize = txtSize
        textPaint
        textPaint.color = txtColor
        textPaint.textAlign = Paint.Align.CENTER

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

//        gapPath.reset()
//        //画左侧的横线
//        path.moveTo(centerX,centerY)
//        path.lineTo()
        canvas?.drawText(firstLine, (width/2).toFloat(),centerY-firstLineHeight,textPaint)
        textPaint.typeface = Typeface.DEFAULT_BOLD
        textPaint.textSize = txtSize*1.2f
        canvas?.drawText(secondLine, (width/2).toFloat(),centerY+secondLineHeight,textPaint)
        textPaint.typeface = Typeface.DEFAULT
        textPaint.textSize = txtSize


    }

    var firstLineWidth = 0f
    var secondLineWidth = 0f
    var firstLineHeight = 0f
    var secondLineHeight = 0f



    val rectF= RectF()

    fun spToPx(sp: Float, context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics).toInt()
    }

    //画红色的部分
    fun drawUpper(canvas: Canvas?){
        //画上面的的部分
        paint.color = secondArchColor
        val secondRadius = if(sweepAngle<=180f) (outterRadius+offset/2) else outterRadius
        val startX = centerX-secondRadius*Math.sin((sweepAngle/2)*Math.PI/180)
        val startY = centerY-secondRadius*Math.cos((sweepAngle/2)*Math.PI/180)
        val endX = centerX+secondRadius*Math.sin((sweepAngle/2)*Math.PI/180)
        val endY = startY
        path.moveTo(centerX,centerY)
        path.lineTo(startX.toFloat(),startY.toFloat())
        path.lineTo(endX.toFloat(), endY.toFloat())
        path.close()

        rectF.set(centerX-secondRadius,centerY-secondRadius,
                centerX+secondRadius,centerY+secondRadius)
        path.addArc(rectF,-90f-(sweepAngle/2), sweepAngle)
        canvas?.drawPath(path,paint)
        path.reset()
    }

    //在底部画上白色
    fun drawBellowUpper(canvas: Canvas?){
        paint.color = bgColor
        path.reset()
        val secondRadius = outterRadius+offset/2+2
        val sweepAngle = sweepAngle+getGapDegree()
        val startX = centerX-secondRadius*Math.sin((sweepAngle/2)*Math.PI/180)
        val startY = centerY-secondRadius*Math.cos((sweepAngle/2)*Math.PI/180)
        val endX = centerX+secondRadius*Math.sin((sweepAngle/2)*Math.PI/180)
        val endY = startY
        path.moveTo(centerX,centerY)
        path.lineTo(startX.toFloat(),startY.toFloat())
        path.lineTo(endX.toFloat(), endY.toFloat())
        path.close()

        rectF.set(centerX-secondRadius,centerY-secondRadius,
                centerX+secondRadius,centerY+secondRadius)
        path.addArc(rectF,-90f-(sweepAngle/2), sweepAngle)
        canvas?.drawPath(path,paint)
        path.reset()
    }


    fun drawGap(canvas: Canvas?){
        //画两根横线
        val secondRadius = outterRadius+offset/2+2
        val degree = sweepAngle+getGapDegree()
        val startX = centerX-secondRadius*Math.sin((degree/2)*Math.PI/180)
        val startY = centerY-secondRadius*Math.cos((degree/2)*Math.PI/180)
        val endX = centerX+secondRadius*Math.sin((degree/2)*Math.PI/180)
        val endY = startY
        gapPaint.strokeWidth = (outterRadius*Math.PI*(getGapDegree()/180f)).toFloat()
        canvas?.drawLine(centerX,centerY,startX.toFloat(),startY.toFloat(),gapPaint)
        canvas?.drawLine(centerX,centerY,endX.toFloat(),endY.toFloat(),gapPaint)
    }

    private fun getGapDegree():Float{
        return if (sweepAngle >= 355f) {
            val remain = 360f-sweepAngle
            remain*0.1f
        } else {
            2f
        }
    }

    fun updateSweepAngle( newAngle:Float){
        this.sweepAngle = newAngle
        postInvalidate()
    }
}