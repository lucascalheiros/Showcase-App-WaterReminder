package com.github.lucascalheiros.waterreminder.common.ui.slider

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import com.github.lucascalheiros.waterreminder.common.ui.R

class ColorSelectionSlider @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var currentValue: Double = 0.0
        set(value) {
            if (field != value) {
                field = value
                onColorChange(value)
                invalidate()
            }
        }

    var colorFromValue: (Double) -> Int = {
        val hue = it.toFloat() * 360f
        val hsv = FloatArray(3)
        hsv[0] = hue
        hsv[1] = 1f
        hsv[2] = 1f
        Color.HSVToColor(hsv)
    }
        set(value) {
            if (field != value) {
                field = value
                invalidate()
            }
        }

    var onColorChange: (Double) -> Unit = {}

    var granularityTrackSteps = 360
        set(value) {
            if (field != value) {
                field = value
                invalidate()
            }
        }

    var trackHeight: Float = 50f
        set(value) {
            if (field != value) {
                field = value
                trackPaint.strokeWidth = value
                invalidate()
            }
        }

    var trackHandleHeight: Float = 50f
        set(value) {
            if (field != value) {
                field = value
                invalidate()
            }
        }

    var trackHandleWidth: Float = 50f
        set(value) {
            if (field != value) {
                field = value
                trackHandlePaint.strokeWidth = value
                invalidate()
            }
        }

    @ColorInt var trackHandleColor: Int = Color.BLACK
        set(value) {
            if (field != value) {
                field = value
                trackHandlePaint.color = value
                invalidate()
            }
        }

    private val trackPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        strokeCap = Paint.Cap.SQUARE
    }

    private val trackHandlePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLACK
        strokeCap = Paint.Cap.ROUND
    }

    private val clipEdgesPath: Path = Path()

    private val trackYCenter: Float
        get() = trackHandleHeight / 2 + verticalPadding

    private val trackWidth: Float
        get() = width.toFloat() - horizontalPadding * 2

    private val trackXStart: Float
        get() = horizontalPadding

    private val trackXEnd: Float
        get() = trackWidth + trackXStart

    private val horizontalPadding: Float
        get() = trackHeight

    private val verticalPadding: Float
        get() = trackHandleWidth

    init {
        trackHeight = resources.getDimension(R.dimen.color_selection_slider_track_height)
        trackHandleHeight = resources.getDimension(R.dimen.color_selection_slider_handle_height)
        trackHandleWidth = resources.getDimension(R.dimen.color_selection_slider_handle_width)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorSelectionSlider)
        try {
            trackHandleColor = typedArray.getColor(R.styleable.ColorSelectionSlider_handleColor, Color.BLACK)
        } finally {
            typedArray.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        val radius = trackHeight / 2
        clipEdgesPath.reset()
        clipEdgesPath.arcTo(
            trackXStart, trackYCenter - radius, trackXStart + 2 * radius, trackYCenter + radius,
            90f, 180F, false
        )
        clipEdgesPath.arcTo(
            trackXEnd - 2 * radius, trackYCenter - radius, trackXEnd, trackYCenter + radius,
            270f, 180F, false
        )
        clipEdgesPath.close()
        canvas.clipPath(clipEdgesPath)
        (0..granularityTrackSteps).reversed().forEach {
            trackPaint.color = colorFromValue(MAX_VALUE / granularityTrackSteps * it)
            val widthStep = (trackXEnd - trackXStart) / granularityTrackSteps
            val xPositionStepStart = widthStep * it
            canvas.drawRect(
                trackXStart + xPositionStepStart - widthStep,
                trackYCenter - trackHeight / 2,
                trackXStart + xPositionStepStart + widthStep,
                trackYCenter + trackHeight / 2,
                trackPaint
            )
        }
        canvas.restore()
        val xPos = trackXStart + trackWidth * currentValue.toFloat()
        canvas.drawLine(
            xPos,
            trackYCenter - trackHandleHeight / 2,
            xPos,
            trackYCenter + trackHandleHeight / 2,
            trackHandlePaint
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            widthMeasureSpec,
            MeasureSpec.makeMeasureSpec(
                (trackHandleHeight + verticalPadding * 2).toInt(),
                MeasureSpec.EXACTLY
            )
        )
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!isEnabled) {
            return false
        }
        val x = event?.x ?: return false
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> run {
                currentValue = (x / width).toDouble().coerceIn(MIN_VALUE, MAX_VALUE)
            }

            MotionEvent.ACTION_MOVE -> {
                currentValue = (x / width).toDouble().coerceIn(MIN_VALUE, MAX_VALUE)
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {

                invalidate()
            }

            else -> {}
        }

        return true
    }

    companion object {
        const val MIN_VALUE = 0.0
        const val MAX_VALUE = 1.0
    }

}