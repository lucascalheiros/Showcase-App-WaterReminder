package com.github.lucascalheiros.waterremindermvp.feature.settings.ui.managenotifications.adapters.notificationtime.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.github.lucascalheiros.waterremindermvp.common.ui.helpers.ContextualPosition
import com.github.lucascalheiros.waterremindermvp.common.ui.helpers.setSurfaceListBackground
import com.github.lucascalheiros.waterremindermvp.common.ui.helpers.showDivider
import com.github.lucascalheiros.waterremindermvp.feature.settings.databinding.ListItemNotificationTimeBinding
import com.github.lucascalheiros.waterremindermvp.feature.settings.ui.managenotifications.adapters.notificationtime.NotificationTimeSection
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toJavaLocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class NotificationTimeViewHolder(
    private val binding: ListItemNotificationTimeBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: NotificationTimeSection.Content.Item,
        onRemoveScheduleClick: () -> Unit
    ) {
        with(binding) {
            tvTime.text = LocalTime.fromSecondOfDay(item.dayTime.daySeconds).toJavaLocalTime().format(
                DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
            )
            ibRemove.setOnClickListener {
                onRemoveScheduleClick()
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
                ),
            )
    }
}
