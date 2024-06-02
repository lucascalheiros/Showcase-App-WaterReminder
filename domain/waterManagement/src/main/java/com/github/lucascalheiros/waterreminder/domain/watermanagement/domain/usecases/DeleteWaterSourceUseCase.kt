package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

interface DeleteWaterSourceUseCase {
    suspend operator fun invoke(waterSourceId: Long)
}
