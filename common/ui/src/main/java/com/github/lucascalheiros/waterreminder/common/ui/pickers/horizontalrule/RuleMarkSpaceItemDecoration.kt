package com.github.lucascalheiros.waterreminder.common.ui.pickers.horizontalrule

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RuleMarkSpaceItemDecoration(
    private val spaceBetweenHorizontal: Float = 0f,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: return

        val halfSpaceHorizontal = (spaceBetweenHorizontal / 2).toInt()
        when (position) {
            0 -> {
                outRect.left = 0
                outRect.right = halfSpaceHorizontal
            }

            itemCount - 1 -> {
                outRect.left = halfSpaceHorizontal
                outRect.right = 0
            }

            else -> {
                outRect.left = halfSpaceHorizontal
                outRect.right = halfSpaceHorizontal
            }
        }
    }
}