package com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.home.domain.repositories.DrinkSortPositionRepository
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.GetSortedDrinkUseCase
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.SetDrinkSortPositionUseCase
import kotlinx.coroutines.flow.first

class SetDrinkSortPositionUseCaseImpl(
    private val drinkSortPositionRepository: DrinkSortPositionRepository,
    private val getSortedDrinkUseCase: GetSortedDrinkUseCase,
): SetDrinkSortPositionUseCase {
    override suspend fun invoke(id: Long, position: Int) {
        val currentSort = getSortedDrinkUseCase().first()
            .map { it.waterSourceTypeId }
            .toMutableList()
            .also {
                it.remove(id)
                it.add(position, id)
            }
        drinkSortPositionRepository.setSortPosition(currentSort)
    }
}