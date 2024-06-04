package com.github.lucascalheiros.waterreminder.domain.watermanagement.data.repositories

import com.github.lucascalheiros.waterreminder.data.waterdataprovider.datasources.local.dao.DailyWaterConsumptionDao
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.models.DailyWaterConsumptionDb
import com.github.lucascalheiros.waterreminder.domain.watermanagement.data.coverters.toDailyWaterConsumption
import com.github.lucascalheiros.waterreminder.domain.watermanagement.data.coverters.toDailyWaterConsumptionDb
import com.github.lucascalheiros.waterreminder.domain.watermanagement.data.coverters.toWaterSourceTypeDb
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumption
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.DailyWaterConsumptionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class DailyWaterConsumptionRepositoryImpl(
    private val dailyWaterConsumptionDao: DailyWaterConsumptionDao,
    ioDispatcher: CoroutineDispatcher
): DailyWaterConsumptionRepository {

    init {
        CoroutineScope(ioDispatcher).launch {
            dailyWaterConsumptionDao.save(
                DailyWaterConsumptionDb(
                    1,
                    2000.0,
                    0
                )
            )
        }
    }

    override fun allFlow(): Flow<List<DailyWaterConsumption>> {
        return dailyWaterConsumptionDao.allFlow().map { it.map { it.toDailyWaterConsumption() } }
    }

    override fun allByPeriodFlow(
        startTimestamp: Long,
        endTimestamp: Long
    ): Flow<List<DailyWaterConsumption>> {
        return dailyWaterConsumptionDao.allByPeriodFlow(startTimestamp, endTimestamp).map { it.map { it.toDailyWaterConsumption() } }
    }

    override suspend fun all(): List<DailyWaterConsumption> {
        return dailyWaterConsumptionDao.all().map { it.toDailyWaterConsumption() }
    }

    override suspend fun allByPeriod(
        startTimestamp: Long,
        endTimestamp: Long
    ): List<DailyWaterConsumption> {
        return dailyWaterConsumptionDao.allByPeriod(startTimestamp, endTimestamp).map { it.toDailyWaterConsumption() }
    }

    override suspend fun getById(id: Long): DailyWaterConsumption? {
        return dailyWaterConsumptionDao.getById(id)?.toDailyWaterConsumption()
    }

    override suspend fun deleteById(id: Long) {
        dailyWaterConsumptionDao.deleteById(id)
    }

    override suspend fun deleteAll() {
        dailyWaterConsumptionDao.deleteAll()
    }

    override suspend fun save(data: DailyWaterConsumption) {
        dailyWaterConsumptionDao.save(data.toDailyWaterConsumptionDb())
    }

}