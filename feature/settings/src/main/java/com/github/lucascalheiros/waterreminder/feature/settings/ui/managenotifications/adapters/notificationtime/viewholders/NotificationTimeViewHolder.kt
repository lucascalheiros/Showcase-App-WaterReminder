package com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.notificationtime.viewholders

import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.github.lucascalheiros.waterreminder.common.ui.helpers.ContextualPosition
import com.github.lucascalheiros.waterreminder.common.ui.helpers.setSurfaceListBackground
import com.github.lucascalheiros.waterreminder.common.ui.helpers.showDivider
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDay
import com.github.lucascalheiros.waterreminder.feature.settings.databinding.ListItemNotificationTimeBinding
import com.github.lucascalheiros.waterreminder.feature.settings.ui.helpers.formatWeekDaysDisplayText
import com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.notificationtime.NotificationTimeSection
import com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.notificationtime.WeekdayState
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toJavaLocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class NotificationTimeViewHolder(
    private val binding: ListItemNotificationTimeBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: NotificationTimeSection.Content.Item,
        listener: NotificationTimeViewHolderListener
    ) {
        with(binding) {
            setupTransition()
            tvTime.text = item.dayTime.formatShort()
            tvWeekdays.text = item.weekdaysState.formatValidWeekDaysUnderlined()
            rbSelected.isVisible = item.selectionMode
            rbSelected.isChecked = item.isSelected
            val clickListener = View.OnClickListener {
                if (item.selectionMode) {
                    listener.onItemClick()
                    rbSelected.isChecked = !rbSelected.isChecked
                }
            }
            tvWeekdays.setOnClickListener {
                listener.onNotificationDaysClick()
            }
            rbSelected.setOnClickListener(clickListener)
            root.setOnClickListener(clickListener)
            root.setOnLongClickListener {
                listener.onItemLongPress()
                rbSelected.isChecked = !rbSelected.isChecked
                true
            }
        }
    }

    fun updateContextualUI(contextualPosition: ContextualPosition) {
        binding.root.setSurfaceListBackground(contextualPosition)
        binding.divider.isVisible = contextualPosition.showDivider
    }

    private fun DayTime.formatShort(): String {
        return LocalTime(hour, minute).toJavaLocalTime().format(
            DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
        )
    }

    private fun List<WeekdayState>.formatValidWeekDaysUnderlined(): SpannableString {
        return mapNotNull { if (it.enabled) it.weekDay else null }
            .formatWeekDaysDisplayText(itemView.context)
            .let {
                SpannableString(it).apply {
                    setSpan(UnderlineSpan(), 0, it.length, 0)
                }
            }
    }

    private fun ListItemNotificationTimeBinding.setupTransition() {
        val transition = AutoTransition()
        transition.setDuration(500)
        transition.setOrdering(TransitionSet.ORDERING_TOGETHER)
        TransitionManager.beginDelayedTransition(root, transition)
    }

    interface NotificationTimeViewHolderListener {
        fun onNotificationDaysClick()
        fun onItemLongPress()
        fun onItemClick()
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
