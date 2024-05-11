package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.WaterSource
import kotlinx.coroutines.flow.Flow

interface GetWaterSourceUseCase {
    operator fun invoke(): Flow<List<WaterSource>>
}