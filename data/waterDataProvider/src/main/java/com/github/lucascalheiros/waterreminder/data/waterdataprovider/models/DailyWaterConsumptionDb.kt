package com.github.lucascalheiros.waterreminder.data.waterdataprovider.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class DailyWaterConsumptionDb(
    @PrimaryKey(autoGenerate = true) val dailyWaterConsumptionId: Long = 0,
    val expectedVolumeInMl: Double,
    val date: Long,
)
