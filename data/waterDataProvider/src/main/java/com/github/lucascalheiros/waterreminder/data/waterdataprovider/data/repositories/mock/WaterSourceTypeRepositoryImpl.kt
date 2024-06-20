package com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.mock

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.WaterSourceTypeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal class WaterSourceTypeRepositoryImpl : WaterSourceTypeRepository {
    private val data = MutableStateFlow<List<WaterSourceType>>(
        listOf(
            WaterSourceType(
                1,
                "Water",
                lightColor = 0xFF0000FF,
                darkColor = 0xFFADD8E6,
                1f,
                false
            ),
            WaterSourceType(
                2,
                "Coffee",
                lightColor = 0xFF5C4033,
                darkColor = 0xFFC4A484,
                1f,
                false
            ),
            WaterSourceType(
                2,
                "Coffee",
                lightColor = 0xFF5C4033,
                darkColor = 0xFFC4A484,
                1f,
                false
            ),
            WaterSourceType(
                2,
                "Coffee",
                lightColor = 0xFF5C4033,
                darkColor = 0xFFC4A484,
                1f,
                false
            ),
            WaterSourceType(
                2,
                "Coffee",
                lightColor = 0xFF5C4033,
                darkColor = 0xFFC4A484,
                1f,
                false
            ),
            WaterSourceType(
                2,
                "Coffee",
                lightColor = 0xFF5C4033,
                darkColor = 0xFFC4A484,
                1f,
                false
            ),
            WaterSourceType(
                2,
                "Coffee",
                lightColor = 0xFF5C4033,
                darkColor = 0xFFC4A484,
                1f,
                false
            ),
            WaterSourceType(
                2,
                "Coffee",
                lightColor = 0xFF5C4033,
                darkColor = 0xFFC4A484,
                1f,
                false
            ),
            )
    )

    override fun allFlow(): Flow<List<WaterSourceType>> {
        return data
    }

    override suspend fun all(): List<WaterSourceType> {
        return data.value
    }

    override suspend fun getById(id: Long): WaterSourceType? {
        return data.value.find { it.waterSourceTypeId == id }
    }

    override suspend fun deleteById(id: Long) {
        data.update {
            it.toMutableList().apply {
                removeIf { it.waterSourceTypeId == id }
            }
        }
    }

    override suspend fun deleteAll() {
        data.update {
            listOf()
        }
    }

    override suspend fun save(data: WaterSourceType) {
        this.data.update {
            it.toMutableList().apply {
                add(data)
            }
        }
    }
}