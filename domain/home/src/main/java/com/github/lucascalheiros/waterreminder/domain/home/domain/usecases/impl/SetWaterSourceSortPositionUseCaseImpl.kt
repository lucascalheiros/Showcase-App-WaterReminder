package com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.home.domain.repositories.WaterSourceSortPositionRepository
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.GetSortedWaterSourceUseCase
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.SetWaterSourceSortPositionUseCase
import kotlinx.coroutines.flow.first

class SetWaterSourceSortPositionUseCaseImpl(
    private val waterSourceSortPositionRepository: WaterSourceSortPositionRepository,
    private val getSortedWaterSourceUseCase: GetSortedWaterSourceUseCase
) : SetWaterSourceSortPositionUseCase {
    override suspend fun invoke(id: Long, position: Int) {
        val currentSort = getSortedWaterSourceUseCase().first()
            .map { it.waterSourceId }
            .toMutableList()
            .also {
                it.remove(id)
                it.add(position, id)
            }
        waterSourceSortPositionRepository.setSortPosition(currentSort)
    }
}