package com.github.lucascalheiros.waterreminder.domain.watermanagement.data.coverters

import com.github.lucascalheiros.waterreminder.data.waterdataprovider.models.ConsumedWaterDb
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.models.DailyWaterConsumptionDb
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.models.WaterSourceDb
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.models.WaterSourceTypeDb
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ConsumedWater
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumption
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSource
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit

internal fun DailyWaterConsumptionDb.toDailyWaterConsumption(): DailyWaterConsumption {
    return DailyWaterConsumption(
        dailyWaterConsumptionId.takeIf { it >= 0 } ?: 0,
        MeasureSystemVolume.create(expectedVolumeInMl, MeasureSystemVolumeUnit.ML),
        date
    )
}

internal fun DailyWaterConsumption.toDailyWaterConsumptionDb(): DailyWaterConsumptionDb {
    return DailyWaterConsumptionDb(
        dailyWaterConsumptionId.takeIf { it >= 0 } ?: 0,
        expectedVolume.toUnit(MeasureSystemVolumeUnit.ML).intrinsicValue(),
        date
    )
}

internal fun WaterSourceDb.toWaterSource(): WaterSource {
    return WaterSource(
        waterSourceId.takeIf { it >= 0 } ?: 0,
        MeasureSystemVolume.Companion.create(volumeInMl, MeasureSystemVolumeUnit.ML),
        waterSourceTypeDb.toWaterSourceType()
    )
}

internal fun WaterSource.toWaterSourceDb(): WaterSourceDb {
    return WaterSourceDb(
        waterSourceId.takeIf { it >= 0 } ?: 0,
        volume.toUnit(MeasureSystemVolumeUnit.ML).intrinsicValue(),
        waterSourceType.toWaterSourceTypeDb()
    )
}

internal fun ConsumedWaterDb.toConsumedWater(): ConsumedWater {
    return ConsumedWater(
        consumedWaterId.takeIf { it >= 0 } ?: 0,
        MeasureSystemVolume.create(volumeInMl, MeasureSystemVolumeUnit.ML),
        consumptionTime,
        waterSourceTypeDb.toWaterSourceType()
    )
}

internal fun ConsumedWater.toConsumedWaterDb(): ConsumedWaterDb {
    return ConsumedWaterDb(
        consumedWaterId.takeIf { it >= 0 } ?: 0,
        volume.toUnit(MeasureSystemVolumeUnit.ML).intrinsicValue(),
        consumptionTime,
        waterSourceType.toWaterSourceTypeDb()
    )
}

internal fun WaterSourceTypeDb.toWaterSourceType(): WaterSourceType {
    return WaterSourceType(
        waterSourceTypeId.takeIf { it >= 0 } ?: 0,
        name,
        lightColor,
        darkColor,
        hydrationFactor,
        custom
    )
}

internal fun WaterSourceType.toWaterSourceTypeDb(): WaterSourceTypeDb {
    return WaterSourceTypeDb(
        waterSourceTypeId.takeIf { it >= 0 } ?: 0,
        name,
        lightColor,
        darkColor,
        hydrationFactor,
        custom
    )
}