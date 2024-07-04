package com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.home.domain.repositories.DrinkSortPositionRepository
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.GetSortedDrinkUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetWaterSourceTypeUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

internal class GetSortedDrinkUseCaseImpl(
    private val drinkSortPositionRepository: DrinkSortPositionRepository,
    private val getWaterSourceTypeUseCase: GetWaterSourceTypeUseCase,
    ): GetSortedDrinkUseCase {
    override fun invoke(): Flow<List<WaterSourceType>> {
        return combine(drinkSortPositionRepository.sortedIds(), getWaterSourceTypeUseCase()) { sortedIds, drinks ->
            val desiredOrder = sortedIds.mapIndexed { index, id -> id to index }.associateBy { it.first }
            drinks.sortedBy { desiredOrder[it.waterSourceTypeId]?.second ?: Int.MAX_VALUE  }        }
    }
}