package com.github.lucascalheiros.waterreminder.data.waterdataprovider.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class WaterSourceDb(
    @PrimaryKey(autoGenerate = true) val waterSourceId: Long = 0,
    val volumeInMl: Double,
    @Embedded
    val waterSourceTypeDb: WaterSourceTypeDb
)
