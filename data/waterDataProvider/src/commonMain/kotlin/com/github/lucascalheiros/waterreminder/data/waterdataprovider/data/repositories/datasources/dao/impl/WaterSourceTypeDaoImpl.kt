package com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.impl

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.WaterDatabase
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.WaterSourceTypeDao
import com.github.lucascalheiros.waterreminderkmp.data.waterdataprovider.WaterSourceTypeDb
import com.github.lucascalheiros.waterreminderkmp.data.waterdataprovider.WaterSourceTypeQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class WaterSourceTypeDaoImpl(
    private val database: WaterDatabase
): WaterSourceTypeDao {

    private val queries: WaterSourceTypeQueries
        get() = database.waterSourceTypeQueries

    override suspend fun all(): List<WaterSourceTypeDb> = withContext(Dispatchers.IO) {
        queries.selectAll().executeAsList()
    }

    override fun allFlow(): Flow<List<WaterSourceTypeDb>> {
        return queries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    override suspend fun getById(id: Long): WaterSourceTypeDb? = withContext(Dispatchers.IO) {
        queries.selectById(id).executeAsOneOrNull()
    }

    override suspend fun save(data: WaterSourceTypeDb) = withContext(Dispatchers.IO) {
        with(data) {
            if (waterSourceTypeId == -1L) {
                queries.insert(name, lightColor, darkColor, hydrationFactor)
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