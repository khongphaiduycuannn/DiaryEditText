package com.demo.diarytextitem.custom.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.core.content.ContextCompat.getDrawable

class Bullet {

    constructor(context: Context, bulletId: Int, resId: Int) {
        this.context = context
        this.bulletId = bulletId
        mBitmap = getBitmap(context, resId)
    }

    constructor(context: Context, bulletId: Int, bitmap: Bitmap) {
        this.context = context
        this.bulletId = bulletId
        mBitmap = bitmap
    }

    private val context: Context

    val bulletId: Int

    var mBitmap: Bitmap? = null

    fun setBitmap(bitmap: Bitmap) {
        mBitmap = bitmap
    }

    fun setResourceId(resId: Int) {
        mBitmap = getBitmap(context, resId)
    }

    private fun getBitmap(context: Context, resId: Int): Bitmap {
        val drawable: Drawable? = getDrawable(context, resId)
        val bitmap =
            Bitmap.createBitmap(dpToPx(12f).toInt(), dpToPx(12f).toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable?.setBounds(0, 0, canvas.width, canvas.height)
        drawable?.draw(canvas)
        return bitmap
    }

    private fun dpToPx(dp: Float): Float {
        val displayMetrics = Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics)
    }
}