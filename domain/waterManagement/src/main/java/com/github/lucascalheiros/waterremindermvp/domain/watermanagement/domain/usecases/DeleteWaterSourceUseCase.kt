package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases

interface DeleteWaterSourceUseCase {
    suspend operator fun invoke(waterSourceId: Long)
}
