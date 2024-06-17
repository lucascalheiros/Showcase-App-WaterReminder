package com.github.lucascalheiros.waterreminder.data.userprofileprovider.models


data class UserProfileDb(
    val name: String,
    val weightInGrams: Double,
    val activityLevel: ActivityLevelDb,
    val temperatureLevel: AmbienceTemperatureLevelDb,
)