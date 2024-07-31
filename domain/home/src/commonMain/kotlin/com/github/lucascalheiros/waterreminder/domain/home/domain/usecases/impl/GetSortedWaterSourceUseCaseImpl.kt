package com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.home.domain.repositories.WaterSourceSortPositionRepository
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.GetSortedWaterSourceUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSource
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetWaterSourceUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

internal class GetSortedWaterSourceUseCaseImpl(
    private val waterSourceSortPositionRepository: WaterSourceSortPositionRepository,
    private val getWaterSourceUseCase: GetWaterSourceUseCase,
): GetSortedWaterSourceUseCase {
    override fun invoke(): Flow<List<WaterSource>> {
        return combine(waterSourceSortPositionRepository.sortedIds(), getWaterSourceUseCase()) { sortedIds, waterSources ->
            val desiredOrder = sortedIds.mapIndexed { index, id -> id to index }.associateBy { it.first }
            waterSources.sortedBy { desiredOrder[it.waterSourceId]?.second ?: Int.MAX_VALUE  }
        }
    }
}