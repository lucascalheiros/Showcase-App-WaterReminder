package com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.coverters

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ConsumedWater
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumption
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSource
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminderkmp.data.waterdataprovider.ConsumedWaterDb
import com.github.lucascalheiros.waterreminderkmp.data.waterdataprovider.DailyWaterConsumptionDb
import com.github.lucascalheiros.waterreminderkmp.data.waterdataprovider.WaterSourceDb
import com.github.lucascalheiros.waterreminderkmp.data.waterdataprovider.WaterSourceTypeDb

internal fun DailyWaterConsumptionDb.toDailyWaterConsumption(): DailyWaterConsumption {
    return DailyWaterConsumption(
        dailyWaterConsumptionId.takeIf { it >= 0 } ?: -1,
        MeasureSystemVolume.create(expectedVolumeInMl, MeasureSystemVolumeUnit.ML),
        date
    )
}

internal fun DailyWaterConsumption.toDailyWaterConsumptionDb(): DailyWaterConsumptionDb {
    return DailyWaterConsumptionDb(
        dailyWaterConsumptionId.takeIf { it >= 0 } ?: -1,
        expectedVolume.toUnit(MeasureSystemVolumeUnit.ML).intrinsicValue(),
        date
    )
}

internal fun WaterSourceDb.toWaterSource(): WaterSource {
    return WaterSource(
        waterSourceId.takeIf { it >= 0 } ?: -1,
        MeasureSystemVolume.Companion.create(volumeInMl, MeasureSystemVolumeUnit.ML),
        WaterSourceTypeDb(
            waterSourceTypeId ?: 0,
            name,
            lightColor,
            darkColor,
            hydrationFactor,
            custom
        ).toWaterSourceType()
    )
}

internal fun WaterSource.toWaterSourceDb(): WaterSourceDb {
    val type = waterSourceType.toWaterSourceTypeDb()
    return WaterSourceDb(
        waterSourceId.takeIf { it >= 0 } ?: -1,
        volume.toUnit(MeasureSystemVolumeUnit.ML).intrinsicValue(),
        type.waterSourceTypeId,
        type.name,
        type.lightColor,
        type.darkColor,
        type.hydrationFactor,
        type.custom
    )
}

internal fun ConsumedWaterDb.toConsumedWater(): ConsumedWater {
    return ConsumedWater(
        consumedWaterId.takeIf { it >= 0 } ?: -1,
        MeasureSystemVolume.create(volumeInMl, MeasureSystemVolumeUnit.ML),
        consumptionTime,
        WaterSourceTypeDb(
            waterSourceTypeId ?: 0,
            name,
            lightColor,
            darkColor,
            hydrationFactor,
            custom
        ).toWaterSourceType()
    )
}

internal fun ConsumedWater.toConsumedWaterDb(): ConsumedWaterDb {
    val type = waterSourceType.toWaterSourceTypeDb()
    return ConsumedWaterDb(
        consumedWaterId.takeIf { it >= 0 } ?: -1,
        volume.toUnit(MeasureSystemVolumeUnit.ML).intrinsicValue(),
        consumptionTime,
        type.waterSourceTypeId,
        type.name,
        type.lightColor,
        type.darkColor,
        type.hydrationFactor,
        type.custom
    )
}

internal fun WaterSourceTypeDb.toWaterSourceType(): WaterSourceType {
    return WaterSourceType(
        waterSourceTypeId.takeIf { it >= 0 } ?: -1,
        name,
        lightColor,
        darkColor,
        hydrationFactor / 100f,
        custom == 1L
    )
}

internal fun WaterSourceType.toWaterSourceTypeDb(): WaterSourceTypeDb {
    return WaterSourceTypeDb(
        waterSourceTypeId.takeIf { it >= 0 } ?: -1,
        name,
        lightColor,
        darkColor,
        (hydrationFactor * 100).toLong(),
        if (custom) 1 else 0
    )
}