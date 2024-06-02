package com.github.lucascalheiros.waterreminder.domain.watermanagement.data.repositories

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumption
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.DailyWaterConsumptionRepository
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock

internal class DailyWaterConsumptionRepositoryImpl: DailyWaterConsumptionRepository {

    private val data = MutableStateFlow<List<DailyWaterConsumption>>(listOf(
        DailyWaterConsumption(
            1,
            MeasureSystemVolume.Companion.create(2000.0, MeasureSystemVolumeUnit.ML),
            Clock.System.now().toEpochMilliseconds()
        )
    ))
    override fun allFlow(): Flow<List<DailyWaterConsumption>> {
        return data
    }

    override suspend fun all(): List<DailyWaterConsumption> {
        return data.value
    }

    override suspend fun getById(id: Long): DailyWaterConsumption? {
        return data.value.find { it.dailyWaterConsumptionId == id }
    }

    override suspend fun deleteById(id: Long) {
        data.update {
            it.toMutableList().apply {
                removeIf { it.dailyWaterConsumptionId == id }
            }
        }
    }

    override suspend fun deleteAll() {
        data.update {
            listOf()
        }
    }

    override suspend fun save(data: DailyWaterConsumption) {
        this.data.update {
            it.toMutableList().apply {
                add(data)
            }
        }
    }
    override fun allByPeriodFlow(
        startTimestamp: Long,
        endTimestamp: Long
    ): Flow<List<DailyWaterConsumption>> {
        return data.map { list ->
            list.filter { it.date in startTimestamp..endTimestamp }
        }
    }

    override suspend fun allByPeriod(
        startTimestamp: Long,
        endTimestamp: Long
    ): List<DailyWaterConsumption> {
        return data.value.filter { it.date in startTimestamp..endTimestamp }
    }

}