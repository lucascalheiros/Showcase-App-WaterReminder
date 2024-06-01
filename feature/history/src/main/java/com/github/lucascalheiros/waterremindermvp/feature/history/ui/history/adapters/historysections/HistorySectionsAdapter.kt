package com.github.lucascalheiros.waterremindermvp.feature.history.ui.history.adapters.historysections

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.lucascalheiros.waterremindermvp.common.ui.helpers.ContextualPosition
import com.github.lucascalheiros.waterremindermvp.common.ui.helpers.getContextualPosition
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.ConsumedWater
import com.github.lucascalheiros.waterremindermvp.feature.history.ui.history.adapters.historysections.viewholders.HistoryConsumedWaterItemViewHolder
import com.github.lucascalheiros.waterremindermvp.feature.history.ui.history.adapters.historysections.viewholders.HistoryConsumptionChartViewHolder
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
            HistorySectionsViewType.ConsumptionChart -> HistoryConsumptionChartViewHolder.inflate(parent)

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
            is HistorySections.ConsumptionChart -> (holder as? HistoryConsumptionChartViewHolder)?.bind(item, false)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        }
        if (payloads.contains(UPDATE_CONTEXTUAL_UI_PAYLOAD)) {
            (holder as? HistoryConsumedWaterItemViewHolder)?.updateContextualUI(getContextualInfo(position))
        }
        val item = getItem(position)
        if (payloads.contains(UPDATE_CHART_PAYLOAD) && item is HistorySections.ConsumptionChart) {
            (holder as? HistoryConsumptionChartViewHolder)?.bind(item, true)
        }
        if (payloads.contains(UPDATE_DAY_HEADER_PAYLOAD) && item is HistorySections.DayHeader) {
            (holder as? HistoryDayHeaderViewHolder)?.bind(item)
        }
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
        const val UPDATE_CHART_PAYLOAD = "UPDATE_CHART_PAYLOAD"
        const val UPDATE_DAY_HEADER_PAYLOAD = "UPDATE_DAY_HEADER_PAYLOAD"
    }
}
