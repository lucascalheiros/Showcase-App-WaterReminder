package com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.snaphelper

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

class StartSnapHelper : PagerSnapHelper() {
    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray {
        val out = IntArray(2)
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager))
        } else {
            out[0] = 0
        }

        if (layoutManager.canScrollVertically()) {
            out[1] = distanceToStart(targetView, getVerticalHelper(layoutManager))
        } else {
            out[1] = 0
        }
        return out
    }

    private fun distanceToStart(targetView: View, helper: OrientationHelper): Int {
        return helper.getDecoratedStart(targetView) - helper.startAfterPadding
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        return if (layoutManager is LinearLayoutManager) {
            getStartView(layoutManager, getHorizontalHelper(layoutManager))
        } else {
            super.findSnapView(layoutManager)
        }
    }

    private fun getStartView(
        layoutManager: LinearLayoutManager,
        helper: OrientationHelper
    ): View? {
        val firstChild = layoutManager.findFirstVisibleItemPosition()
        if (firstChild == RecyclerView.NO_POSITION) {
            return null
        }

        val child = layoutManager.findViewByPosition(firstChild)

        return if (helper.getDecoratedEnd(child) >= helper.getDecoratedMeasurement(child) / 2
            && helper.getDecoratedEnd(child) > 0
        ) {
            child
        } else {
            if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.itemCount - 1) {
                null
            } else {
                layoutManager.findViewByPosition(firstChild + 2)
            }
        }
    }

    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        return OrientationHelper.createHorizontalHelper(layoutManager)
    }

    private fun getVerticalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        return OrientationHelper.createVerticalHelper(layoutManager)
    }
}