package com.me.harris.tipdemo

import android.content.Context


/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 */
fun Context.dip2px(dpValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}


fun Context?.screenWidth() =this?.resources?.displayMetrics?.widthPixels?:0

fun Context?.screenHeight() = this?.resources?.displayMetrics?.heightPixels?:0