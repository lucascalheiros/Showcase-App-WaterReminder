package com.github.lucascalheiros.waterreminder.common.ui

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat.getColor
import kotlin.math.min

class ColoredCircleChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint: Paint by lazy { circleStrokedPaint() }

    private val squaredArcRect: RectF by lazy { RectF() }

    private val circleStrokeWidth: Float

    private val baseStrokeColor: Int

    private var colorAndPercentageMap = mapOf<Int, Float>()

    private var colorAndAccumulatedPercentageList = listOf<ColorAndPercentage>()

    private var animator: ValueAnimator? = null

    private var circleShapeInfo: CircleShapeInfo? = null

    init {
        val typedArray =
            context.theme.obtainStyledAttributes(attrs,R.styleable.ColoredCircleChart, 0, 0)
        circleStrokeWidth = typedArray.getDimension(R.styleable.ColoredCircleChart_strokeWidth, 50F)
        baseStrokeColor =
            typedArray.getColor(R.styleable.ColoredCircleChart_baseStrokeColor, getColor(context, R.color.white_50_alpha))
        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.color = baseStrokeColor

        circleShapeInfo?.run {
            canvas.drawCircle(centerX, centerY, radius, paint)
        }

        colorAndAccumulatedPercentageList.forEach {
            paint.color = it.color
            val sweepAngle = it.percentage * 360
            canvas.drawArc(
                squaredArcRect,
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
        val halfStroke = circleStrokeWidth / 2
        val minDim = min(w, h)
        squaredArcRect.set(
            halfStroke + (w - minDim) / 2,
            halfStroke + (h - minDim) / 2,
            minDim - halfStroke + (w - minDim) / 2,
            minDim - halfStroke + (h - minDim) / 2
        )
        circleShapeInfo = CircleShapeInfo(
            width / 2f,
            height / 2f,
            circleStrokeWidth
        )
    }

    fun setColorAndPercentages(newValues: List<ColorAndPercentage>) {
        animator?.cancel()
        setNewColorAndPercentagesAndInvalidateDraw(newValues)
    }

    fun setColorAndPercentages(newValues: List<ColorAndPercentage>, animateDuration: Long) {
        val linearAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            setDuration(animateDuration)
        }
        setColorAndPercentages(newValues, linearAnimator)
    }

    fun setColorAndPercentages(newValues: List<ColorAndPercentage>, valueAnimator: ValueAnimator) {
        val newPercentageMap = newValues
            .associateBy { it.color }
            .mapValues { it.value.percentage }
        animator?.cancel()
        animator = valueAnimator.apply {
            addUpdateListenerFor(newPercentageMap)
            start()
        }
    }

    private fun ValueAnimator.addUpdateListenerFor(newPercentageMap: Map<Int, Float>) {
        addUpdateListener { animation: ValueAnimator ->
            val fraction = animation.animatedFraction
            val colors = colorAndPercentageMap.keys + newPercentageMap.keys
            colors.map {
                val currentPercentage = colorAndPercentageMap[it] ?: 0f
                val newPercentage = newPercentageMap[it] ?: 0f
                val interpolatedPercentage =
                    (currentPercentage + fraction * (newPercentage - currentPercentage))
                ColorAndPercentage(it, interpolatedPercentage)
            }.let {
                setNewColorAndPercentagesAndInvalidateDraw(it)
            }
        }
    }

    private fun setNewColorAndPercentagesAndInvalidateDraw(newValues: List<ColorAndPercentage>) {
        colorAndPercentageMap = newValues
            .associateBy { it.color }
            .mapValues { it.value.percentage }
        colorAndAccumulatedPercentageList = newValues.toAccumulatedPercentageList()
        invalidate()
    }

    private fun circleStrokedPaint(): Paint {
        return Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = circleStrokeWidth
        }
    }

    private fun List<ColorAndPercentage>.toAccumulatedPercentageList(): List<ColorAndPercentage> {
        return fold<ColorAndPercentage, List<ColorAndPercentage>>(listOf()) { acc, value ->
            val accumulatedPercentage =  acc.map { it.percentage }.sum()
            val currentPercentage =  value.percentage
            acc + listOf(value.copy(percentage = currentPercentage + accumulatedPercentage))
        }.reversed()
    }

    private data class CircleShapeInfo(
        val centerX: Float,
        val centerY: Float,
        private val strokeWidth: Float
    ) {
        val radius = (min(centerX.toDouble(), centerY.toDouble()) - strokeWidth / 2f).toFloat()
    }

    companion object {
        private const val START_ANGLE = -90f
    }
}

data class ColorAndPercentage(
    val color: Int,
    val percentage: Float
)