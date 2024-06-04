package com.github.lucascalheiros.waterreminder.data.waterdataprovider.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ConsumedWaterDb(
    @PrimaryKey(autoGenerate = true) val consumedWaterId: Long = 0,
    val volumeInMl: Double,
    val consumptionTime: Long,
    @Embedded
    val waterSourceTypeDb: WaterSourceTypeDb
)
