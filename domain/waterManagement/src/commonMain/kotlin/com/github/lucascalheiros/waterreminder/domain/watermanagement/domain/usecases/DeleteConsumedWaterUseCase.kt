package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

interface DeleteConsumedWaterUseCase {
    suspend operator fun invoke(consumedWaterId: Long)
}
