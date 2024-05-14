package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.repositories.WaterSourceTypeRepository
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.GetWaterSourceTypeUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.requests.AsyncRequest
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