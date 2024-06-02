package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.menus

import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AppTheme
import com.github.lucascalheiros.waterreminder.feature.settings.R

fun View.showThemeMenu(onSelected: (AppTheme) -> Unit) {
    val popup = PopupMenu(context, this, Gravity.RIGHT)
    popup.menuInflater.inflate(R.menu.menu_theme_options, popup.menu)

    popup.setOnMenuItemClickListener { menuItem: MenuItem ->
        val theme = when (menuItem.itemId) {
            R.id.autoThemeOpt -> AppTheme.Auto

            R.id.darkThemeOpt -> AppTheme.Dark

            R.id.lightThemeOpt -> AppTheme.Light

            else -> return@setOnMenuItemClickListener false
        }
        onSelected(theme)
        true
    }

    popup.show()
}