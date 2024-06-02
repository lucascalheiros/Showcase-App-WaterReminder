package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.common.util.requests.AsyncRequest
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.WaterSourceTypeRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetWaterSourceTypeUseCase
import kotlinx.coroutines.flow.Flow

internal class GetWaterSourceTypeUseCaseImpl(
    private val waterSourceRepository: WaterSourceTypeRepository
): GetWaterSourceTypeUseCase {

    override fun invoke(ignore: AsyncRequest.Continuous): Flow<List<WaterSourceType>> {
        return waterSourceRepository.allFlow()
    }

    override suspend fun invoke(ignore: AsyncRequest.Single): List<WaterSourceType> {
        return waterSourceRepository.all()
    }

}