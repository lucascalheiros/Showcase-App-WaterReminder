package com.github.lucascalheiros.waterreminder.feature.history.ui.history.utils

import com.github.lucascalheiros.waterreminder.common.ui.charts.stackedbarchart.HorizontalChartRule
import com.github.lucascalheiros.waterreminder.common.ui.charts.stackedbarchart.StackBarColumn
import com.github.lucascalheiros.waterreminder.domain.history.domain.models.ChartOption

fun List<StackBarColumn>.horizontalChartRuleConfig(
    chartPeriodOption: ChartOption
): HorizontalChartRule.HorizontalChartRuleConfig {
    val labelDataList = when (chartPeriodOption) {
        ChartOption.Month -> listOf(0, 7, 14, 22, size - 1).map { index ->
            HorizontalChartRule.LabelData(
                index,
                get(index).label
            )
        }

        else -> mapIndexedNotNull { index, stackBarColumn ->
            HorizontalChartRule.LabelData(
                index,
                stackBarColumn.label
            )
        }
    }
    return HorizontalChartRule.HorizontalChartRuleConfig(
        size,
        labelDataList
    )
}
