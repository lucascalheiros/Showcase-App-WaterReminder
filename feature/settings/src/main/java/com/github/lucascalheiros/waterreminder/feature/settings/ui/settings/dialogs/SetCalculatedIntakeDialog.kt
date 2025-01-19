package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.dialogs

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.github.lucascalheiros.waterreminder.feature.settings.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.createSetCalculatedIntakeDialog(
    onConfirm: () -> Unit
): AlertDialog {
    return MaterialAlertDialogBuilder(this)
        .setTitle(R.string.set_intake)
        .setMessage(R.string.do_you_want_to_set_the_calculated_intake_as_your_daily_intake)
        .setPositiveButton(com.github.lucascalheiros.waterreminder.common.appcore.R.string.confirm) { _, _ ->
            onConfirm()
        }
        .setNegativeButton(com.github.lucascalheiros.waterreminder.common.appcore.R.string.cancel) { dialog, _ ->
            dialog.dismiss()
        }.create()
}