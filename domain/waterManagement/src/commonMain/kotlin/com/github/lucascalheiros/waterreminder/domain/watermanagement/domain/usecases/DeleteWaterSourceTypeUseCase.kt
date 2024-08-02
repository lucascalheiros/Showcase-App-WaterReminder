package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

interface DeleteWaterSourceTypeUseCase {
    suspend operator fun invoke(waterSourceId: Long)
}
