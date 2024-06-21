package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.sections.profilesection

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.ActivityLevel
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AmbienceTemperatureLevel
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.UserProfile
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeight

interface ProfileSectionContract {
    interface View {
        fun setUserProfile(userProfile: UserProfile)
        fun setCalculatedIntake(measureSystemVolume: MeasureSystemVolume)
    }

    interface Presenter {
        fun onUserNameSet(name: String)
        fun onUserWeightSet(weight: MeasureSystemWeight)
        fun onUserActivityLevelSet(activityLevel: ActivityLevel)
        fun onUserTemperatureLevelSet(temperatureLevel: AmbienceTemperatureLevel)
    }
}
