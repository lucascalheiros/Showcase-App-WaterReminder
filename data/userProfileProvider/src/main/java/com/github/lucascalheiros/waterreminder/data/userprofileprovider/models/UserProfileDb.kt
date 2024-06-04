package com.github.lucascalheiros.waterreminder.data.userprofileprovider.models


data class UserProfileDb(
    val name: String,
    val weightInGrams: Double,
    val activityLevelInWeekDays: Int,
    val temperatureLevel: AmbienceTemperatureLevelDb,
)