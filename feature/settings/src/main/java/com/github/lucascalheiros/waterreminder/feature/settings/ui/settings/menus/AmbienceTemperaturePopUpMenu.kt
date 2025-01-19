package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.menus

import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AmbienceTemperatureLevel
import com.github.lucascalheiros.waterreminder.feature.settings.R

fun View.showAmbienceTemperatureMenu(onSelected: (AmbienceTemperatureLevel) -> Unit) {
    val popup = PopupMenu(context, this, Gravity.RIGHT)

    popup.setForceShowIcon(true)

    popup.menuInflater.inflate(R.menu.menu_temperature_options, popup.menu)

    popup.setOnMenuItemClickListener { menuItem: MenuItem ->
        val theme = when (menuItem.itemId) {
            R.id.coldOpt -> AmbienceTemperatureLevel.Cold

            R.id.moderateOpt -> AmbienceTemperatureLevel.Moderate

            R.id.warmOpt -> AmbienceTemperatureLevel.Warm

            R.id.hotOpt -> AmbienceTemperatureLevel.Hot

            else -> return@setOnMenuItemClickListener false
        }
        onSelected(theme)
        true
    }

    popup.show()
}