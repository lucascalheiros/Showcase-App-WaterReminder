package com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories

import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.coverters.toDailyWaterConsumption
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.DailyWaterConsumptionDao
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumption
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.DailyWaterConsumptionRepository
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DailyWaterConsumptionRepositoryImpl(
    private val dailyWaterConsumptionDao: DailyWaterConsumptionDao,
): DailyWaterConsumptionRepository {

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

    override suspend fun save(volume: MeasureSystemVolume) {
        dailyWaterConsumptionDao.save(volume)
    }

}