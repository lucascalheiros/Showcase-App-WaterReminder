package com.github.lucascalheiros.waterreminder.common.ui.charts.stackedbarchart

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.textview.MaterialTextView

class HorizontalChartRule @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private var config: HorizontalChartRuleConfig? = null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(resolveSizeAndState(0, widthMeasureSpec, 0), resolveSizeAndState(0, heightMeasureSpec, 0))
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val config = config ?: return
        val labels = config.labels
        val spaceItems = config.totalItems * 2 + 1
        val spaceWidth = width / spaceItems
        for (i in 0 until childCount) {
            val label = labels.getOrNull(i) ?: return
            val child = getChildAt(i)
            val width = child.measuredWidth
            val startPos = spaceWidth * (2 * label.position + 1) + spaceWidth / 2
            child.layout(startPos - width / 2, 0, startPos + width / 2, child.measuredHeight)
        }
    }

    fun setHorizontalChartRuleConfig(horizontalChartRuleConfig: HorizontalChartRuleConfig) {
        config = horizontalChartRuleConfig
        removeAllViews()
        horizontalChartRuleConfig.labels.map { label ->
            MaterialTextView(context).also {
                it.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
                it.textSize = 10f
                it.text = label.label
                it.gravity = Gravity.CENTER
                addView(it)
            }
        }
    }

    data class HorizontalChartRuleConfig(
        val totalItems: Int,
        val labels: List<LabelData>
    )

    data class LabelData(
        val position: Int,
        val label: String
    )
}
