package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.menus

import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import com.github.lucascalheiros.waterreminder.feature.settings.R
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit

fun View.showMeasureSystemMenu(onSelected: (MeasureSystemVolumeUnit) -> Unit) {
    val popup = PopupMenu(context, this, Gravity.RIGHT)
    popup.menuInflater.inflate(R.menu.menu_measure_system_options, popup.menu)

    popup.setOnMenuItemClickListener { menuItem: MenuItem ->
        val measureSystemUnit = when (menuItem.itemId) {
            R.id.unitSIOpt -> MeasureSystemVolumeUnit.ML

            R.id.unitUKOpt -> MeasureSystemVolumeUnit.OZ_UK

            R.id.unitUSOpt -> MeasureSystemVolumeUnit.OZ_US

            else -> return@setOnMenuItemClickListener false
        }
        onSelected(measureSystemUnit)
        true
    }

    popup.show()
}
