package com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WaterSourceTypeDb(
    @PrimaryKey(autoGenerate = true) val waterSourceTypeId: Long = 0,
    val name: String,
    val lightColor: Long,
    val darkColor: Long,
    val hydrationFactor: Float,
    val custom: Boolean
)