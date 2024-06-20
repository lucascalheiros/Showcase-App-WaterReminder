package com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.activitylevelinput.helpers

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.ActivityLevel
import com.github.lucascalheiros.waterreminder.feature.firstaccess.R

fun ActivityLevel.titleRes(): Int {
    return when(this) {
        ActivityLevel.Sedentary -> R.string.activity_level_sedentary_title
        ActivityLevel.Light -> R.string.activity_level_light_title
        ActivityLevel.Moderate -> R.string.activity_level_moderate_title
        ActivityLevel.Heavy -> R.string.activity_level_heavy_title
    }

}
fun ActivityLevel.descriptionRes(): Int {
    return when(this) {
        ActivityLevel.Sedentary -> R.string.activity_level_sedentary_description
        ActivityLevel.Light -> R.string.activity_level_light_description
        ActivityLevel.Moderate -> R.string.activity_level_moderate_description
        ActivityLevel.Heavy -> R.string.activity_level_heavy_description
    }
}
