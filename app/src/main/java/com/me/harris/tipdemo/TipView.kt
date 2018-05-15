package com.me.harris.tipdemo

import android.content.Context
import android.graphics.*
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout



class TipView : View  {

    constructor(ctx: Context) : this(ctx,null)
    constructor(ctx: Context, attributes: AttributeSet?) : super(ctx, attributes)

    constructor(ctx:Context,anchor:View): this(ctx,null){
        this.anchor = anchor
    }


    lateinit var anchor:View


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

    private var content: CharSequence = ""

    fun setText(str: CharSequence) {
        this.content = str
    }


    var layout: Layout? = null
    val padding by lazy {
        context.dip2px(10f).toFloat()
    }

    val verticalPadding by lazy {
        context.dip2px(2f).toFloat()
    }

    val maxContentWidth by lazy {
        context.screenWidth()/2
    }

    //计算实际需要的宽高
    fun calculateMeasureSize(): IntArray {

        val txtRawWidth = textPaint.measureText(content.toString())// 文字的宽度

       var array = IntArray(2)
        anchor.getLocationOnScreen(array)
        val remainingWidth:Int
        when(layoutRule) {

            RelativeLayout.ABOVE
                    -> {
                layout = StaticLayout(content, textPaint, txtRawWidth.toInt(),
                        Layout.Alignment.ALIGN_NORMAL,
                        1.1f, 1.0f,
                        true)
                val txtRawHeight = (layout as StaticLayout).height.toFloat()
               return intArrayOf((txtRawWidth + padding * 2 + dx * 2 + shadowRadius * 2).toInt(),
                        (txtRawHeight + padding * 2 + arrow_height + dy * 2 + shadowRadius * 2).toInt())
            }

            RelativeLayout.BELOW -> {
                layout = StaticLayout(content, textPaint, txtRawWidth.toInt(),
                        Layout.Alignment.ALIGN_NORMAL,
                        1.1f, 1.0f,
                        true)
                val txtRawHeight = (layout as StaticLayout).height.toFloat()
                return  intArrayOf((txtRawWidth + padding * 2 + dx * 2 + shadowRadius * 2).toInt(),
                        (txtRawHeight + verticalPadding * 2 + arrow_height + dy * 2 + shadowRadius * 2).toInt())

            }
            RelativeLayout.LEFT_OF -> {
                remainingWidth = array[0]-arrow_height
                val exceeedingLimit:Boolean
                if (txtRawWidth>remainingWidth- padding * 2 - dx * 2 -arrow_height- shadowRadius * 2){ //文字长度一行放不下
                    exceeedingLimit = true
                    layout = StaticLayout(content,textPaint,remainingWidth
                            ,Layout.Alignment.ALIGN_NORMAL,
                            1.1f,1.0f,true) //气泡在锚点左右侧的时候，最大宽度限定为距离锚点距离屏幕左侧的距离
                }else {
                    exceeedingLimit = false
                    layout = StaticLayout(content, textPaint, txtRawWidth.toInt(),
                            Layout.Alignment.ALIGN_NORMAL,
                            1.1f, 1.0f,
                            true) //一行摆得下
                }
                val txtRawHeight = (layout as StaticLayout).height.toFloat()
                if (exceeedingLimit){
                    return intArrayOf((remainingWidth ),
                            (txtRawHeight + padding * 2+ dy * 2 + shadowRadius * 2).toInt())
                }else {
                    return intArrayOf((txtRawWidth + padding * 2 + dx * 2 +arrow_height+ shadowRadius * 2).toInt(),
                            (txtRawHeight + padding * 2+ dy * 2 + shadowRadius * 2).toInt())
                }

            }
            else -> {
                remainingWidth = context.screenWidth()-array[0]- anchor.measuredWidth-arrow_height
                val exceeedingLimit:Boolean
                if (txtRawWidth>remainingWidth-padding * 2 - dx * 2 -arrow_height- shadowRadius * 2){ // 气泡在锚点右侧，剩余的空间已经摆不下了，需要换行
                    exceeedingLimit = true
                    layout = StaticLayout(content,textPaint,remainingWidth
                            ,Layout.Alignment.ALIGN_NORMAL,
                            1.1f,1.0f,true)
                }else {
                    exceeedingLimit = false
                    layout = StaticLayout(content, textPaint, txtRawWidth.toInt(),
                            Layout.Alignment.ALIGN_NORMAL,
                            1.1f, 1.0f,
                            true)
                }
                val txtRawHeight = (layout as StaticLayout).height.toFloat()
                if (exceeedingLimit){
                    return  intArrayOf((remainingWidth),
                            (txtRawHeight + padding * 2+ dy * 2 + shadowRadius * 2).toInt())
                }else{
                    return  intArrayOf((txtRawWidth + padding * 2 + dx * 2 +arrow_height+ shadowRadius * 2).toInt(),
                            (txtRawHeight + padding * 2+ dy * 2 + shadowRadius * 2).toInt())
                }


            }
        }

    }

    fun verticalPadding():Int{
        return (padding+dy+shadowRadius).toInt()
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

//    fun anchor(view: View?) {
//        this.anchor = view
//    }

    fun arrowOffset(offset:Int){
        this.arrowOffset= offset
    }

    //如果不设定arrowOffset，默认让箭头指向锚点中心， 比如在锚点下方就指向锚点底部边的中点
    private fun calculateArrowOffset(): Int {
//        anchor?.let {
            if (this.arrowOffset>0) {
                return arrowOffset
            }
        var array = IntArray(2)
        anchor!!.getLocationOnScreen(array)

            when (layoutRule) {
                RelativeLayout.BELOW -> {
//                    return (size[0]-arrow_width)/2
                    val arrowAbsPosition = array[0]+anchor!!.measuredWidth/2//箭头相对于屏幕左侧的绝对距离
                    return arrowAbsPosition-(context.screenWidth()-measuredWidth)/2
                }
                RelativeLayout.ABOVE -> {
                    val arrowAbsPosition = array[0]+anchor!!.measuredWidth/2 //箭头相对于屏幕左侧的绝对距离
                    return arrowAbsPosition-(context.screenWidth()-measuredWidth)/2
                }
                RelativeLayout.LEFT_OF -> {
                    return   (array[1]-arrow_width)/2
                }
                RelativeLayout.RIGHT_OF -> {
                    return   (array[1]-arrow_width)/2
                }
        }
        return 0
    }






    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val arr = calculateMeasureSize()


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

        var height: Float
        var width: Float

        path.reset()      // 箭头的path
        pathRect.reset()    //圆角矩形的path

        when (layoutRule){
            RelativeLayout.LEFT_OF ->{
                height = measuredHeight.toFloat()-dy*2-shadowRadius*2
                width = measuredWidth.toFloat()-dx*2-shadowRadius*2-arrow_height
                roundRect.set(left.toFloat()-padding,dy,dx+measuredWidth-arrow_height,dy+height)
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
                roundRect.set(dx, dy,dx+width,dy+height-arrow_height)
              path.moveTo(dx+arrowOffset, (height-arrow_height))
              path.lineTo(dx+arrowOffset+arrow_width/2, height)
              path.lineTo(dx+arrowOffset+arrow_width, (height-arrow_height))
            }
            RelativeLayout.RIGHT_OF -> {
                 height = measuredHeight.toFloat()-dy*2-shadowRadius*2
                 width = measuredWidth.toFloat()-dx*2-shadowRadius*2-arrow_height
                        roundRect.set(dx+arrow_height,dy,dx+width+arrow_height,dy+height)
            }
        }
        pathRect.addRoundRect(roundRect, rectRadius, rectRadius, Path.Direction.CCW)




        path.close()
//
        path.op(pathRect, Path.Op.UNION)
       
        canvas.drawPath(path, paint)
        drawText(canvas)
    }


    fun drawText(canvas: Canvas) {

        when(layoutRule){
            RelativeLayout.ABOVE-> {
             canvas.translate(padding, padding-arrow_height/2)
            }
            RelativeLayout.BELOW -> {
              canvas.translate(padding, padding+arrow_height/4)

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