package com.github.lucascalheiros.waterremindermvp.feature.history.ui.history.adapters.historysections.viewholders

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.lucascalheiros.waterremindermvp.common.appcore.format.shortValueAndUnitFormatted
import com.github.lucascalheiros.waterremindermvp.common.ui.charts.stackedbarchart.StackBarColumn
import com.github.lucascalheiros.waterremindermvp.common.ui.charts.stackedbarchart.StackData
import com.github.lucascalheiros.waterremindermvp.common.ui.getThemeAwareColor
import com.github.lucascalheiros.waterremindermvp.common.util.date.atStartOfDay
import com.github.lucascalheiros.waterremindermvp.feature.history.databinding.ListItemConsumptionChartBinding
import com.github.lucascalheiros.waterremindermvp.feature.history.databinding.TooltipStackbarIntakeBinding
import com.github.lucascalheiros.waterremindermvp.feature.history.ui.history.adapters.historysections.ConsumptionVolumeFromDay
import com.github.lucascalheiros.waterremindermvp.feature.history.ui.history.adapters.historysections.HistorySections
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

class HistoryConsumptionChartViewHolder(private val binding: ListItemConsumptionChartBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: HistorySections.ConsumptionChart, animate: Boolean) {
        val context = binding.root.context
        val stackBarColumns = item.consumptionVolumeFromDay.map { it.toStackedColumn(context) }
        val expectedIntakeValue = item.volumeIntake.intrinsicValue()
        with(binding.stackedBarChart) {
            lineValue = expectedIntakeValue
            setMaxCustomValue(expectedIntakeValue * 1.1, animate)
            setStackBarColumns(stackBarColumns, animate)
            setTooltipStackViewProvider { column ->
                if (column.data.isEmpty()) {
                    return@setTooltipStackViewProvider  null
                }
                val tooltipBinding =
                    TooltipStackbarIntakeBinding.inflate(LayoutInflater.from(binding.root.context))
                val consumptionVolumeFromDay = item.consumptionVolumeFromDay.find {
                    it.date.toEpochDays().toString() == column.id
                } ?: return@setTooltipStackViewProvider null

                val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                tooltipBinding.tvDate.text =
                    consumptionVolumeFromDay.date.toJavaLocalDate().format(formatter)
                tooltipBinding.tvIntake.text =
                    consumptionVolumeFromDay.consumptionVolumeByType.joinToString("\n") {
                        "%s %s".format(
                            it.waterSourceType.name,
                            it.volume.shortValueAndUnitFormatted(context)
                        )
                    }

                tooltipBinding.root
            }
        }
    }

    companion object {
        fun inflate(parent: ViewGroup): HistoryConsumptionChartViewHolder =
            HistoryConsumptionChartViewHolder(
                ListItemConsumptionChartBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }
}

fun ConsumptionVolumeFromDay.toStackedColumn(context: Context): StackBarColumn {
    return StackBarColumn(
        date.toEpochDays().toString(),
        consumptionVolumeByType.map {
            StackData(it.waterSourceType.run {
                context.getThemeAwareColor(lightColor, darkColor).toInt()
            }, it.volume.intrinsicValue())
        },
        date.let {
            val formatter = DateFormat.getBestDateTimePattern(Locale.getDefault(), "MMd")
            DateFormat.format(formatter, it.atStartOfDay().toEpochMilliseconds()).toString()
        })
}


