package com.github.lucascalheiros.waterreminder.common.ui.pickers.horizontalrule

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.github.lucascalheiros.waterreminder.common.ui.databinding.ViewHorizontalRuleBinding
import com.github.lucascalheiros.waterreminder.common.ui.helpers.smoothScrollToStartOfPosition


class HorizontalRuleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ViewHorizontalRuleBinding.inflate(LayoutInflater.from(context), this)
    private val ruleMarkAdapter = RuleMarkAdapter()
    private val snapHelper = SmoothLinearSnap()

    var items: Int = 500
        set(value) {
            field = value
            updateItemsRange()
        }

    var listener: OnHorizontalRuleChangeListener? = null
    var value: Int = 0
        private set

    init {
        updateItemsRange()
        with(binding.rvRule) {
            adapter = ruleMarkAdapter
            addItemDecoration(RuleMarkSpaceItemDecoration(40f))
            setClipToPadding(false)
            snapHelper.attachToRecyclerView(this)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == SCROLL_STATE_IDLE) {
                        listener?.onValueStopChanging(value)
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    snapHelper.findSnapView(layoutManager)?.let {
                        val position = getChildAdapterPosition(it)
                        if (value != position) {
                            listener?.onValueChanging(position)
                            value = position
                        }
                    }
                }
            })
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val halfPadding = w / 2
        with(binding.rvRule) {
            setPadding(halfPadding, paddingTop, halfPadding, paddingBottom)
        }
    }

    fun setSelectedValue(value: Int, animate: Boolean = true) {
        if (binding.rvRule.scrollState != SCROLL_STATE_IDLE || value == this.value) {
            return
        }
        with(binding.rvRule) {
            if (animate) {
                smoothScrollToStartOfPosition(value)
            } else {
                scrollToPosition(value)
                smoothScrollBy(1, 0)
            }
        }
    }

    private fun updateItemsRange() {
        ruleMarkAdapter.submitList((0..items).toList())
    }

    interface OnHorizontalRuleChangeListener {
        fun onValueChanging(value: Int) {}
        fun onValueStopChanging(value: Int) {}
    }
}