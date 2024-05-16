package com.github.lucascalheiros.waterremindermvp.feature.home.ui.addwatersource.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.postDelayed
import com.github.lucascalheiros.waterremindermvp.feature.home.R
import com.github.lucascalheiros.waterremindermvp.feature.home.databinding.DialogContentVolumeInputBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.createVolumeInputDialog(
    onConfirm: (Double) -> Unit
): AlertDialog {
    val binding =
        DialogContentVolumeInputBinding.inflate(LayoutInflater.from(this))

    return MaterialAlertDialogBuilder(this)
        .setTitle(R.string.volume_input)
        .setView(binding.root)
        .setPositiveButton(R.string.confirm) { _, _ ->
            val volume = binding.tieVolumeInput.text?.toString().orEmpty().toDoubleOrNull()
            if (volume != null && volume > 0.0) {
                onConfirm(volume)
            }
        }
        .setNegativeButton(R.string.cancel) { dialog, _ ->
            dialog.dismiss()
        }
        .create().apply {
            setOnShowListener {
                binding.tieVolumeInput.postDelayed(200) {
                    binding.tieVolumeInput.requestFocus()
                    val imm =
                        ContextCompat.getSystemService(
                            this@createVolumeInputDialog,
                            InputMethodManager::class.java
                        )
                    imm?.showSoftInput(
                        binding.tieVolumeInput,
                        InputMethodManager.SHOW_IMPLICIT
                    )
                }
            }
        }

}