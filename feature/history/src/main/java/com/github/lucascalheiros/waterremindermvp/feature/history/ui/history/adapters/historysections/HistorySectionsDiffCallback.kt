package com.github.lucascalheiros.waterremindermvp.feature.history.ui.history.adapters.historysections

import androidx.recyclerview.widget.DiffUtil

object HistorySectionsDiffCallback : DiffUtil.ItemCallback<HistorySections>() {
   override fun areItemsTheSame(oldItem: HistorySections, newItem: HistorySections): Boolean {
       return when {
           oldItem is HistorySections.Title && newItem is HistorySections.Title -> true
           oldItem is HistorySections.DayHeader && newItem is HistorySections.DayHeader ->
               oldItem.summary.date == newItem.summary.date

           oldItem is HistorySections.ConsumedWaterItem && newItem is HistorySections.ConsumedWaterItem ->
               oldItem.consumedWater.consumedWaterId == newItem.consumedWater.consumedWaterId

           else -> false
       }
   }

   override fun areContentsTheSame(
       oldItem: HistorySections,
       newItem: HistorySections
   ): Boolean {
       return when {
           oldItem is HistorySections.Title && newItem is HistorySections.Title -> true
           oldItem is HistorySections.DayHeader && newItem is HistorySections.DayHeader ->
               oldItem == newItem

           oldItem is HistorySections.ConsumedWaterItem && newItem is HistorySections.ConsumedWaterItem ->
               oldItem == newItem

           else -> false
       }
   }
}