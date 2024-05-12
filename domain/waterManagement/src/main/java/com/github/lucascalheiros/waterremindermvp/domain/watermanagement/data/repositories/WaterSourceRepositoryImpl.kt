package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.data.repositories

import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemUnit
import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolume
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.WaterSource
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.repositories.WaterSourceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal class WaterSourceRepositoryImpl: WaterSourceRepository {
    val waterSourceType = WaterSourceType(
        1,
        "Water",
        color = 0xFF00FF00,
        1f,
        false

    )
    private val data = MutableStateFlow<List<WaterSource>>(listOf(
        WaterSource(
            1,
            MeasureSystemVolume.create(250.0, MeasureSystemUnit.SI),
            waterSourceType
        )
    ))
    override fun allFlow(): Flow<List<WaterSource>> {
        return data
    }

    override suspend fun all(): List<WaterSource> {
        return data.value
    }

    override suspend fun getById(id: Long): WaterSource? {
        return data.value.find { it.waterSourceId == id }
    }

    override suspend fun deleteById(id: Long) {
        data.update {
            it.toMutableList().apply {
                removeIf { it.waterSourceId == id }
            }
        }
    }

    override suspend fun deleteAll() {
        data.update {
            listOf()
        }
    }

    override suspend fun save(data: WaterSource) {
        this.data.update {
            it.toMutableList().apply {
                add(data)
            }
        }
    }
}