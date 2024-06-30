package com.github.lucascalheiros.waterreminder.feature.home.ui.adddrink.dialogs

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.github.lucascalheiros.waterreminder.common.ui.databinding.DialogContentSliderBinding
import com.github.lucascalheiros.waterreminder.feature.home.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.slider.LabelFormatter.LABEL_VISIBLE

fun Context.createHydrationInputDialog(
    defaultValue: Float = 1f,
    onConfirm: (Float) -> Unit
): AlertDialog {
    val binding = DialogContentSliderBinding.inflate(LayoutInflater.from(this)).apply {
        slider.valueTo = 1f
        slider.valueFrom = 0.1f
        slider.stepSize = 0.1f
        slider.value = defaultValue
        slider.labelBehavior = LABEL_VISIBLE
    }
    return MaterialAlertDialogBuilder(this)
        .setTitle(R.string.dialog_hydration_factor_title)
        .setView(binding.root)
        .setPositiveButton(R.string.dialog_hydration_factor_confirm) { _, _ ->
            onConfirm(binding.slider.value)
        }
        .setNegativeButton(R.string.dialog_hydration_factor_cancel) { dialog, _ ->
            dialog.dismiss()
        }
        .create().apply {
            show()
        }
}