package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases

interface DeleteConsumedWaterUseCase {
    suspend operator fun invoke(consumedWaterId: Long)
}
