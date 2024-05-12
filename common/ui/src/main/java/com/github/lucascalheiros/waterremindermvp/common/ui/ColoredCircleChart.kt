package com.github.lucascalheiros.waterremindermvp.common.ui

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class ColoredCircleChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint: Paint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = this@ColoredCircleChart.strokeWidth
        }
    }

    private val rectF: RectF by lazy { RectF() }

    private val strokeWidth: Float

    private val baseStrokeColor: Int

    private var colorAndPercentageMap = mutableMapOf<Int, Float>()

    private var animator: ValueAnimator? = null

    private var centerX = 0f

    private var centerY = 0f

    private var radius = 0f

    init {
        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.ColoredCircleChart, 0, 0)
        strokeWidth = typedArray.getDimension(R.styleable.ColoredCircleChart_strokeWidth, 50F)
        baseStrokeColor = typedArray.getColor(R.styleable.ColoredCircleChart_baseStrokeColor, Color.WHITE)
        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.color = baseStrokeColor
        canvas.drawCircle(centerX, centerY, radius, paint)

        colorAndPercentageMap.forEach {
            paint.color = it.key
            val sweepAngle = it.value * 360
            canvas.drawArc(
                rectF,
                START_ANGLE,
                sweepAngle,
                false,
                paint
            )
        }
    }

    override fun onSizeChanged(
        w: Int,
        h: Int,
        oldw: Int,
        oldh: Int
    ) {
        super.onSizeChanged(w, h, oldw, oldh)
        rectF[(strokeWidth / 2), (strokeWidth / 2), (w - strokeWidth / 2)] = (h - strokeWidth / 2)
        centerX = width / 2f
        centerY = height / 2f
        radius = (min(centerX.toDouble(), centerY.toDouble()) - strokeWidth / 2f).toFloat()
    }

    fun setColorAndPercentages(newValues: List<ColorAndPercentage>) {
        animator?.cancel()
        _setColorAndPercentages(newValues)
    }

    fun animateColorAndPercentages(newValues: List<ColorAndPercentage>, duration: Long = 1000) {
        val newColorMap = newValues.associateBy { it.color }.mapValues { it.value.percentage }
        animator?.cancel()
        animator = ValueAnimator.ofFloat(0f, 1f).apply {
            setDuration(duration)
            addUpdateListener { animation: ValueAnimator ->
                val fraction = animation.animatedFraction
                val colors = (colorAndPercentageMap.keys + newValues.map { it.color }).toSet()
                colors.map {
                    val currentPercentage = colorAndPercentageMap[it] ?: 0f
                    val newPercentage = newColorMap[it] ?: 0f
                    val interpolatedPercentage = (currentPercentage + fraction * (newPercentage - currentPercentage))
                    ColorAndPercentage(it, interpolatedPercentage)
                }.let {
                    _setColorAndPercentages(it)
                }
            }
            start()
        }
    }

    private fun _setColorAndPercentages(newValues: List<ColorAndPercentage>) {
        colorAndPercentageMap = newValues.associateBy { it.color }.mapValues { it.value.percentage }.toMutableMap()
        invalidate()
    }

    companion object {
        private const val START_ANGLE = -90f
    }
}

data class ColorAndPercentage(
    val color: Int,
    val percentage: Float
)