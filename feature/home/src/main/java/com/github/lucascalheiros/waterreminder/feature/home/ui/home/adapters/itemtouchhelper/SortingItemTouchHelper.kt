package com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.itemtouchhelper

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SortingItemTouchHelperCallback(
    private val listContract: ItemTouchHelperContract
): ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        if (listContract.isDraggable(viewHolder)) {
            val dragFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.UP or ItemTouchHelper.DOWN
            return makeMovementFlags(dragFlags, 0)
        } else {
            return 0
        }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return listContract.onRowMoved(viewHolder, target)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            listContract.onRowSelected(viewHolder)
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }

    override fun clearView(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ) {
        super.clearView(recyclerView, viewHolder)
        listContract.onRowClear(viewHolder)
    }

    interface ItemTouchHelperContract {
        fun onRowMoved(from: RecyclerView.ViewHolder, to: RecyclerView.ViewHolder): Boolean
        fun onRowSelected(viewHolder: RecyclerView.ViewHolder?)
        fun onRowClear(viewHolder: RecyclerView.ViewHolder)
        fun isDraggable(viewHolder: RecyclerView.ViewHolder): Boolean
    }
}