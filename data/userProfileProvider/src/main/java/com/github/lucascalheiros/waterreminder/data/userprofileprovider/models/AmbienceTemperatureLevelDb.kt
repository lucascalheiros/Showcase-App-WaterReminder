package com.github.lucascalheiros.waterreminder.data.userprofileprovider.models

enum class AmbienceTemperatureLevelDb(val value: Int) {
    Cold(0),
    Moderate(1),
    Warn(2),
    Hot(3),
    ;

    companion object {
        fun Int.toAmbienceTemperatureLevelDb(): AmbienceTemperatureLevelDb {
            return AmbienceTemperatureLevelDb.entries.find { it.value == this } ?: Moderate
        }
    }
}