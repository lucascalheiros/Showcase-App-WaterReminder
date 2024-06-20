package com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.models.ConsumedWaterDb
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.models.DailyWaterConsumptionDb
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.models.WaterSourceDb
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.models.WaterSourceTypeDb
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.ConsumedWaterDao
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.DailyWaterConsumptionDao
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.WaterSourceDao
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.WaterSourceTypeDao


@Database(
    entities = [
        ConsumedWaterDb::class,
        DailyWaterConsumptionDb::class,
        WaterSourceDb::class,
        WaterSourceTypeDb::class,
    ], version = 1
)
internal abstract class WaterDataDB : RoomDatabase() {
    abstract fun consumedWaterDao(): ConsumedWaterDao
    abstract fun dailyWaterConsumptionDao(): DailyWaterConsumptionDao
    abstract fun waterSourceDao(): WaterSourceDao
    abstract fun waterSourceTypoDao(): WaterSourceTypeDao

    companion object {
        val Context.db: WaterDataDB
            get() = Room.databaseBuilder(
                applicationContext,
                WaterDataDB::class.java, "WaterDataDB.db"
            ).build()
    }
}