package com.github.lucascalheiros.waterreminder.common.ui.pickers.horizontalrule

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.LayoutParams
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class RuleMarkAdapter : ListAdapter<Int, RuleMarkAdapter.MarkViewHolder>(DiffCallback) {

    val ruleMarkDescriptorCreator: (Int) -> RuleMarkDescriptor = {
        when {
            it % 10 == 0 -> RuleMarkDescriptor(100, 5, Color.BLACK)
            it % 5 == 0 -> RuleMarkDescriptor(70, 5, Color.GRAY)
            else -> RuleMarkDescriptor(50, 5, Color.GRAY)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkViewHolder {
        return MarkViewHolder(View(parent.context))
    }

    override fun onBindViewHolder(holder: MarkViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MarkViewHolder(val markView: View) : ViewHolder(markView) {
        fun bind(value: Int) {
            val ruleMarkDescriptor = ruleMarkDescriptorCreator(value)
            markView.layoutParams = LayoutParams(
                ruleMarkDescriptor.width,
                ruleMarkDescriptor.height,
            )
            markView.setBackgroundColor(ruleMarkDescriptor.color)
        }
    }

    private object DiffCallback : ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }
}

data class RuleMarkDescriptor(
    val height: Int,
    val width: Int,
    @ColorInt val color: Int
)
