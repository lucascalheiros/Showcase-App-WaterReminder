package com.github.lucascalheiros.waterreminder.common.ui.charts.stackedbarchart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.github.lucascalheiros.waterreminder.common.ui.R
import kotlin.math.max


class ChartArea @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val stackViewsMap = mutableMapOf<Int, StackedBarView>()

    private var stackBarColumns: List<StackBarColumn> = listOf()

    private var maxCustomValue: Double = 0.0

    private val maxColumnValue: Double
        get() = stackBarColumns.maxOfOrNull { it.columnValue } ?: 0.0

    val maxChartValue: Double
        get() = max(maxCustomValue, maxColumnValue)

    private var focusedIndex: Int? = null
        set(value) {
            field = value
            stackViewsMap.forEach {
                it.value.alpha = if (value == null || it.key == value) {
                    1f
                } else {
                    0.5f
                }
            }
        }

    private val chartRectBounds = Rect()

    private val linePaint = Paint()

    private var tooltipViewProvider: ((StackBarColumn) -> View?)? = null

    var lineValue: Double? = null
        set(value) {
            field = value
            invalidate()
        }

    init {
        setWillNotDraw(false)
        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.StackedBarChart, 0, 0)
        linePaint.strokeWidth = typedArray.getDimension(R.styleable.StackedBarChart_lineWidth, 5f)
        val typedValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)
        linePaint.color = typedValue.data
        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val lineValue = lineValue ?: return

        val heightDensityPerValue = height / maxChartValue

        getHitRect(chartRectBounds)

        val lineHeight = bottom - (lineValue * heightDensityPerValue).toFloat()

        canvas.drawLine(
            0f,
            lineHeight,
            width.toFloat(),
            lineHeight,
            linePaint
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (childCount == 0) {
            setMeasuredDimension(resolveSizeAndState(0, widthMeasureSpec, 0), resolveSizeAndState(0, heightMeasureSpec, 0))
            return
        }

        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        val childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width / (2 * childCount + 1), MeasureSpec.EXACTLY)
        val childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec)
        }

        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        var currentStart = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val width = child.measuredWidth
            currentStart += width
            child.layout(currentStart, top, currentStart + width, bottom)
            currentStart += width
        }
    }

    fun setTooltipStackViewProvider(tooltipViewProvider: (StackBarColumn) -> View?) {
        this.tooltipViewProvider = tooltipViewProvider
    }

    fun setMaxCustomValue(value: Double, animate: Boolean = true) {
        maxCustomValue = value
        updateStackedBars(animate)
    }

    fun setStackBarColumns(data: List<StackBarColumn>, animate: Boolean = true) {
        this.stackBarColumns = data
        updateStackedBars(animate)
    }

    private fun setupTooltipClickListener() {
        stackViewsMap.entries.forEachIndexed { index, mutableEntry ->
            val stackView = mutableEntry.value
            stackView.setOnClickListener {
                val stackBarColumn = stackView.stackBarColumn ?: return@setOnClickListener
                val tooltipViewProvider = tooltipViewProvider ?: return@setOnClickListener
                val view = tooltipViewProvider(stackBarColumn) ?: return@setOnClickListener
                view.measure(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
                )
                val popup = PopupWindow(
                    view, view.measuredWidth, view.measuredHeight, true
                )
                focusedIndex = index
                popup.showAsDropDown(stackView, -(view.measuredWidth - stackView.width) / 2, 0)
                popup.setOnDismissListener {
                    focusedIndex = null
                }
            }
        }
    }

    private fun updateStackedBars(animate: Boolean) {
        removeAllViews()
        stackBarColumns.forEachIndexed { index, stackBarColumn ->
            val stack = stackViewsMap.getOrPut(index) {
                StackedBarView(context)
            }.apply {
                setMaxValue(maxChartValue, animate)
                setStackData(stackBarColumn, animate)
                layoutParams = LayoutParams(0, LayoutParams.MATCH_PARENT)
            }
            addView(stack)
        }
        setupTooltipClickListener()
    }
}
