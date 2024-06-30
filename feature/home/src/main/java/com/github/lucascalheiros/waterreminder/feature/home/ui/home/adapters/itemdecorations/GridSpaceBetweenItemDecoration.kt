package com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.itemdecorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpaceBetweenItemDecoration(
    private val spaceBetweenHorizontal: Float = 0f,
    private val spaceBetweenVertical: Float = 0f
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val halfSpaceHorizontal = spaceBetweenHorizontal.toInt()
        val halfSpaceVertical = spaceBetweenVertical.toInt()
        outRect.left = 0
        outRect.right = halfSpaceHorizontal
        outRect.top = halfSpaceVertical
        outRect.bottom = 0
    }
}