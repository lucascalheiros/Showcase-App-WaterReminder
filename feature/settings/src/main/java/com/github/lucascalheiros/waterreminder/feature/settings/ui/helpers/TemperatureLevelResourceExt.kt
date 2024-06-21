package com.github.lucascalheiros.waterreminder.feature.settings.ui.helpers

import android.content.Context
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.ActivityLevel
import com.github.lucascalheiros.waterreminder.feature.settings.R

fun ActivityLevel.displayText(context: Context): String {
    return when (this) {
        ActivityLevel.Sedentary -> R.string.activity_level_sedentary_title
        ActivityLevel.Light -> R.string.activity_level_light_title
        ActivityLevel.Moderate -> R.string.activity_level_moderate_title
        ActivityLevel.Heavy -> R.string.activity_level_heavy_title
    }.let {
        context.resources.getString(it)
    }
}
