package com.github.lucascalheiros.waterremindermvp.feature.history.ui.history.adapters.historysections

import androidx.recyclerview.widget.DiffUtil
import com.github.lucascalheiros.waterremindermvp.feature.history.ui.history.adapters.historysections.HistorySectionsAdapter.Companion.UPDATE_CHART_PAYLOAD
import com.github.lucascalheiros.waterremindermvp.feature.history.ui.history.adapters.historysections.HistorySectionsAdapter.Companion.UPDATE_DAY_HEADER_PAYLOAD

object HistorySectionsDiffCallback : DiffUtil.ItemCallback<HistorySections>() {
    override fun areItemsTheSame(oldItem: HistorySections, newItem: HistorySections): Boolean {
        return when {
            oldItem is HistorySections.Title && newItem is HistorySections.Title -> true

            oldItem is HistorySections.DayHeader && newItem is HistorySections.DayHeader -> oldItem.summary.date == newItem.summary.date

            oldItem is HistorySections.ConsumedWaterItem && newItem is HistorySections.ConsumedWaterItem -> oldItem.consumedWater.consumedWaterId == newItem.consumedWater.consumedWaterId

            oldItem is HistorySections.ConsumptionChart && newItem is HistorySections.ConsumptionChart -> true

            else -> false
        }
    }

    override fun areContentsTheSame(
        oldItem: HistorySections, newItem: HistorySections
    ): Boolean {
        return when {
            oldItem is HistorySections.Title && newItem is HistorySections.Title -> true

            oldItem is HistorySections.DayHeader && newItem is HistorySections.DayHeader -> oldItem == newItem

            oldItem is HistorySections.ConsumedWaterItem && newItem is HistorySections.ConsumedWaterItem -> oldItem == newItem

            oldItem is HistorySections.ConsumptionChart && newItem is HistorySections.ConsumptionChart -> {
                oldItem.volumeIntake == newItem.volumeIntake && oldItem.consumptionVolumeFromDay.toSet()
                    .intersect(newItem.consumptionVolumeFromDay.toSet()).size == oldItem.consumptionVolumeFromDay.size
            }

            else -> false
        }
    }

    override fun getChangePayload(oldItem: HistorySections, newItem: HistorySections): Any? {
        return when {
            oldItem is HistorySections.DayHeader && newItem is HistorySections.DayHeader -> UPDATE_DAY_HEADER_PAYLOAD

            oldItem is HistorySections.ConsumptionChart && newItem is HistorySections.ConsumptionChart -> UPDATE_CHART_PAYLOAD

            else -> super.getChangePayload(oldItem, newItem)

        }
    }
}