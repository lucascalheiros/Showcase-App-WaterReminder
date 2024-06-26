package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.ConsumedWaterRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.DeleteConsumedWaterUseCase

internal class DeleteConsumedWaterUseCaseImpl(
    private val consumedWaterRepository: ConsumedWaterRepository
) : DeleteConsumedWaterUseCase {
    override suspend fun invoke(consumedWaterId: Long) {
        consumedWaterRepository.deleteById(consumedWaterId)
    }

}