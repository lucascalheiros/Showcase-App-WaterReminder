package com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.watersource

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.lucascalheiros.waterreminder.common.appcore.format.shortValueAndUnitFormatted
import com.github.lucascalheiros.waterreminder.common.ui.getThemeAwareColor
import com.github.lucascalheiros.waterreminder.common.util.logDebug
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSource
import com.github.lucascalheiros.waterreminder.feature.home.R
import com.github.lucascalheiros.waterreminder.feature.home.databinding.ListItemWaterSourceBinding
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.itemtouchhelper.SortingItemTouchHelperCallback.ItemTouchHelperContract
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.menus.WaterSourceCardMenuActions
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.menus.showWaterSourceCardMenu

class WaterSourceCardAdapter : RecyclerView.Adapter<ViewHolder>(),
    ItemTouchHelperContract {

    var listener: WaterSourceCardsListener? = null
    private var data = listOf<WaterSourceCard>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (ViewType.from(viewType)) {
            ViewType.ConsumptionItem -> ConsumptionViewHolder(
                ListItemWaterSourceBinding.inflate(
                    inflater,
                    parent,
                    false
                ).apply {
                    tvWaterSourceName.isVisible = true
                    tvVolume.isVisible = true
                    root.updateLayoutParams {
                        width = root.context.itemWidth().toInt()
                    }
                }
            )

            ViewType.AddItem -> AddViewHolder(
                ListItemWaterSourceBinding.inflate(
                    inflater,
                    parent,
                    false
                ).apply {
                    tvAdd.isVisible = true
                    root.updateLayoutParams {
                        width = root.context.itemWidth().toInt()
                    }
                }
            )

            null -> throw IllegalStateException("ViewType is not known type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).let { ViewType.from(it).value }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun getItem(position: Int): WaterSourceCard {
        return data[position]
    }

    fun submitList(newDataset: List<WaterSourceCard>, commitCallback: (() -> Unit)? = null) {
        val oldDataset = data
        if (oldDataset.size == newDataset.size && oldDataset.zip(newDataset).all { (a, b) -> a == b }) {
            return
        }
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = oldDataset.size

            override fun getNewListSize() = newDataset.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return DiffCallback.areItemsTheSame(oldDataset[oldItemPosition], newDataset[newItemPosition])
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return DiffCallback.areContentsTheSame(oldDataset[oldItemPosition], newDataset[newItemPosition])
            }

            override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                return null
            }
        })

        data = newDataset
        diffResult.dispatchUpdatesTo(this)
        commitCallback?.invoke()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is WaterSourceCard.AddItem -> (holder as? AddViewHolder)?.bind()
            is WaterSourceCard.ConsumptionItem -> (holder as? ConsumptionViewHolder)?.bind(item)
        }
    }

    inner class ConsumptionViewHolder(private val binding: ListItemWaterSourceBinding) :
        ViewHolder(binding.root) {

        var movedTo: Int? = null
        var cardItem: WaterSourceCard.ConsumptionItem? = null

        fun bind(item: WaterSourceCard.ConsumptionItem) {
            cardItem = item
            movedTo = null
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
            }
        }
    }

    inner class AddViewHolder(private val binding: ListItemWaterSourceBinding) :
        ViewHolder(binding.root) {
        fun bind() {
            binding.cvCard.setOnClickListener {
                listener?.onAddWaterSourceClick()
            }
        }
    }

    private fun Context.itemWidth(): Float {
        return (Resources.getSystem().displayMetrics.widthPixels - resources.getDimension(com.github.lucascalheiros.waterreminder.common.ui.R.dimen.screen_horizontal_margin) * 2 -
                resources.getDimension(R.dimen.water_source_card_horizontal_space)) / 2
    }

    override fun onRowMoved(from: ViewHolder, to: ViewHolder): Boolean {
        return if (getItem(from.bindingAdapterPosition).isDraggable() && getItem(to.bindingAdapterPosition).isDraggable()) {
            (from as? ConsumptionViewHolder)?.movedTo = to.bindingAdapterPosition
            notifyItemMoved(from.bindingAdapterPosition, to.bindingAdapterPosition)
            true
        } else {
            false
        }
    }

    override fun onRowSelected(viewHolder: ViewHolder?) {
    }

    override fun onRowClear(viewHolder: ViewHolder) {
        val consumptionViewHolder = (viewHolder as? ConsumptionViewHolder) ?: return
        val cardItem = consumptionViewHolder.cardItem ?: return
        val waterSource = cardItem.waterSource
        val movedTo = consumptionViewHolder.movedTo
        consumptionViewHolder.movedTo = null
        if (movedTo == null) {
            viewHolder.itemView.showWaterSourceCardMenu {
                when (it) {
                    WaterSourceCardMenuActions.Delete -> listener?.onDeleteWaterSourceCard(
                        waterSource
                    )
                }
            }
        } else {
            val currentDataModified = data.toMutableList()
            currentDataModified.remove(cardItem)
            currentDataModified.add(movedTo, cardItem)
            data = currentDataModified
            logDebug("::onRowClear movedTo: $movedTo $waterSource")
            listener?.onMoveToPosition(waterSource, movedTo)
        }
    }

    override fun isDraggable(viewHolder: ViewHolder): Boolean {
        return getItem(viewHolder.bindingAdapterPosition).isDraggable()
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
    fun onMoveToPosition(waterSource: WaterSource, position: Int)
}

sealed interface WaterSourceCard {
    data class ConsumptionItem(val waterSource: WaterSource) : WaterSourceCard
    data object AddItem : WaterSourceCard

    fun isDraggable(): Boolean {
        return when (this) {
            AddItem -> false
            is ConsumptionItem -> true
        }
    }
}