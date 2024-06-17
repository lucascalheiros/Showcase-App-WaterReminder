package com.github.lucascalheiros.waterreminder.measuresystem.domain.models

enum class MeasureSystemUnit {
    SI,
    UK,
    US;

    fun toVolumeUnit(): MeasureSystemVolumeUnit {
        return when (this) {
            SI -> MeasureSystemVolumeUnit.ML
            UK -> MeasureSystemVolumeUnit.OZ_UK
            US -> MeasureSystemVolumeUnit.OZ_US
        }
    }

    fun toWeightUnit(): MeasureSystemWeightUnit {
        return when (this) {
            SI -> MeasureSystemWeightUnit.GRAMS
            UK, US -> MeasureSystemWeightUnit.POUNDS
        }
    }

    fun toTemperatureUnit(): MeasureSystemTemperatureUnit {
        return when (this) {
            SI -> MeasureSystemTemperatureUnit.Celsius
            UK, US -> MeasureSystemTemperatureUnit.Fahrenheit
        }
    }
}

