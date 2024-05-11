package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.WaterSource
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.repositories.WaterSourceRepository
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.GetCurrentMeasureSystemUnitUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.GetWaterSourceUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.requests.AsyncRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

internal class GetWaterSourceUseCaseImpl(
    private val waterSourceRepository: WaterSourceRepository,
    private val getCurrentMeasureSystemUnitUseCase: GetCurrentMeasureSystemUnitUseCase
): GetWaterSourceUseCase {

    override fun invoke(): Flow<List<WaterSource>> {
        return combine(
            waterSourceRepository.allFlow(),
            getCurrentMeasureSystemUnitUseCase(AsyncRequest.Continuous)
        ) { list, unit ->
            list.map { it.copy(volume = it.volume.toUnit(unit)) }
        }
    }

}