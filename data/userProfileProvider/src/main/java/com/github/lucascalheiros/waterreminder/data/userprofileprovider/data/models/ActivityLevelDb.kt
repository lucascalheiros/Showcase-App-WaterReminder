package com.github.lucascalheiros.waterreminder.data.userprofileprovider.data.models

enum class ActivityLevelDb(val value: Int) {
    Sedentary(0),
    Light(1),
    Moderate(2),
    Heavy(3),
    ;

    companion object {
        fun Int.toActivityLevelDb(): ActivityLevelDb {
            return ActivityLevelDb.entries.find { it.value == this } ?: Light
        }
    }
}