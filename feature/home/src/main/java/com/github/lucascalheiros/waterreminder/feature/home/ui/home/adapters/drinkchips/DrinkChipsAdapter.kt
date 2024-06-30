package com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.drinkchips

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.lucascalheiros.waterreminder.common.ui.getThemeAwareColor
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.feature.home.R
import com.github.lucascalheiros.waterreminder.feature.home.databinding.ListItemDrinkBinding
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.menus.DrinkChipsMenuActions
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.menus.showDrinkChipsMenu

class DrinkChipsAdapter : ListAdapter<DrinkItems, ViewHolder>(DiffCallback) {

    var listener: DrinkChipsListener? = null

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is DrinkItems.AddItem -> (holder as? AddViewHolder)?.bind()
            is DrinkItems.OptionItem -> (holder as? OptionViewHolder)?.bind(item)
        }
    }

    inner class OptionViewHolder(private val binding: ListItemDrinkBinding) :
        ViewHolder(binding.root) {
        fun bind(item: DrinkItems.OptionItem) {
            val color = item.waterSource.run {
                binding.root.context.getThemeAwareColor(
                    lightColor,
                    darkColor
                )
            }
            with(binding.root) {
                text = item.waterSource.name
                setTextColor(color.toInt())
                setOnClickListener {
                    listener?.onDrinkClick(item.waterSource)
                }
                setOnLongClickListener { view ->
                    view.showDrinkChipsMenu {
                        when (it) {
                            DrinkChipsMenuActions.Delete ->
                                listener?.onDeleteDrink(item.waterSource)
                        }
                    }
                    true
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
                    newItem is DrinkItems.OptionItem -> oldItem.waterSource.waterSourceTypeId == newItem.waterSource.waterSourceTypeId

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
                    newItem is DrinkItems.OptionItem -> oldItem.waterSource == newItem.waterSource

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
}

sealed interface DrinkItems {
    data class OptionItem(val waterSource: WaterSourceType) : DrinkItems
    data object AddItem : DrinkItems
}