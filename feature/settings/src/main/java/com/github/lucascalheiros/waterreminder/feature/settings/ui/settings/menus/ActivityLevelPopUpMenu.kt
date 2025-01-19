package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.menus

import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.ActivityLevel
import com.github.lucascalheiros.waterreminder.feature.settings.R

fun View.showActivityLevelMenu(onSelected: (ActivityLevel) -> Unit) {
    val popup = PopupMenu(context, this, Gravity.RIGHT)

    popup.setForceShowIcon(true)

    popup.menuInflater.inflate(R.menu.menu_activity_options, popup.menu)

    popup.setOnMenuItemClickListener { menuItem: MenuItem ->
        val theme = when (menuItem.itemId) {
            R.id.sedentaryOpt -> ActivityLevel.Sedentary

            R.id.lightOpt -> ActivityLevel.Light

            R.id.moderateOpt -> ActivityLevel.Moderate

            R.id.heavyOpt -> ActivityLevel.Heavy

            else -> return@setOnMenuItemClickListener false
        }
        onSelected(theme)
        true
    }

    popup.show()
}