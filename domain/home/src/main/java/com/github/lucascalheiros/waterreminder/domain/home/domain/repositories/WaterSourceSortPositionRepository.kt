package com.github.lucascalheiros.waterreminder.domain.home.domain.repositories

import kotlinx.coroutines.flow.Flow

interface WaterSourceSortPositionRepository {
    fun sortedIds(): Flow<List<Long>>
    suspend fun setSortPosition(ids: List<Long>)
}