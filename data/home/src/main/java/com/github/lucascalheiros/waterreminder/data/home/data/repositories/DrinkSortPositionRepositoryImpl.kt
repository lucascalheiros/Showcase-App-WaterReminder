package com.github.lucascalheiros.waterreminder.data.home.data.repositories

import com.github.lucascalheiros.waterreminder.data.home.data.models.PositionSourceType
import com.github.lucascalheiros.waterreminder.data.home.data.repositories.datasources.dao.PositionTrackDao
import com.github.lucascalheiros.waterreminder.domain.home.domain.repositories.DrinkSortPositionRepository
import kotlinx.coroutines.flow.Flow

class DrinkSortPositionRepositoryImpl(
    private val dao: PositionTrackDao
): DrinkSortPositionRepository {
    override fun sortedIds(): Flow<List<Long>> {
        return dao.all(PositionSourceType.Drink)
    }

    override suspend fun setSortPosition(ids: List<Long>) {
        dao.save(PositionSourceType.Drink, ids)
    }
}