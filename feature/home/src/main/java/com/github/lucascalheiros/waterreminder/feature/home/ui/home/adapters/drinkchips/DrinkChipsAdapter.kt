package com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.drinkchips

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.lucascalheiros.waterreminder.common.ui.getThemeAwareColor
import com.github.lucascalheiros.waterreminder.common.util.logDebug
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.feature.home.R
import com.github.lucascalheiros.waterreminder.feature.home.databinding.ListItemDrinkBinding
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.itemtouchhelper.SortingItemTouchHelperCallback
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.menus.DrinkChipsMenuActions
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.menus.showDrinkChipsMenu

class DrinkChipsAdapter : RecyclerView.Adapter<ViewHolder>(),
    SortingItemTouchHelperCallback.ItemTouchHelperContract {

    var listener: DrinkChipsListener? = null
    private var data = listOf<DrinkItems>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemDrinkBinding.inflate(
            inflater,
            parent,
            false
        )

        return when (ViewType.from(viewType)) {
            ViewType.OptionItem -> OptionViewHolder(
                binding
            )

            ViewType.AddItem -> AddViewHolder(
                binding
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

    private fun getItem(position: Int): DrinkItems {
        return data[position]
    }

    fun submitList(newDataset: List<DrinkItems>, commitCallback: (() -> Unit)? = null) {
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
            is DrinkItems.AddItem -> (holder as? AddViewHolder)?.bind()
            is DrinkItems.OptionItem -> (holder as? OptionViewHolder)?.bind(item)
        }
    }

    inner class OptionViewHolder(private val binding: ListItemDrinkBinding) :
        ViewHolder(binding.root) {

        var movedTo: Int? = null
        var chipItem: DrinkItems.OptionItem? = null

        fun bind(item: DrinkItems.OptionItem) {
            chipItem = item
            movedTo = null
            val color = item.type.run {
                binding.root.context.getThemeAwareColor(
                    lightColor,
                    darkColor
                )
            }
            with(binding.root) {
                text = item.type.name
                setTextColor(color.toInt())
                setOnClickListener {
                    listener?.onDrinkClick(item.type)
                }
            }
        }
    }

    inner class AddViewHolder(private val binding: ListItemDrinkBinding) :
        ViewHolder(binding.root) {
        fun bind() {
            with(binding.root) {
                setText(R.string.drink_add)
                setOnClickListener {
                    listener?.onAddDrinkClick()
                }
            }
        }
    }

    override fun onRowMoved(from: ViewHolder, to: ViewHolder): Boolean {
        return if (getItem(from.bindingAdapterPosition).isDraggable() && getItem(to.bindingAdapterPosition).isDraggable()) {
            (from as? OptionViewHolder)?.movedTo = to.bindingAdapterPosition
            notifyItemMoved(from.bindingAdapterPosition, to.bindingAdapterPosition)
            true
        } else {
            false
        }
    }

    override fun onRowSelected(viewHolder: ViewHolder?) {
    }

    override fun onRowClear(viewHolder: ViewHolder) {
        val optionViewHolder = (viewHolder as? OptionViewHolder) ?: return
        val chipItem = optionViewHolder.chipItem ?: return
        val type = chipItem.type
        val movedTo = optionViewHolder.movedTo
        optionViewHolder.movedTo = null
        if (movedTo == null) {
            viewHolder.itemView.showDrinkChipsMenu {
                when (it) {
                    DrinkChipsMenuActions.Delete ->
                        listener?.onDeleteDrink(type)
                }
            }
        } else {
            val currentDataModified = data.toMutableList()
            currentDataModified.remove(chipItem)
            currentDataModified.add(movedTo, chipItem)
            data = currentDataModified
            logDebug("::onRowClear movedTo: $movedTo $type")
            listener?.onMoveToPosition(type, movedTo)
        }
    }

    override fun isDraggable(viewHolder: ViewHolder): Boolean {
        return getItem(viewHolder.bindingAdapterPosition).isDraggable()
    }
}

private enum class ViewType(val value: Int) {
    OptionItem(0),
    AddItem(1);

    companion object {
        fun from(value: Int): ViewType? {
            return entries.find { it.value == value }
        }

        fun from(drinkItems: DrinkItems): ViewType {
            return when (drinkItems) {
                DrinkItems.AddItem -> AddItem
                is DrinkItems.OptionItem -> OptionItem
            }
        }
    }
}

private object DiffCallback : DiffUtil.ItemCallback<DrinkItems>() {
    override fun areItemsTheSame(oldItem: DrinkItems, newItem: DrinkItems): Boolean {
        return when {
            oldItem is DrinkItems.OptionItem &&
                    newItem is DrinkItems.OptionItem -> oldItem.type.waterSourceTypeId == newItem.type.waterSourceTypeId

            oldItem is DrinkItems.AddItem &&
                    newItem is DrinkItems.AddItem -> true

            else -> false
        }
    }

    override fun areContentsTheSame(
        oldItem: DrinkItems,
        newItem: DrinkItems
    ): Boolean {
        return when {
            oldItem is DrinkItems.OptionItem &&
                    newItem is DrinkItems.OptionItem -> oldItem.type == newItem.type

            oldItem is DrinkItems.AddItem &&
                    newItem is DrinkItems.AddItem -> true

            else -> false
        }
    }
}

interface DrinkChipsListener {
    fun onDrinkClick(type: WaterSourceType)
    fun onAddDrinkClick()
    fun onDeleteDrink(type: WaterSourceType)
    fun onMoveToPosition(type: WaterSourceType, position: Int)
}

sealed interface DrinkItems {
    data class OptionItem(val type: WaterSourceType) : DrinkItems
    data object AddItem : DrinkItems

    fun isDraggable(): Boolean {
        return when (this) {
            AddItem -> false
            is OptionItem -> true
        }
    }
}