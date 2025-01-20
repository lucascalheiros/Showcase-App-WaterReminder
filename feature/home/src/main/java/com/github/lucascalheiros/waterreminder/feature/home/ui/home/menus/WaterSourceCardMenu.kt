package com.github.lucascalheiros.waterreminder.feature.home.ui.home.menus

import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import com.github.lucascalheiros.waterreminder.feature.home.R

fun View.showWaterSourceCardMenu(onSelected: (WaterSourceCardMenuActions) -> Unit) {
    val popup = PopupMenu(context, this, Gravity.CENTER).apply {
        setForceShowIcon(true)
    }
    popup.menuInflater.inflate(R.menu.menu_water_source_card, popup.menu)

    popup.setOnMenuItemClickListener { menuItem: MenuItem ->
        val action = when (menuItem.itemId) {
            R.id.deleteOpt -> WaterSourceCardMenuActions.Delete

            else -> return@setOnMenuItemClickListener false
        }
        onSelected(action)
        true
    }

    popup.show()
}

enum class WaterSourceCardMenuActions {
    Delete
}