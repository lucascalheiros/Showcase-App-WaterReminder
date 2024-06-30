package com.github.lucascalheiros.waterreminder.feature.home.ui.adddrink.dialogs

import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.postDelayed
import com.github.lucascalheiros.waterreminder.common.ui.databinding.DialogContentInputBinding
import com.github.lucascalheiros.waterreminder.feature.home.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.createNameInputDialog(
    onConfirm: (String) -> Unit
): AlertDialog {
    val binding = DialogContentInputBinding.inflate(LayoutInflater.from(this))

    binding.tieInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES

    return MaterialAlertDialogBuilder(this)
        .setTitle(R.string.dialog_name_input_title)
        .setView(binding.root)
        .setPositiveButton(R.string.dialog_name_input_confirm) { _, _ ->
            val input = binding.tieInput.text?.toString().orEmpty()
            if (input.isNotBlank()) {
                onConfirm(input)
            }
        }
        .setNegativeButton(R.string.dialog_name_input_cancel) { dialog, _ ->
            dialog.dismiss()
        }
        .create().apply {
            setOnShowListener {
                binding.tieInput.postDelayed(200) {
                    binding.tieInput.requestFocus()
                    val imm =
                        ContextCompat.getSystemService(
                            this@createNameInputDialog,
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