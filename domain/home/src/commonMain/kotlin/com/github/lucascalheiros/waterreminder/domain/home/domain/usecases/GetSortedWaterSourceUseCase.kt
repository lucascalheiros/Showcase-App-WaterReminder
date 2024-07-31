package com.github.lucascalheiros.waterreminder.domain.home.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSource
import kotlinx.coroutines.flow.Flow

interface GetSortedWaterSourceUseCase {
    operator fun invoke(): Flow<List<WaterSource>>
}