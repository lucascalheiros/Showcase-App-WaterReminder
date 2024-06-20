package com.github.lucascalheiros.waterreminder.data.userprofileprovider.data.models


data class UserProfileDb(
    val name: String,
    val weightInGrams: Double,
    val activityLevel: ActivityLevelDb,
    val temperatureLevel: AmbienceTemperatureLevelDb,
)