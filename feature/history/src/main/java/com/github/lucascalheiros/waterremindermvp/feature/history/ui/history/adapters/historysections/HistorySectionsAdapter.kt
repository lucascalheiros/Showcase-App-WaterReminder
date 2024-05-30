package com.github.lucascalheiros.waterremindermvp.feature.history.ui.history.adapters.historysections

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.lucascalheiros.waterremindermvp.common.ui.helpers.ContextualPosition
import com.github.lucascalheiros.waterremindermvp.common.ui.helpers.getContextualPosition
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.ConsumedWater
import com.github.lucascalheiros.waterremindermvp.feature.history.ui.history.adapters.historysections.viewholders.HistoryConsumedWaterItemViewHolder
import com.github.lucascalheiros.waterremindermvp.feature.history.ui.history.adapters.historysections.viewholders.HistoryDayHeaderViewHolder
import com.github.lucascalheiros.waterremindermvp.feature.history.ui.history.adapters.historysections.viewholders.HistoryTitleViewHolder

class HistorySectionsAdapter :
    ListAdapter<HistorySections, ViewHolder>(HistorySectionsDiffCallback) {

    var onDeleteConsumedWaterClick: ((ConsumedWater) -> Unit)? = null

    override fun getItemViewType(position: Int): Int {
        return HistorySectionsViewType.from(getItem(position)).value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (HistorySectionsViewType.from(viewType)) {
            HistorySectionsViewType.Title -> HistoryTitleViewHolder.inflate(parent)
            HistorySectionsViewType.DayHeader -> HistoryDayHeaderViewHolder.inflate(parent)
            HistorySectionsViewType.ConsumedWaterItem ->
                HistoryConsumedWaterItemViewHolder.inflate(parent) {
                    onDeleteConsumedWaterClick?.invoke(it)
                }

            null -> throw IllegalStateException("ViewType is not known type")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is HistorySections.ConsumedWaterItem -> {
                (holder as? HistoryConsumedWaterItemViewHolder)?.apply {
                    bind(item)
                    updateContextualUI(getContextualInfo(position))
                }
            }

            is HistorySections.DayHeader -> (holder as? HistoryDayHeaderViewHolder)?.bind(item)
            HistorySections.Title -> Unit
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.contains(UPDATE_CONTEXTUAL_UI_PAYLOAD)) {
            (holder as? HistoryConsumedWaterItemViewHolder)?.updateContextualUI(getContextualInfo(position))
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onCurrentListChanged(
        previousList: MutableList<HistorySections>,
        currentList: MutableList<HistorySections>
    ) {
        super.onCurrentListChanged(previousList, currentList)
        notifyItemRangeChanged(0, currentList.size, UPDATE_CONTEXTUAL_UI_PAYLOAD)
    }

    private fun getContextualInfo(position: Int): ContextualPosition {
        return currentList.getContextualPosition<HistorySections.ConsumedWaterItem>(position)
    }

    companion object {
        private const val UPDATE_CONTEXTUAL_UI_PAYLOAD = "UPDATE_CONTEXTUAL_UI_PAYLOAD"
    }
}
