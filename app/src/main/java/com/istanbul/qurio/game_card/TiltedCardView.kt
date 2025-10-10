package com.istanbul.qurio.game_card

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.graphics.withClip

open class TiltedCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val path = Path()
    private val topInsetPx = 4f * resources.displayMetrics.density
    private val bottomInsetPx = 12f * resources.displayMetrics.density
    private val topCutHeight = 0f
    private val bottomCutHeight = 0f

    init {
        setWillNotDraw(false)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.reset()

        val topY = topCutHeight
        val bottomY = h.toFloat() - bottomCutHeight

        path.moveTo(topInsetPx, topY)
        path.lineTo(w.toFloat() - topInsetPx, topY)
        path.lineTo(w.toFloat() - bottomInsetPx, bottomY)
        path.lineTo(bottomInsetPx, bottomY)
        path.close()
    }

    override fun dispatchDraw(canvas: Canvas) {
        canvas.withClip(clipPath = path) {
            super.dispatchDraw(this)
        }
    }
}