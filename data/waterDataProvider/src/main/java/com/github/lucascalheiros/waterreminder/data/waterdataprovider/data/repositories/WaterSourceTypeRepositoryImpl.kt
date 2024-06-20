package com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories

import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.WaterSourceTypeDao
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.coverters.toWaterSourceType
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.coverters.toWaterSourceTypeDb
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.WaterSourceTypeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class WaterSourceTypeRepositoryImpl(
    private val waterSourceTypeDao: WaterSourceTypeDao,
    ioDispatcher: CoroutineDispatcher
) : WaterSourceTypeRepository {

    init {
        CoroutineScope(ioDispatcher).launch {
            initDefaultData()
        }
    }

    override fun allFlow(): Flow<List<WaterSourceType>> {
        return waterSourceTypeDao.allFlow().map { it.map { it.toWaterSourceType() } }
    }

    override suspend fun all(): List<WaterSourceType> {
        return waterSourceTypeDao.all().map { it.toWaterSourceType() }.let {
            if (it.isEmpty()) {
                initDefaultData()
                waterSourceTypeDao.all().map { it.toWaterSourceType() }
            } else {
                it
            }
        }
    }

    override suspend fun getById(id: Long): WaterSourceType? {
        return waterSourceTypeDao.getById(id)?.toWaterSourceType()
    }

    override suspend fun deleteById(id: Long) {
        waterSourceTypeDao.deleteById(id)
    }

    override suspend fun deleteAll() {
        waterSourceTypeDao.deleteAll()
    }

    override suspend fun save(data: WaterSourceType) {
        waterSourceTypeDao.save(data.toWaterSourceTypeDb())
    }

    private suspend fun initDefaultData() {
        waterSourceTypeDao.save(
            WaterSourceType(
                1,
                "Water",
                lightColor = 0xFF0000FF,
                darkColor = 0xFFADD8E6,
                1f,
                false
            ).toWaterSourceTypeDb()
        )
        waterSourceTypeDao.save(
            WaterSourceType(
                2,
                "Coffee",
                lightColor = 0xFF5C4033,
                darkColor = 0xFFC4A484,
                1f,
                false
            ).toWaterSourceTypeDb()
        )
    }

}