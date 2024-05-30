package com.github.lucascalheiros.waterremindermvp.feature.settings.ui.managenotifications.adapters.weekdaysswitch.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.github.lucascalheiros.waterremindermvp.common.ui.helpers.ContextualPosition
import com.github.lucascalheiros.waterremindermvp.common.ui.helpers.setSurfaceListBackground
import com.github.lucascalheiros.waterremindermvp.common.ui.helpers.showDivider
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models.WeekDay
import com.github.lucascalheiros.waterremindermvp.feature.settings.R
import com.github.lucascalheiros.waterremindermvp.feature.settings.databinding.ListItemWeekDaySwitchBinding
import com.github.lucascalheiros.waterremindermvp.feature.settings.ui.managenotifications.adapters.weekdaysswitch.WeekDaySwitchSection

class WeekDaysSwitchItemViewHolder(
    private val binding: ListItemWeekDaySwitchBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: WeekDaySwitchSection.Item,
        onWeekDayNotificationStateChange: (WeekDay, Boolean) -> Unit
    ) {
        with(binding) {
            weekName.setText(item.weekDay.displayText())
            switchWeekDayEnabled.isChecked = item.enabled
            switchWeekDayEnabled.setOnClickListener {
                onWeekDayNotificationStateChange(item.weekDay, !item.enabled)
            }
        }
    }

    fun updateContextualUI(contextualPosition: ContextualPosition) {
        binding.root.setSurfaceListBackground(contextualPosition)
        binding.divider.isVisible = contextualPosition.showDivider
    }

    companion object {
        fun inflate(parent: ViewGroup): WeekDaysSwitchItemViewHolder =
            WeekDaysSwitchItemViewHolder(
                ListItemWeekDaySwitchBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
            )
    }
}

private fun WeekDay.displayText(): Int {
    return when (this) {
        WeekDay.Sunday -> R.string.sunday
        WeekDay.Monday -> R.string.monday
        WeekDay.Tuesday -> R.string.tuesday
        WeekDay.Wednesday -> R.string.wednesday
        WeekDay.Thursday -> R.string.thursday
        WeekDay.Friday -> R.string.friday
        WeekDay.Saturday -> R.string.saturday
    }
}
