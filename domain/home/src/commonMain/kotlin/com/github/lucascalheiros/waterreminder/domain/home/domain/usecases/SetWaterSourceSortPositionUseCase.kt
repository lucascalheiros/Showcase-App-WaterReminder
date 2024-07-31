package com.github.lucascalheiros.waterreminder.domain.home.domain.usecases

interface SetWaterSourceSortPositionUseCase {
    suspend operator fun invoke(id: Long, position: Int)
}