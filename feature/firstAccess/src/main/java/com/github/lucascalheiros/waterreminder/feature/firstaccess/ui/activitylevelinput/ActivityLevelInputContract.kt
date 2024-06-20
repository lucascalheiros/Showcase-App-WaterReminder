package com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.activitylevelinput

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.ActivityLevel

interface ActivityLevelInputContract {
    interface View {
        fun updateSelected(activityLevel: ActivityLevel)
    }

    interface Presenter {
        fun selectCard(activityLevel: ActivityLevel)
    }
}