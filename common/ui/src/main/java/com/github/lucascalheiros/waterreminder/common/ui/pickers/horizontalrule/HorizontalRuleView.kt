package com.github.lucascalheiros.waterreminder.common.ui.pickers.horizontalrule

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.github.lucascalheiros.waterreminder.common.ui.databinding.ViewHorizontalRuleBinding


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
                        listener?.onValueChanging(position)
                        value = position
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
        if (animate) {
            binding.rvRule.smoothScrollToStartOfPosition(value)
        } else {
            binding.rvRule.scrollToPosition(value)
        }
    }

    private fun RecyclerView.smoothScrollToStartOfPosition(position: Int) {
        val linearSmoothScroller = object : LinearSmoothScroller(context) {
            override fun getHorizontalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }
        linearSmoothScroller.targetPosition = position
        (layoutManager as LinearLayoutManager).startSmoothScroll(linearSmoothScroller)
    }

    private fun updateItemsRange() {
        ruleMarkAdapter.submitList((0..items).toList())
    }

    interface OnHorizontalRuleChangeListener {
        fun onValueChanging(value: Int) {}
        fun onValueStopChanging(value: Int) {}
    }
}