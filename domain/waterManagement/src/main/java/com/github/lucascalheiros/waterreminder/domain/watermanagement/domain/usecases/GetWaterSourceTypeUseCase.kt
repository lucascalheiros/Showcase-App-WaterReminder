package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import kotlinx.coroutines.flow.Flow

interface GetWaterSourceTypeUseCase {
    operator fun invoke(): Flow<List<WaterSourceType>>
    suspend operator fun invoke(id: Long): WaterSourceType?
    suspend fun single(): List<WaterSourceType>
}