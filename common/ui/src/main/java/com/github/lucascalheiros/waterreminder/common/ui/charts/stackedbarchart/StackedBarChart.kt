package com.github.lucascalheiros.waterreminder.common.ui.charts.stackedbarchart

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Space
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.lucascalheiros.waterreminder.common.ui.databinding.ViewStackedBarChartBinding
import com.google.android.material.textview.MaterialTextView


class StackedBarChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ViewStackedBarChartBinding.inflate(LayoutInflater.from(context), this)

    var verticalRuleSteps = 4
        set(value) {
            field = value
            updateVerticalRule()
        }

    private fun updateVerticalRule() {
        binding.llLeftRule.removeAllViews()
        val maxValue = binding.llBarChartArea.maxChartValue
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

    fun setTooltipStackViewProvider(tooltipViewProvider: (StackBarColumn) -> View?) {
        binding.llBarChartArea.setTooltipStackViewProvider(tooltipViewProvider)
    }

    fun setMaxCustomValue(value: Double, animate: Boolean = true) {
        binding.llBarChartArea.setMaxCustomValue(value, animate)
        updateVerticalRule()
    }

    fun setStackBarColumns(data: List<StackBarColumn>, animate: Boolean = true) {
        binding.llBarChartArea.setStackBarColumns(data, animate)
        updateVerticalRule()
    }

    fun setLineValue(value: Double?) {
        binding.llBarChartArea.lineValue = value
        updateVerticalRule()
    }

    fun setHorizontalRuleConfig(config: HorizontalChartRule.HorizontalChartRuleConfig) {
        binding.llBottomRule.setHorizontalChartRuleConfig(config)
    }

    private fun flexSpace(): Space {
        return Space(context).apply {
            layoutParams = LinearLayout.LayoutParams(0, 0, 1f)
        }
    }
}
