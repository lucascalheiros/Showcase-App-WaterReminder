package com.github.lucascalheiros.waterreminder.common.ui.pickers.horizontalrule

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.LayoutParams
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.lucascalheiros.waterreminder.common.ui.dimension.dp

class RuleMarkAdapter : ListAdapter<Int, RuleMarkAdapter.MarkViewHolder>(DiffCallback) {

    override fun getItemViewType(position: Int): Int {
        return when {
            position % 10 == 0 -> ViewType.Large.viewType
            position % 5 == 0 -> ViewType.Medium.viewType
            else -> ViewType.Small.viewType
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkViewHolder {
        return MarkViewHolder(View(parent.context).apply {
            val ruleMarkDescriptor = ViewType.fromViewType(viewType)!!.toRuleMark()
            layoutParams = LayoutParams(
                ruleMarkDescriptor.width,
                ruleMarkDescriptor.height,
            )
            setBackgroundColor(ruleMarkDescriptor.color)
        })
    }

    override fun onBindViewHolder(holder: MarkViewHolder, position: Int) {

    }

    inner class MarkViewHolder(markView: View) : ViewHolder(markView)

    private fun ViewType.toRuleMark(): RuleMarkDescriptor {
        return when (this) {
            ViewType.Small -> RuleMarkDescriptor(16.dp, 2.dp, Color.GRAY)
            ViewType.Medium -> RuleMarkDescriptor(30.dp, 2.dp, Color.GRAY)
            ViewType.Large -> RuleMarkDescriptor(40.dp, 2.dp, Color.BLACK)
        }
    }

    private enum class ViewType(val viewType: Int) {
        Small(0),
        Medium(1),
        Large(2);

        companion object {
            fun fromViewType(viewType: Int): ViewType? {
                return entries.find { it.viewType == viewType }
            }
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
