package com.github.lucascalheiros.waterreminder.feature.home.ui.adddrink.dialogs

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.github.lucascalheiros.waterreminder.common.ui.pickers.color.HsvColorPicker
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ThemeAwareColor
import com.github.lucascalheiros.waterreminder.feature.home.R
import com.github.lucascalheiros.waterreminder.feature.home.databinding.DialogColorPickerBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.createColorSelectorDialog(
    defaultValue: ThemeAwareColor,
    onConfirm: (ThemeAwareColor) -> Unit
): AlertDialog {
    var lightColor = defaultValue.onLightColor
    var darkColor = defaultValue.onDarkColor

    val binding = DialogColorPickerBinding.inflate(LayoutInflater.from(this)).apply {
        cvOnLightColor.setCardBackgroundColor(lightColor)
        cvOnDarkColor.setCardBackgroundColor(darkColor)
        tvLightInfo.setTextColor(lightColor)
        tvDarkInfo.setTextColor(darkColor)
        llLight.setOnClickListener {
            createColorPickerDialog(
                getColor(com.github.lucascalheiros.waterreminder.common.ui.R.color.light_surface),
                lightColor
            ) {
                lightColor = it
                cvOnLightColor.setCardBackgroundColor(lightColor)
                tvLightInfo.setTextColor(lightColor)
            }
        }
        llDark.setOnClickListener {
            createColorPickerDialog(
                getColor(com.github.lucascalheiros.waterreminder.common.ui.R.color.dark_surface),
                darkColor
            ) {
                darkColor = it
                cvOnDarkColor.setCardBackgroundColor(darkColor)
                tvDarkInfo.setTextColor(darkColor)
            }
        }
    }

    return MaterialAlertDialogBuilder(this)
        .setTitle(R.string.dialog_drink_colors_title)
        .setView(binding.root)
        .setPositiveButton(R.string.dialog_hydration_factor_confirm) { _, _ ->
            onConfirm(ThemeAwareColor(lightColor, darkColor))
        }
        .setNegativeButton(R.string.dialog_hydration_factor_cancel) { dialog, _ ->
            dialog.dismiss()
        }
        .create().apply {
            show()
        }
}


fun Context.createColorPickerDialog(
    colorBackgroundSample: Int,
    color: Int,
    onConfirm: (Int) -> Unit
): AlertDialog {
    val view = HsvColorPicker(this).apply {
        setColor(color)
        setColorBackgroundSample(colorBackgroundSample)
    }

    return MaterialAlertDialogBuilder(this)
        .setTitle(R.string.dialog_color_selector_title)
        .setView(view)
        .setPositiveButton(R.string.dialog_hydration_factor_confirm) { _, _ ->
            onConfirm(view.getColor())
        }
        .setNegativeButton(R.string.dialog_hydration_factor_cancel) { dialog, _ ->
            dialog.dismiss()
        }
        .create().apply {
            show()
        }
}