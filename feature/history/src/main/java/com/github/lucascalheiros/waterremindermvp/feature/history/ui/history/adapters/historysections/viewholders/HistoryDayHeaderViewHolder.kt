package com.github.lucascalheiros.waterremindermvp.feature.history.ui.history.adapters.historysections.viewholders

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.lucascalheiros.waterremindermvp.common.appcore.format.shortValueAndUnitFormatted
import com.github.lucascalheiros.waterremindermvp.common.ui.ColorAndPercentage
import com.github.lucascalheiros.waterremindermvp.common.ui.getThemeAwareColor
import com.github.lucascalheiros.waterremindermvp.common.util.date.todayLocalDate
import com.github.lucascalheiros.waterremindermvp.feature.history.R
import com.github.lucascalheiros.waterremindermvp.feature.history.databinding.ListItemDayHeaderSummaryBinding
import com.github.lucascalheiros.waterremindermvp.feature.history.ui.history.adapters.historysections.HistorySections
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class HistoryDayHeaderViewHolder(private val binding: ListItemDayHeaderSummaryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: HistorySections.DayHeader) {
        with(binding) {
            tvHeaderTitle.text = item.summary.date.formatDayHeaderTitle(root.context)
            tvPercentage.text = root.context.getString(
                com.github.lucascalheiros.waterremindermvp.common.appcore.R.string.percent_value,
                item.summary.percentage.toInt()
            )
            tvVolume.text = item.summary.intake.shortValueAndUnitFormatted(root.context)
            coloredCircleChart.setColorAndPercentages(item.summary.consumptionPercentageByType.map {
                val color = it.waterSourceType.run {
                    root.context.getThemeAwareColor(lightColor, darkColor)
                }.toInt()
                ColorAndPercentage(color, it.percentage)
            })
        }
    }

    companion object {
        fun inflate(parent: ViewGroup): HistoryDayHeaderViewHolder = HistoryDayHeaderViewHolder(
            ListItemDayHeaderSummaryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}

private fun LocalDate.formatDayHeaderTitle(context: Context): String {
    val today = todayLocalDate()
    return when (this) {
        today -> context.resources.getString(R.string.today)
        today.minus(1, DateTimeUnit.DAY) -> context.resources.getString(R.string.yesterday)
        else -> {
            val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
            toJavaLocalDate().format(formatter)
        }
    }
}