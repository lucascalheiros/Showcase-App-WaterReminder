package com.github.lucascalheiros.waterreminder.domain.userinformation.data.converters

import com.github.lucascalheiros.waterreminder.data.userprofileprovider.models.AmbienceTemperatureLevelDb
import com.github.lucascalheiros.waterreminder.data.userprofileprovider.models.UserProfileDb
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AmbienceTemperatureLevel
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.UserProfile
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeight
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeightUnit

internal fun UserProfile.toDb(): UserProfileDb {
    return UserProfileDb(
        name,
        weight.toUnit(MeasureSystemWeightUnit.GRAMS).intrinsicValue(),
        activityLevelInWeekDays,
        temperatureLevel.toAmbienceTemperatureLevelDb()
    )
}

internal fun UserProfileDb.toModel(): UserProfile {
    return UserProfile(
        name,
        MeasureSystemWeight.Companion.create(weightInGrams, MeasureSystemWeightUnit.GRAMS),
        activityLevelInWeekDays,
        temperatureLevel.toAmbienceTemperatureLevel()
    )
}

private fun AmbienceTemperatureLevelDb.toAmbienceTemperatureLevel(): AmbienceTemperatureLevel {
    return when(this) {
        AmbienceTemperatureLevelDb.Cold -> AmbienceTemperatureLevel.Cold
        AmbienceTemperatureLevelDb.Moderate -> AmbienceTemperatureLevel.Moderate
        AmbienceTemperatureLevelDb.Warn -> AmbienceTemperatureLevel.Warn
        AmbienceTemperatureLevelDb.Hot -> AmbienceTemperatureLevel.Hot
    }
}

private fun AmbienceTemperatureLevel.toAmbienceTemperatureLevelDb(): AmbienceTemperatureLevelDb {
    return when(this) {
        AmbienceTemperatureLevel.Cold -> AmbienceTemperatureLevelDb.Cold
        AmbienceTemperatureLevel.Moderate -> AmbienceTemperatureLevelDb.Moderate
        AmbienceTemperatureLevel.Warn -> AmbienceTemperatureLevelDb.Warn
        AmbienceTemperatureLevel.Hot -> AmbienceTemperatureLevelDb.Hot
    }
}