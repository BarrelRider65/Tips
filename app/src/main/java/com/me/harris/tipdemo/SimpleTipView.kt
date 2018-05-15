package com.me.harris.tipdemo

import android.content.Context
import android.graphics.*
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout

class SimpleTipView : View {

    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attributes: AttributeSet?) : super(ctx, attributes)

    // val ARROW_TOP_LEFT = 0X000001
    // val ARROW_TOP_RIGHT = 0X000010
    // val ARROW_BOTTOM_LEFT = 0X000100
    // val ARROW_BOTTOM_RIGHT = 0X001000


    private var arrow_height = 20 // 箭头的高度
    private var arrow_width = 20 //箭头的宽度


    private val shadowColor = Color.parseColor("#77000000")
    private val rectRadius = 10f
    private var shadowRadius = 2f
    private var dx = 5f
    private var dy = 5f
    private var drawRect: RectF

    private var maxWidth = 0
    private var minWidth = 0
    private var minHeight = 0
    val paint = Paint()
    val textPaint= TextPaint(Paint.ANTI_ALIAS_FLAG)

    private var textColor= Color.BLACK
    private var backGroundColor= Color.WHITE


    init {

        shadowRadius = context.dip2px(2f).toFloat()
        dx = context.dip2px(0.5f).toFloat()
        dy = context.dip2px(0.5f).toFloat()
        arrow_height=context.dip2px(10f)
        arrow_width = context.dip2px(10f)

        paint.isAntiAlias = true
        /**
         * 解决旋转时的锯齿问题
         */
        paint.isFilterBitmap = true
        paint.isDither = true
        paint.style = Paint.Style.FILL
        paint.color = backGroundColor
        /**
         * 设置阴影
         */
        paint.setShadowLayer(shadowRadius, dx, dy, shadowColor)

        textPaint.density = context.resources.displayMetrics.density
        textPaint.color = textColor
        textPaint.style = Paint.Style.FILL_AND_STROKE
        textPaint.textSize = context.dip2px(12f).toFloat()


        drawRect = RectF()

        maxWidth = (context.screenWidth() / 2.5f).toInt()
        minHeight = context.dip2px(20f)
        minWidth = context.dip2px(20f)
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)

    }

    private var content: CharSequence? = ""

    fun setText(str: CharSequence) {
        this.content = str
    }


    var layout: Layout? = null
    val padding by lazy {
        context.dip2px(10f).toFloat()
    }

    fun calculateTipSize(): IntArray {



        val txtRawWidth = textPaint.measureText(content.toString())// 文字的宽度

//        txtWidth = matchChoose(minWidth, maxWidth, txtWidth.toInt()).toFloat()

        layout = StaticLayout(content, textPaint, txtRawWidth.toInt(), Layout.Alignment.ALIGN_NORMAL, 1.1f, 1.0f, true)


        val txtRawHeight = Math.max(minHeight.toFloat(), (layout as StaticLayout).height.toFloat())

        return when(layoutRule){
            RelativeLayout.BELOW  ->         // Tip在锚点之上或者之下的时候，高度加上箭头高度
                intArrayOf((txtRawWidth + padding * 2 + dx * 2 + shadowRadius * 2).toInt(),
                        (txtRawHeight + padding * 2 + arrow_height + dy * 2 + shadowRadius * 2).toInt())

            RelativeLayout.ABOVE  ->
                     intArrayOf((txtRawWidth + padding * 2 + dx * 2 + shadowRadius * 2).toInt(),
                             (txtRawHeight + padding * 2 + arrow_height + dy * 2 + shadowRadius * 2).toInt())
              //Tip在锚点左边或者右边的时候，宽度加上箭头高度 
            RelativeLayout.LEFT_OF ->
                   intArrayOf((txtRawWidth + padding * 2 + dx * 2 +arrow_height+ shadowRadius * 2).toInt(),
                           (txtRawHeight + padding * 2+ dy * 2 + shadowRadius * 2).toInt())
            else ->
                intArrayOf((txtRawWidth + padding * 2 + dx * 2 +arrow_height+ shadowRadius * 2).toInt(),
                        (txtRawHeight + padding * 2+ dy * 2 + shadowRadius * 2).toInt())
        }
    }

    fun matchChoose(start: Int, end: Int, num: Int): Int {
        var s = start
        var e = end
        if (s > end) {
            val tmp = s
            e = s
            s = tmp
        }
        return Math.max(s, Math.min(num, e))
    }

    fun tipTextColor(color:Int){
        this.textColor=color
        textPaint.color=color
    }
    fun tipBackGroundColor(color:Int){
        this.backGroundColor=color
        paint.color=color
    }

    fun addLayoutRule(rule:Int){
        this.layoutRule  = rule
    }

    var layoutRule  = RelativeLayout.ABOVE

    private var arrowOffset = 0
    private var anchor: View? = null
    fun anchor(view: View?) {
        this.anchor = view
    }

    fun arrowOffset(offset:Int){
        this.arrowOffset= offset
    }

    //箭头相对于画布左上角的偏移量
    private fun calculateArrowOffset(): Int {
//        anchor?.let {
//            val arr = IntArray(2)
//            it.getLocationOnScreen(arr)
            if (this.arrowOffset>0) {
                return arrowOffset
            }
            val size = calculateTipSize()
            when(layoutRule) {
                RelativeLayout.BELOW -> {
                   return (size[0]-arrow_width)/2
                }
                RelativeLayout.ABOVE -> {
                   return  (size[0]-arrow_width)/2
                }
                RelativeLayout.LEFT_OF -> {
                 return   (size[1]-arrow_width)/2
                }
                RelativeLayout.RIGHT_OF -> {
                 return   (size[1]-arrow_width)/2
                }
            }
        return 0
    }



    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val arr = calculateTipSize()


        val w = MeasureSpec.makeMeasureSpec(arr[0], MeasureSpec.EXACTLY)
        val h = MeasureSpec.makeMeasureSpec(arr[1], MeasureSpec.EXACTLY)

        setMeasuredDimension(w, h)

        arrowOffset = calculateArrowOffset()
    }


    private val path = Path()
    private val pathRect = Path()
    private val roundRect = RectF()
    // private val srcOut = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas!!)

        var height = measuredHeight.toFloat() - arrow_height - dy * 2 - shadowRadius * 2
        var width = measuredWidth.toFloat() - dx * 2 - shadowRadius * 2

        path.reset()      // 箭头的path
        pathRect.reset()    //圆角矩形的path

        when (layoutRule){
            RelativeLayout.LEFT_OF ->{
                height = measuredHeight.toFloat()-dy*2-shadowRadius*2
                width = measuredWidth.toFloat()-dx*2-shadowRadius*2-arrow_height
                roundRect.set(dx-arrow_height,dy,dx+width-arrow_height,dy+height)
            }
            RelativeLayout.BELOW -> {
             height = measuredHeight.toFloat()-dy*2-shadowRadius*2-arrow_height
             width = measuredWidth.toFloat()-dx*2-shadowRadius*2
             roundRect.set(dx,dy+arrow_height,dx+width,dy+height+arrow_height)
              path.moveTo(dx+arrowOffset,dy+arrow_height)
              path.lineTo(dx+arrowOffset+arrow_width/2,dy)
              path.lineTo(dx+arrowOffset+arrow_width,dy+arrow_height)
            }
            RelativeLayout.ABOVE -> {
             height = measuredHeight.toFloat()-dy*2-shadowRadius*2-arrow_height
             width = measuredWidth.toFloat()-dx*2-shadowRadius*2
             roundRect.set(dx,dy-arrow_height,dx+width,dy+height-arrow_height)
              path.moveTo(dx+arrowOffset,dy+height)
              path.lineTo(dx+arrowOffset+arrow_width/2,dy-arrow_height+arrow_height)
              path.lineTo(dx+arrowOffset+arrow_width,dy+arrow_height)
            }
            RelativeLayout.RIGHT_OF -> {
                 height = measuredHeight.toFloat()-dy*2-shadowRadius*2
                 width = measuredWidth.toFloat()-dx*2-shadowRadius*2-arrow_height
                        roundRect.set(dx+arrow_height,dy,dx+width+arrow_height,dy+height)
            }
        }
        pathRect.addRoundRect(roundRect, rectRadius, rectRadius, Path.Direction.CCW)
//        path.moveTo(arrowOffset + dx + width / 2 + arrow_height, dy + height)
//
//        path.lineTo(arrowOffset + dx + width / 2, dy + height + arrow_height)
//
//        path.lineTo(arrowOffset + dx + width / 2 - arrow_height, dy + height)
//
////        path.lineTo(dy, height)



        path.close()
//
        path.op(pathRect, Path.Op.UNION)
       
        canvas.drawPath(path, paint)
        drawText(canvas)
    }


    fun drawText(canvas: Canvas) {

        when(layoutRule){
            RelativeLayout.ABOVE-> {
             canvas.translate(padding, padding-arrow_height)

            }
            RelativeLayout.BELOW -> {
              canvas.translate(padding, padding+arrow_height)

            }
            RelativeLayout.LEFT_OF -> {
              canvas.translate(padding-arrow_height,padding)
            }
            RelativeLayout.RIGHT_OF -> {
               canvas.translate(padding+arrow_height,padding)
            }
        }


        layout?.draw(canvas)
    }


}