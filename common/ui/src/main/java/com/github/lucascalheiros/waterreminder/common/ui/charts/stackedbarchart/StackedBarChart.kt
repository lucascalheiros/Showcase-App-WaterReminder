package com.github.lucascalheiros.waterreminder.common.ui.charts.stackedbarchart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Space
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.lucascalheiros.waterreminder.common.ui.R
import com.github.lucascalheiros.waterreminder.common.ui.databinding.ViewStackedBarChartBinding
import com.google.android.material.textview.MaterialTextView
import kotlin.math.max


class StackedBarChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ViewStackedBarChartBinding.inflate(LayoutInflater.from(context), this)

    private val stackViewsMap = mutableMapOf<Int, StackedBarView>()

    private var stackBarColumns: List<StackBarColumn> = listOf()

    private var maxCustomValue: Double = 0.0

    private val maxColumnValue: Double
        get() = stackBarColumns.maxOfOrNull { it.columnValue } ?: 0.0

    private val maxChartValue: Double
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

    var verticalRuleSteps = 4
        set(value) {
            field = value
            updateVerticalRule()
        }

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

        val heightDensityPerValue = binding.llBarChartArea.height / maxChartValue

        binding.llBarChartArea.getHitRect(chartRectBounds)

        val lineHeight =
            binding.llBarChartArea.height - (lineValue * heightDensityPerValue).toFloat()

        canvas.drawLine(
            chartRectBounds.left.toFloat(),
            lineHeight,
            chartRectBounds.right.toFloat(),
            lineHeight,
            linePaint
        )
    }

    fun setTooltipStackViewProvider(tooltipViewProvider: (StackBarColumn) -> View?) {
        this.tooltipViewProvider = tooltipViewProvider
    }

    fun setMaxCustomValue(value: Double, animate: Boolean = true) {
        maxCustomValue = value
        updateStackedBars(animate)
        updateVerticalRule()
    }

    fun setStackBarColumns(data: List<StackBarColumn>, animate: Boolean = true) {
        this.stackBarColumns = data
        updateStackedBars(animate)
        updateHorizontalRule()
        updateVerticalRule()
    }

    private fun setupTooltipClickListener() {
        stackViewsMap.entries.forEachIndexed { index, mutableEntry ->
            val stackView = mutableEntry.value
            stackView.setOnClickListener {
                val stackBarColumn = stackView.stackBarColumn ?: return@setOnClickListener
                val tooltipViewProvider = tooltipViewProvider ?: return@setOnClickListener
                val view = tooltipViewProvider(stackBarColumn) ?: return@setOnClickListener
                view.measure(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                val popup = PopupWindow(
                    view, view.measuredWidth, view.measuredHeight, true
                )
                focusedIndex = index
                popup.showAsDropDown(stackView)
                popup.setOnDismissListener {
                    focusedIndex = null
                }
            }
        }
    }

    private fun updateHorizontalRule() {
        binding.llBottomRule.removeAllViews()
        binding.llBottomRule.addView(flexSpace())
        stackBarColumns.forEach { stackBarColumn ->
            binding.llBottomRule.addView(MaterialTextView(context).apply {
                text = stackBarColumn.label
                textSize = 10f
            })
            binding.llBottomRule.addView(flexSpace())
        }
    }

    private fun updateVerticalRule() {
        binding.llLeftRule.removeAllViews()
        val maxValue = maxChartValue
        (0..verticalRuleSteps).forEach { pos ->
            if (pos != 0) {
                binding.llLeftRule.addView(flexSpace())
            }
            binding.llLeftRule.addView(MaterialTextView(context).apply {
                layoutParams =
                    LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                text = (maxValue - (maxValue / verticalRuleSteps) * pos).toInt().toString()
                textSize = 10f
            })
        }
    }

    private fun updateStackedBars(animate: Boolean) {
        val llBarChartArea = binding.llBarChartArea
        llBarChartArea.removeAllViews()
        llBarChartArea.addView(flexSpace())
        stackBarColumns.forEachIndexed { index, stackBarColumn ->
            val stack = stackViewsMap.getOrPut(index) {
                StackedBarView(context)
            }.apply {
                setMaxValue(maxChartValue, animate)
                setStackData(stackBarColumn, animate)
                layoutParams = LayoutParams(50 + (innerPaddingTouchHelper * 2).toInt(), LayoutParams.MATCH_PARENT)
            }
            llBarChartArea.addView(stack)
            llBarChartArea.addView(flexSpace())
        }
        setupTooltipClickListener()
    }

    private fun flexSpace(): Space {
        return Space(context).apply {
            layoutParams = LinearLayout.LayoutParams(0, 0, 1f)
        }
    }
}
