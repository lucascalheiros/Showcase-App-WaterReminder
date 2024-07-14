package com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.notificationtime.viewholders

import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.github.lucascalheiros.waterreminder.common.ui.helpers.ContextualPosition
import com.github.lucascalheiros.waterreminder.common.ui.helpers.setSurfaceListBackground
import com.github.lucascalheiros.waterreminder.common.ui.helpers.showDivider
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDay
import com.github.lucascalheiros.waterreminder.feature.settings.databinding.ListItemNotificationTimeBinding
import com.github.lucascalheiros.waterreminder.feature.settings.ui.helpers.formatWeekDaysDisplayText
import com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.notificationtime.NotificationTimeSection
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toJavaLocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class NotificationTimeViewHolder(
    private val binding: ListItemNotificationTimeBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: NotificationTimeSection.Content.Item,
        onNotificationDaysClick: () -> Unit,
        onRemoveScheduleClick: () -> Unit
    ) {
        val weekDayStateMap = item.weekdaysState.associateBy({ it.weekDay }) {
            it.enabled
        }
        with(binding) {
            val time = LocalTime.fromSecondOfDay(item.dayTime.daySeconds).toJavaLocalTime().format(
                DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
            )
            val notificationDaysText = WeekDay.entries.filter { weekDayStateMap[it] != false }.run {
                formatWeekDaysDisplayText(root.context)
            }
            val weekDaysSpan = SpannableString(notificationDaysText)
            weekDaysSpan.setSpan(UnderlineSpan(), 0, notificationDaysText.length, 0)
            tvTime.text = time
            tvWeekdays.text = weekDaysSpan
            ibRemove.setOnClickListener {
                onRemoveScheduleClick()
            }
            tvWeekdays.setOnClickListener {
                onNotificationDaysClick()
            }
        }
    }

    fun updateContextualUI(contextualPosition: ContextualPosition) {
        binding.root.setSurfaceListBackground(contextualPosition)
        binding.divider.isVisible = contextualPosition.showDivider
    }

    companion object {
        fun inflate(parent: ViewGroup): NotificationTimeViewHolder =
            NotificationTimeViewHolder(
                ListItemNotificationTimeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }
}
