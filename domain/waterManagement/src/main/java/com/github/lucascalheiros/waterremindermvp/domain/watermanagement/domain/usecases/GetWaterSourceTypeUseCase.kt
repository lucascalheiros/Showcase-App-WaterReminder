package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.requests.AsyncRequest
import kotlinx.coroutines.flow.Flow

interface GetWaterSourceTypeUseCase {
    operator fun invoke(ignore: AsyncRequest.Continuous): Flow<List<WaterSourceType>>
    suspend operator fun invoke(ignore: AsyncRequest.Single): List<WaterSourceType>
}