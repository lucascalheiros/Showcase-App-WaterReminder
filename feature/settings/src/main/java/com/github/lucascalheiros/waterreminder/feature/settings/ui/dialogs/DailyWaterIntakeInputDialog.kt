package com.github.lucascalheiros.waterreminder.feature.settings.ui.dialogs

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDay
import com.github.lucascalheiros.waterreminder.feature.settings.databinding.DialogWeekdaysListContentBinding
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.notificationWeekDaysPicker(
    title: String,
    selectedWeekDays: List<WeekDay>,
    onConfirm: (List<WeekDay>) -> Unit
): AlertDialog {
    val binding = DialogWeekdaysListContentBinding.inflate(LayoutInflater.from(this))

    WeekDay.entries.forEach {
        with(binding.cardViewFromWeekDay(it)) {
            isChecked = it in selectedWeekDays
            setOnClickListener {
                isChecked = !isChecked
            }
        }
    }

    return MaterialAlertDialogBuilder(this)
        .setTitle(title)
        .setView(binding.root)
        .setPositiveButton(com.github.lucascalheiros.waterreminder.common.appcore.R.string.confirm) { dialog, _ ->
            onConfirm(WeekDay.entries.filter { binding.cardViewFromWeekDay(it).isChecked })
            dialog.dismiss()
        }
        .setNegativeButton(com.github.lucascalheiros.waterreminder.common.appcore.R.string.cancel) { dialog, _ ->
            dialog.dismiss()
        }
        .create()
}

private fun DialogWeekdaysListContentBinding.  cardViewFromWeekDay(weekDay: WeekDay): MaterialCardView {
    return when (weekDay) {
        WeekDay.Sunday -> sundayCard
        WeekDay.Monday -> mondayCard
        WeekDay.Tuesday -> tuesdayCard
        WeekDay.Wednesday -> wednesdayCard
        WeekDay.Thursday -> thursdayCard
        WeekDay.Friday -> fridayCard
        WeekDay.Saturday -> saturdayCard
    }
}