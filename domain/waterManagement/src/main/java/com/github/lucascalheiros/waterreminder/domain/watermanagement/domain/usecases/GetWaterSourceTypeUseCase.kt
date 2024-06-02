package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.common.util.requests.AsyncRequest
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import kotlinx.coroutines.flow.Flow

interface GetWaterSourceTypeUseCase {
    operator fun invoke(ignore: AsyncRequest.Continuous): Flow<List<WaterSourceType>>
    suspend operator fun invoke(ignore: AsyncRequest.Single): List<WaterSourceType>
}