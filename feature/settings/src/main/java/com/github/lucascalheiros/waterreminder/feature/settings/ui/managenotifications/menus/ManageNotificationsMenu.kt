package com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.menus

import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import com.github.lucascalheiros.waterreminder.feature.settings.R

fun View.showManageNotificationsMenu(onSelected: (ManageNotificationsMenuOptions) -> Unit) {
    val popup = PopupMenu(context, this, Gravity.RIGHT)
    popup.menuInflater.inflate(R.menu.menu_manage_notifications_more, popup.menu)

    popup.setOnMenuItemClickListener { menuItem: MenuItem ->
        val measureSystemUnit = when (menuItem.itemId) {
            R.id.selectAll -> ManageNotificationsMenuOptions.SelectAll

            else -> return@setOnMenuItemClickListener false
        }
        onSelected(measureSystemUnit)
        true
    }

    popup.show()
}

enum class ManageNotificationsMenuOptions {
    SelectAll
}
