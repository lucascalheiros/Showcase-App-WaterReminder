package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.WaterSource

interface CreateWaterSourceUseCase {
    suspend operator fun invoke(waterSource: WaterSource)
}
