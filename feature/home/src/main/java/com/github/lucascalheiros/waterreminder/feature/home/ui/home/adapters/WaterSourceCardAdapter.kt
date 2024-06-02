package com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.lucascalheiros.waterreminder.common.appcore.format.shortValueAndUnitFormatted
import com.github.lucascalheiros.waterreminder.common.ui.getThemeAwareColor
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSource
import com.github.lucascalheiros.waterreminder.feature.home.databinding.ListItemAddWaterSourceBinding
import com.github.lucascalheiros.waterreminder.feature.home.databinding.ListItemWaterSourceBinding
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.menus.WaterSourceCardMenuActions
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.menus.showWaterSourceCardMenu

class WaterSourceCardAdapter : ListAdapter<WaterSourceCard, ViewHolder>(DiffCallback) {

    var listener: WaterSourceCardsListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (ViewType.from(viewType)) {
            ViewType.ConsumptionItem -> ConsumptionViewHolder(
                ListItemWaterSourceBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            ViewType.AddItem -> AddViewHolder(
                ListItemAddWaterSourceBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            null -> throw IllegalStateException("ViewType is not known type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).let { ViewType.from(it).value }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is WaterSourceCard.AddItem -> (holder as? AddViewHolder)?.bind()
            is WaterSourceCard.ConsumptionItem -> (holder as? ConsumptionViewHolder)?.bind(item)
        }
    }

    inner class ConsumptionViewHolder(private val binding: ListItemWaterSourceBinding) :
        ViewHolder(binding.root) {
        fun bind(item: WaterSourceCard.ConsumptionItem) {
            val color = item.waterSource.waterSourceType.run {
                binding.root.context.getThemeAwareColor(
                    lightColor,
                    darkColor
                )
            }
            with(binding.tvWaterSourceName) {
                text = item.waterSource.waterSourceType.name
                setTextColor(color.toInt())
            }
            with(binding.tvVolume) {
                text = item.waterSource.volume.shortValueAndUnitFormatted(binding.root.context)
                setTextColor(color.toInt())
            }

            with(binding.cvCard) {
                setOnClickListener {
                    listener?.onWaterSourceClick(item.waterSource)
                }
                setOnLongClickListener {
                    it.showWaterSourceCardMenu {
                        when (it) {
                            WaterSourceCardMenuActions.Delete -> listener?.onDeleteWaterSourceCard(
                                item.waterSource
                            )
                        }
                    }
                    true
                }
            }
        }
    }

    inner class AddViewHolder(private val binding: ListItemAddWaterSourceBinding) :
        ViewHolder(binding.root) {
        fun bind() {
            binding.cvCard.setOnClickListener {
                listener?.onAddWaterSourceClick()
            }
        }
    }
}

private enum class ViewType(val value: Int) {
    ConsumptionItem(0),
    AddItem(1);

    companion object {
        fun from(value: Int): ViewType? {
            return entries.find { it.value == value }
        }

        fun from(waterSourceCard: WaterSourceCard): ViewType {
            return when (waterSourceCard) {
                WaterSourceCard.AddItem -> AddItem
                is WaterSourceCard.ConsumptionItem -> ConsumptionItem
            }
        }
    }
}

private object DiffCallback : DiffUtil.ItemCallback<WaterSourceCard>() {
    override fun areItemsTheSame(oldItem: WaterSourceCard, newItem: WaterSourceCard): Boolean {
        return when {
            oldItem is WaterSourceCard.ConsumptionItem &&
                    newItem is WaterSourceCard.ConsumptionItem -> oldItem.waterSource.waterSourceId == newItem.waterSource.waterSourceId

            oldItem is WaterSourceCard.AddItem &&
                    newItem is WaterSourceCard.AddItem -> true

            else -> false
        }
    }

    override fun areContentsTheSame(
        oldItem: WaterSourceCard,
        newItem: WaterSourceCard
    ): Boolean {
        return when {
            oldItem is WaterSourceCard.ConsumptionItem &&
                    newItem is WaterSourceCard.ConsumptionItem -> oldItem.waterSource.volume == newItem.waterSource.volume

            oldItem is WaterSourceCard.AddItem &&
                    newItem is WaterSourceCard.AddItem -> true

            else -> false
        }
    }
}

interface WaterSourceCardsListener {
    fun onWaterSourceClick(waterSource: WaterSource)
    fun onAddWaterSourceClick()
    fun onDeleteWaterSourceCard(waterSource: WaterSource)
}

sealed interface WaterSourceCard {
    data class ConsumptionItem(val waterSource: WaterSource) : WaterSourceCard
    data object AddItem : WaterSourceCard
}