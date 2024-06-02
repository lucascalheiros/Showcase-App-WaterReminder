package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.menus

import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import com.github.lucascalheiros.waterreminder.common.measuresystem.MeasureSystemUnit
import com.github.lucascalheiros.waterreminder.feature.settings.R

fun View.showMeasureSystemMenu(onSelected: (MeasureSystemUnit) -> Unit) {
    val popup = PopupMenu(context, this, Gravity.RIGHT)
    popup.menuInflater.inflate(R.menu.menu_measure_system_options, popup.menu)

    popup.setOnMenuItemClickListener { menuItem: MenuItem ->
        val measureSystemUnit = when (menuItem.itemId) {
            R.id.unitSIOpt -> MeasureSystemUnit.SI

            R.id.unitUKOpt -> MeasureSystemUnit.UK

            R.id.unitUSOpt -> MeasureSystemUnit.US

            else -> return@setOnMenuItemClickListener false
        }
        onSelected(measureSystemUnit)
        true
    }

    popup.show()
}
