package com.github.lucascalheiros.waterreminder.data.home.data.repositories

import com.github.lucascalheiros.waterreminder.data.home.data.models.PositionSourceType
import com.github.lucascalheiros.waterreminder.data.home.data.repositories.datasources.dao.PositionTrackDao
import com.github.lucascalheiros.waterreminder.domain.home.domain.repositories.WaterSourceSortPositionRepository
import kotlinx.coroutines.flow.Flow

class WaterSourceSortPositionRepositoryImpl(
    private val dao: PositionTrackDao
): WaterSourceSortPositionRepository {
    override fun sortedIds(): Flow<List<Long>> {
        return dao.all(PositionSourceType.WaterSource)
    }

    override suspend fun setSortPosition(ids: List<Long>) {
        dao.save(PositionSourceType.WaterSource, ids)
    }
}