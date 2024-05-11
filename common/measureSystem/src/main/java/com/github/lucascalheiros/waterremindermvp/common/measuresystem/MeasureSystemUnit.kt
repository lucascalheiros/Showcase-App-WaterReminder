package com.github.lucascalheiros.waterremindermvp.common.measuresystem

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
}

enum class MeasureSystemVolumeUnit {
    ML,
    OZ_UK,
    OZ_US
}

enum class MeasureSystemWeightUnit {
    GRAMS,
    POUNDS
}