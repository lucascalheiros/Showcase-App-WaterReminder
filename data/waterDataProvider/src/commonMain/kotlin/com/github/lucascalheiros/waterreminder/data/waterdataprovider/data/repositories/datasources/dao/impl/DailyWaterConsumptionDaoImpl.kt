package com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.impl

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.WaterDatabase
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.DailyWaterConsumptionDao
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminderkmp.data.waterdataprovider.DailyWaterConsumptionDb
import com.github.lucascalheiros.waterreminderkmp.data.waterdataprovider.DailyWaterConsumptionQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

class DailyWaterConsumptionDaoImpl(
    private val database: WaterDatabase
) : DailyWaterConsumptionDao {

    private val queries: DailyWaterConsumptionQueries
        get() = database.dailyWaterConsumptionQueries

    override suspend fun all(): List<DailyWaterConsumptionDb> = withContext(Dispatchers.IO) {
        queries.selectAll().executeAsList()
    }

    override suspend fun allByPeriod(
        startTimestamp: Long,
        endTimestamp: Long
    ): List<DailyWaterConsumptionDb> {
        return all().filter { it.date in startTimestamp..endTimestamp }
    }

    override fun allFlow(): Flow<List<DailyWaterConsumptionDb>> {
        return queries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    override fun allByPeriodFlow(
        startTimestamp: Long,
        endTimestamp: Long
    ): Flow<List<DailyWaterConsumptionDb>> {
        return allFlow().map { data -> data.filter { it.date in startTimestamp..endTimestamp } }
    }

    override suspend fun getById(id: Long): DailyWaterConsumptionDb? = withContext(Dispatchers.IO) {
        queries.selectById(id).executeAsOneOrNull()
    }

    override suspend fun save(volume: MeasureSystemVolume) = withContext(Dispatchers.IO) {
        queries.insert(volume.toUnit(MeasureSystemVolumeUnit.ML).intrinsicValue(), Clock.System.now().toEpochMilliseconds())
    }

    override suspend fun deleteById(id: Long) = withContext(Dispatchers.IO) {
        queries.deleteById(id)
    }

    override suspend fun deleteAll() = withContext(Dispatchers.IO) {
        queries.deleteAll()
    }

}