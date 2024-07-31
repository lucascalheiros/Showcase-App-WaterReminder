package com.github.lucascalheiros.waterreminder.common.ui.charts.stackedbarchart

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.max

class StackedBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var stackBarColumn: StackBarColumn? = null
        private set

    private val barPaint = Paint()

    var innerPaddingTouchHelper = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var colorToValueMap = mapOf<Int, Double>()

    private var stackBarUpdateAnimator: ValueAnimator? = null

    private var maxValueUpdateAnimator: ValueAnimator? = null

    private var maxValue: Double? = null
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val maxValue = maxValue ?: return
        val totalValue = max(colorToValueMap.values.sum(), maxValue)
        var accumulatedStackHeight = height.toFloat()

        colorToValueMap.entries.forEach { (color, value) ->
            barPaint.color = color
            val stackColorHeight = height * (value / totalValue).toFloat()
            canvas.drawRect(
                0f + innerPaddingTouchHelper,
                accumulatedStackHeight - stackColorHeight,
                width.toFloat() - innerPaddingTouchHelper,
                accumulatedStackHeight,
                barPaint
            )
            accumulatedStackHeight -= stackColorHeight
        }
    }

    fun setMaxValue(value: Double, animate: Boolean = true) {
        maxValueUpdateAnimator?.cancel()
        if (!animate) {
            maxValue = value
            invalidate()
            return
        }
        maxValueUpdateAnimator =
            ValueAnimator.ofFloat(maxValue?.toFloat() ?: 0f, value.toFloat()).apply {
                setDuration(ANIMATION_DURATION)
                addUpdateListener { animation: ValueAnimator ->
                    maxValue = (animation.animatedValue as Float).toDouble()
                    invalidate()
                }
                start()
            }
    }


    fun setStackData(stackBarColumn: StackBarColumn, animate: Boolean = true) {
        this.stackBarColumn = stackBarColumn
        stackBarUpdateAnimator?.cancel()
        if (!animate) {
            colorToValueMap = stackBarColumn.data.associate { it.color to it.value }
            invalidate()
            return
        }
        stackBarUpdateAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            setDuration(ANIMATION_DURATION)
            addUpdateListenerFor(stackBarColumn.data.associate { it.color to it.value })
            start()
        }
    }

    private fun ValueAnimator.addUpdateListenerFor(newColorToValueMap: Map<Int, Double>) {
        addUpdateListener { animation: ValueAnimator ->
            val fraction = animation.animatedFraction
            val colors = colorToValueMap.keys + newColorToValueMap.keys
            colors.associateWith {
                val currentPercentage = colorToValueMap[it] ?: 0.0
                val newPercentage = newColorToValueMap[it] ?: 0.0
                val interpolatedPercentage =
                    (currentPercentage + fraction * (newPercentage - currentPercentage))
                interpolatedPercentage
            }.let {
                colorToValueMap = it
                invalidate()
            }
        }
    }

    companion object {
        private const val ANIMATION_DURATION = 500L
    }
}

