package com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.impl

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.WaterDatabase
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.ConsumedWaterDao
import com.github.lucascalheiros.waterreminderkmp.data.waterdataprovider.ConsumedWaterDb
import com.github.lucascalheiros.waterreminderkmp.data.waterdataprovider.ConsumedWaterQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ConsumedWaterDaoImpl(
    private val database: WaterDatabase
) : ConsumedWaterDao {

    private val queries: ConsumedWaterQueries
        get() = database.consumedWaterQueries

    override suspend fun all(): List<ConsumedWaterDb> = withContext(Dispatchers.IO) {
        queries.selectAll().executeAsList()
    }

    override suspend fun allByPeriod(
        startTimestamp: Long,
        endTimestamp: Long
    ): List<ConsumedWaterDb> {
        return all().filter { it.consumptionTime in startTimestamp..endTimestamp }
    }

    override fun allFlow(): Flow<List<ConsumedWaterDb>> {
        return queries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    override fun allByPeriodFlow(
        startTimestamp: Long,
        endTimestamp: Long
    ): Flow<List<ConsumedWaterDb>> {
        return allFlow().map { data -> data.filter { it.consumptionTime in startTimestamp..endTimestamp } }
    }

    override suspend fun getById(id: Long): ConsumedWaterDb? = withContext(Dispatchers.IO) {
        queries.selectById(id).executeAsOneOrNull()
    }

    override suspend fun save(data: ConsumedWaterDb) = withContext(Dispatchers.IO) {
        with(data) {
            if (consumedWaterId == -1L) {
                queries.insert(
                    volumeInMl,
                    consumptionTime,
                    waterSourceTypeId,
                    name,
                    lightColor,
                    darkColor,
                    hydrationFactor,
                    custom
                )
            } else {
                queries.update(this)
            }
        }
    }

    override suspend fun deleteById(id: Long) = withContext(Dispatchers.IO) {
        queries.deleteById(id)
    }

    override suspend fun deleteAll() = withContext(Dispatchers.IO) {
        queries.deleteAll()
    }

}