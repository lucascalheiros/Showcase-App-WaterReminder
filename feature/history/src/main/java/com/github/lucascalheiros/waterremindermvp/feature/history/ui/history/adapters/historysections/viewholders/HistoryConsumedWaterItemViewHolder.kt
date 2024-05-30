package com.github.lucascalheiros.waterremindermvp.feature.history.ui.history.adapters.historysections.viewholders

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.github.lucascalheiros.waterremindermvp.common.appcore.format.shortValueAndUnitFormatted
import com.github.lucascalheiros.waterremindermvp.common.ui.getThemeAwareColor
import com.github.lucascalheiros.waterremindermvp.common.ui.helpers.ContextualPosition
import com.github.lucascalheiros.waterremindermvp.common.ui.helpers.setSurfaceListBackground
import com.github.lucascalheiros.waterremindermvp.common.ui.helpers.showDivider
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.ConsumedWater
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterremindermvp.feature.history.R
import com.github.lucascalheiros.waterremindermvp.feature.history.databinding.ListItemConsumedWaterItemBinding
import com.github.lucascalheiros.waterremindermvp.feature.history.ui.history.adapters.historysections.HistorySections
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class HistoryConsumedWaterItemViewHolder(
    private val binding: ListItemConsumedWaterItemBinding,
    private val onDeleteItemClick: (ConsumedWater) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HistorySections.ConsumedWaterItem) {
        with(binding) {
            val color = item.consumedWater.waterSourceType.getThemeAwareColor(root.context)
            tvSourceName.setTextColor(color)
            tvTime.setTextColor(color)
            tvVolume.setTextColor(color)
            tvSourceName.text = item.consumedWater.waterSourceType.name
            tvTime.text = item.consumedWater.consumptionTime.formatToShortDate()
            tvVolume.text = item.consumedWater.volume.shortValueAndUnitFormatted(root.context)
            root.setOnLongClickListener {
                it.showMenu(item)
                true
            }
        }
    }

    fun updateContextualUI(contextualPosition: ContextualPosition) {
        binding.root.setSurfaceListBackground(contextualPosition)
        binding.divider.isVisible = contextualPosition.showDivider
    }

    private fun View.showMenu(item: HistorySections.ConsumedWaterItem) {
        val popup = PopupMenu(context, this, Gravity.RIGHT)
        popup.menuInflater.inflate(R.menu.menu_consumed_water_log, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.deleteOpt -> {
                    onDeleteItemClick(item.consumedWater)
                    true
                }

                else -> false
            }
        }

        popup.show()
    }

    companion object {
        fun inflate(
            parent: ViewGroup,
            onDeleteItemClick: (ConsumedWater) -> Unit
        ): HistoryConsumedWaterItemViewHolder =
            HistoryConsumedWaterItemViewHolder(
                ListItemConsumedWaterItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onDeleteItemClick
            )
    }
}

private fun WaterSourceType.getThemeAwareColor(context: Context): Int {
    return context.getThemeAwareColor(lightColor, darkColor).toInt()
}

private fun Long.formatToShortDate(): String {
    return Instant.fromEpochMilliseconds(this).toLocalDateTime(
        TimeZone.currentSystemDefault()
    ).toJavaLocalDateTime().format(
        DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
    )
}