package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.postDelayed
import com.github.lucascalheiros.waterreminder.common.appcore.format.shortUnitFormatted
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.common.ui.databinding.DialogContentInputBinding
import com.github.lucascalheiros.waterreminder.feature.settings.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.createDailyWaterIntakeInputDialog(
    unit: com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit,
    onConfirm: (Double) -> Unit
): AlertDialog {
    val binding = DialogContentInputBinding.inflate(LayoutInflater.from(this))

    binding.tilInput.suffixText = unit.shortUnitFormatted(this)

    return MaterialAlertDialogBuilder(this)
        .setTitle(R.string.settings_dialog_daily_water_intake_input)
        .setView(binding.root)
        .setPositiveButton(com.github.lucascalheiros.waterreminder.common.appcore.R.string.confirm) { _, _ ->
            val volume = binding.tieInput.text?.toString().orEmpty().toDoubleOrNull()
            if (volume != null && volume > 0.0) {
                onConfirm(volume)
            }
        }
        .setNegativeButton(com.github.lucascalheiros.waterreminder.common.appcore.R.string.cancel) { dialog, _ ->
            dialog.dismiss()
        }
        .create().apply {
            setOnShowListener {
                binding.tieInput.postDelayed(200) {
                    binding.tieInput.requestFocus()
                    val imm =
                        ContextCompat.getSystemService(
                            this@createDailyWaterIntakeInputDialog,
                            InputMethodManager::class.java
                        )
                    imm?.showSoftInput(
                        binding.tieInput,
                        InputMethodManager.SHOW_IMPLICIT
                    )
                }
            }
        }

}