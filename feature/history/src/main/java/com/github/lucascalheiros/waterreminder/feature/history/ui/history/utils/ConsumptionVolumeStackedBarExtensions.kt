package com.github.lucascalheiros.waterreminder.feature.history.ui.history.utils

import android.content.Context
import android.view.LayoutInflater
import com.github.lucascalheiros.waterreminder.common.appcore.format.shortValueAndUnitFormatted
import com.github.lucascalheiros.waterreminder.common.ui.charts.stackedbarchart.StackBarColumn
import com.github.lucascalheiros.waterreminder.common.ui.charts.stackedbarchart.StackData
import com.github.lucascalheiros.waterreminder.common.ui.getThemeAwareColor
import com.github.lucascalheiros.waterreminder.feature.history.databinding.TooltipStackbarIntakeBinding
import com.github.lucascalheiros.waterreminder.domain.history.domain.models.ChartOption
import com.github.lucascalheiros.waterreminder.domain.history.domain.models.ConsumptionVolume
import kotlinx.datetime.Month
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.format.TextStyle
import java.util.Locale

val ConsumptionVolume.stackBarIdentifier: String
    get() = when (this) {
        is ConsumptionVolume.FromDay -> date.toEpochDays().toString()
        is ConsumptionVolume.FromMonth -> yearAndMonth.toString()
    }

fun ConsumptionVolume.toStackedColumn(
    context: Context,
    chartPeriodOption: ChartOption
): StackBarColumn {
    return StackBarColumn(
        stackBarIdentifier,
        consumptionVolumeByType.map {
            StackData(it.waterSourceType.run {
                context.getThemeAwareColor(lightColor, darkColor).toInt()
            }, it.volume.intrinsicValue())
        },
        getBarLabel(chartPeriodOption)
    )
}

private fun ConsumptionVolume.getBarLabel(chartPeriodOption: ChartOption) = when (this) {
    is ConsumptionVolume.FromDay -> date.let {
        if (chartPeriodOption == ChartOption.Week)
            date.dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.getDefault())
        else it.dayOfMonth.toString()
    }

    is ConsumptionVolume.FromMonth -> Month(yearAndMonth.month).getDisplayName(TextStyle.NARROW, Locale.getDefault())
}

fun ConsumptionVolume.createTooltipView(context: Context) =
    TooltipStackbarIntakeBinding.inflate(LayoutInflater.from(context)).also { tooltipBinding ->
        tooltipBinding.tvDate.text = tooltipDateTitle()
        tooltipBinding.tvIntake.text = tooltipConsumptionDescription(context)
    }.root

private fun ConsumptionVolume.tooltipDateTitle() = when (this) {
    is ConsumptionVolume.FromDay -> {
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
        date.toJavaLocalDate().format(formatter)
    }

    is ConsumptionVolume.FromMonth ->  Month(yearAndMonth.month).getDisplayName(TextStyle.FULL, Locale.getDefault())
}

private fun ConsumptionVolume.tooltipConsumptionDescription(context: Context) =
    consumptionVolumeByType.joinToString("\n") {
        "%s %s".format(
            it.waterSourceType.name,
            it.volume.shortValueAndUnitFormatted(context)
        )
    }
