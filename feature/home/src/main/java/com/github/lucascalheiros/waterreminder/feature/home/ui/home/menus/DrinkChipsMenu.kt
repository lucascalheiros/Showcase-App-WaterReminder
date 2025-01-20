package com.github.lucascalheiros.waterreminder.feature.home.ui.home.menus

import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import com.github.lucascalheiros.waterreminder.feature.home.R

fun View.showDrinkChipsMenu(onSelected: (DrinkChipsMenuActions) -> Unit) {
    val popup = PopupMenu(context, this, Gravity.CENTER).apply {
        setForceShowIcon(true)
    }
    popup.menuInflater.inflate(R.menu.menu_drink_chip, popup.menu)

    popup.setOnMenuItemClickListener { menuItem: MenuItem ->
        val action = when (menuItem.itemId) {
            R.id.deleteOpt -> DrinkChipsMenuActions.Delete

            else -> return@setOnMenuItemClickListener false
        }
        onSelected(action)
        true
    }

    popup.show()
}

enum class DrinkChipsMenuActions {
    Delete
}