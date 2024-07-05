package com.github.lucascalheiros.waterreminder.common.ui.helpers

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.smoothScrollToStartOfPosition(position: Int) {
    val linearSmoothScroller = object : LinearSmoothScroller(context) {
        override fun getHorizontalSnapPreference(): Int {
            return SNAP_TO_START
        }
    }
    linearSmoothScroller.targetPosition = position
    (layoutManager as LinearLayoutManager).startSmoothScroll(linearSmoothScroller)
}
