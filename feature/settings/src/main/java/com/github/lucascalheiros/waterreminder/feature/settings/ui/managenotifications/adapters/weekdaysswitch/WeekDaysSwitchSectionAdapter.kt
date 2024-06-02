package com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.weekdaysswitch

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.lucascalheiros.waterreminder.common.ui.helpers.ContextualPosition
import com.github.lucascalheiros.waterreminder.common.ui.helpers.getContextualPosition
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDay
import com.github.lucascalheiros.waterreminder.feature.settings.R
import com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.viewholders.SectionTitleViewHolder
import com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.weekdaysswitch.viewholders.WeekDaysSwitchItemViewHolder

class WeekDaysSwitchSectionAdapter :
    ListAdapter<WeekDaySwitchSection, ViewHolder>(DiffCallback) {

    var onWeekDayNotificationStateChange: (WeekDay, Boolean) -> Unit = { _, _ -> }

    override fun getItemViewType(position: Int): Int {
        return ViewType.from(getItem(position)).value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (ViewType.from(viewType)) {
            ViewType.Title -> SectionTitleViewHolder.inflate(parent)
            ViewType.Item -> WeekDaysSwitchItemViewHolder.inflate(parent)

            null -> throw IllegalStateException("ViewType is not known type")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is WeekDaySwitchSection.Item -> {
                (holder as? WeekDaysSwitchItemViewHolder)?.apply {
                    bind(item) { weekDay, state ->
                        onWeekDayNotificationStateChange(weekDay, state)
                    }
                    updateContextualUI(getContextualInfo(position))
                }
            }

            WeekDaySwitchSection.Title -> (holder as? SectionTitleViewHolder)?.bind(R.string.week_days)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.contains(UPDATE_CONTEXTUAL_UI_PAYLOAD)) {
            (holder as? WeekDaysSwitchItemViewHolder)?.updateContextualUI(getContextualInfo(position))
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onCurrentListChanged(
        previousList: MutableList<WeekDaySwitchSection>,
        currentList: MutableList<WeekDaySwitchSection>
    ) {
        super.onCurrentListChanged(previousList, currentList)
        notifyItemRangeChanged(0, currentList.size, UPDATE_CONTEXTUAL_UI_PAYLOAD)
    }

    private fun getContextualInfo(position: Int): ContextualPosition {
        return currentList.getContextualPosition<WeekDaySwitchSection.Item>(position)
    }

    private object DiffCallback : DiffUtil.ItemCallback<WeekDaySwitchSection>() {
        override fun areItemsTheSame(oldItem: WeekDaySwitchSection, newItem: WeekDaySwitchSection): Boolean {
            return when {
                oldItem is WeekDaySwitchSection.Title && newItem is WeekDaySwitchSection.Title -> true

                oldItem is WeekDaySwitchSection.Item && newItem is WeekDaySwitchSection.Item ->
                    oldItem.weekDay == newItem.weekDay

                else -> false
            }
        }

        override fun areContentsTheSame(
            oldItem: WeekDaySwitchSection,
            newItem: WeekDaySwitchSection
        ): Boolean {
            return when {
                oldItem is WeekDaySwitchSection.Title && newItem is WeekDaySwitchSection.Title -> true

                oldItem is WeekDaySwitchSection.Item && newItem is WeekDaySwitchSection.Item ->
                    oldItem.weekDay == newItem.weekDay

                else -> false
            }
        }
    }

    private enum class ViewType(val value: Int) {
        Title(0),
        Item(1);

        companion object {
            fun from(value: Int): ViewType? {
                return entries.find { it.value == value }
            }

            fun from(data: WeekDaySwitchSection): ViewType {
                return when (data) {
                    is WeekDaySwitchSection.Item -> Item
                    WeekDaySwitchSection.Title -> Title
                }
            }
        }
    }

    companion object {
        private const val UPDATE_CONTEXTUAL_UI_PAYLOAD = "UPDATE_CONTEXTUAL_UI_PAYLOAD"
    }
}
