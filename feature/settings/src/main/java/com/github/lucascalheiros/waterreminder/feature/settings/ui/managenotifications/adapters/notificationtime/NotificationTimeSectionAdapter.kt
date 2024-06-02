package com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.notificationtime

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.lucascalheiros.waterreminder.common.ui.helpers.ContextualPosition
import com.github.lucascalheiros.waterreminder.common.ui.helpers.getContextualPosition
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.feature.settings.R
import com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.notificationtime.viewholders.AddNotificationTimeViewHolder
import com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.notificationtime.viewholders.NotificationTimeViewHolder
import com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.viewholders.SectionTitleViewHolder

class NotificationTimeSectionAdapter :
    ListAdapter<NotificationTimeSection, ViewHolder>(DiffCallback) {

    var onAddScheduleClick: () -> Unit = {}
    var onRemoveScheduleClick: (DayTime) -> Unit = {}

    override fun getItemViewType(position: Int): Int {
        return ViewType.from(getItem(position)).value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (ViewType.from(viewType)) {
            ViewType.Title -> SectionTitleViewHolder.inflate(parent)
            ViewType.Item -> NotificationTimeViewHolder.inflate(parent)
            ViewType.AddItem -> AddNotificationTimeViewHolder.inflate(parent)

            null -> throw IllegalStateException("ViewType is not known type")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            NotificationTimeSection.Title -> (holder as? SectionTitleViewHolder)?.bind(R.string.notification_time)

            NotificationTimeSection.Content.AddItem -> (holder as? AddNotificationTimeViewHolder)?.apply {
                holder.bind {
                    onAddScheduleClick()
                }
                updateContextualUI(getContextualInfo(position))
            }

            is NotificationTimeSection.Content.Item -> (holder as? NotificationTimeViewHolder)?.apply {
                bind(item) {
                    onRemoveScheduleClick(item.dayTime)
                }
                updateContextualUI(getContextualInfo(position))
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.contains(UPDATE_CONTEXTUAL_UI_PAYLOAD)) {
            (holder as? NotificationTimeViewHolder)?.updateContextualUI(getContextualInfo(position))
            (holder as? AddNotificationTimeViewHolder)?.updateContextualUI(getContextualInfo(position))
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onCurrentListChanged(
        previousList: MutableList<NotificationTimeSection>,
        currentList: MutableList<NotificationTimeSection>
    ) {
        super.onCurrentListChanged(previousList, currentList)
        notifyItemRangeChanged(0, currentList.size, UPDATE_CONTEXTUAL_UI_PAYLOAD)
    }

    private fun getContextualInfo(position: Int): ContextualPosition {
        return currentList.getContextualPosition<NotificationTimeSection.Content>(position)
    }

    private object DiffCallback : DiffUtil.ItemCallback<NotificationTimeSection>() {
        override fun areItemsTheSame(
            oldItem: NotificationTimeSection,
            newItem: NotificationTimeSection
        ): Boolean {
            return when {
                oldItem is NotificationTimeSection.Title && newItem is NotificationTimeSection.Title -> true

                oldItem is NotificationTimeSection.Content.Item && newItem is NotificationTimeSection.Content.Item ->
                    oldItem.dayTime == newItem.dayTime

                oldItem is NotificationTimeSection.Content.AddItem && newItem is NotificationTimeSection.Content.AddItem -> true

                else -> false
            }
        }

        override fun areContentsTheSame(
            oldItem: NotificationTimeSection,
            newItem: NotificationTimeSection
        ): Boolean {
            return when {
                oldItem is NotificationTimeSection.Title && newItem is NotificationTimeSection.Title -> true

                oldItem is NotificationTimeSection.Content.Item && newItem is NotificationTimeSection.Content.Item ->
                    oldItem == newItem

                oldItem is NotificationTimeSection.Content.AddItem && newItem is NotificationTimeSection.Content.AddItem -> true

                else -> false
            }
        }
    }

    private enum class ViewType(val value: Int) {
        Title(0),
        Item(1),
        AddItem(2);

        companion object {
            fun from(value: Int): ViewType? {
                return entries.find { it.value == value }
            }

            fun from(data: NotificationTimeSection): ViewType {
                return when (data) {
                    NotificationTimeSection.Title -> Title
                    NotificationTimeSection.Content.AddItem -> AddItem
                    is NotificationTimeSection.Content.Item -> Item
                }
            }
        }
    }

    companion object {
        private const val UPDATE_CONTEXTUAL_UI_PAYLOAD = "UPDATE_CONTEXTUAL_UI_PAYLOAD"
    }
}
