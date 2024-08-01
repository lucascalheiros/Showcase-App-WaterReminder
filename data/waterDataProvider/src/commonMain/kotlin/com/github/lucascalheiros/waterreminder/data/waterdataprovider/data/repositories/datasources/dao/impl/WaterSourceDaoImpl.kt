package com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.impl

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.WaterDatabase
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.WaterSourceDao
import com.github.lucascalheiros.waterreminderkmp.data.waterdataprovider.WaterSourceDb
import com.github.lucascalheiros.waterreminderkmp.data.waterdataprovider.WaterSourceQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class WaterSourceDaoImpl(
    private val database: WaterDatabase
) : WaterSourceDao {

    private val queries: WaterSourceQueries
        get() = database.waterSourceQueries

    override suspend fun all(): List<WaterSourceDb> = withContext(Dispatchers.IO) {
        queries.selectAll().executeAsList()
    }

    override fun allFlow(): Flow<List<WaterSourceDb>> {
        return queries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    override suspend fun getById(id: Long): WaterSourceDb? = withContext(Dispatchers.IO) {
        queries.selectById(id).executeAsOneOrNull()
    }

    override suspend fun save(data: WaterSourceDb) = withContext(Dispatchers.IO) {
        with(data) {
            if (waterSourceId == -1L) {
                queries.insert(
                    volumeInMl,
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