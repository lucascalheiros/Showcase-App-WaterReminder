package com.github.lucascalheiros.waterreminder.domain.home.domain.usecases

interface SetDrinkSortPositionUseCase {
    suspend operator fun invoke(id: Long, position: Int)
}