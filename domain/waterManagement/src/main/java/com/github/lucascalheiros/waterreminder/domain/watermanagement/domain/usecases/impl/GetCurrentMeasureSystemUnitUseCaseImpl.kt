package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.common.measuresystem.MeasureSystemUnit
import com.github.lucascalheiros.waterreminder.common.util.requests.AsyncRequest
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.MeasureSystemUnitRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetCurrentMeasureSystemUnitUseCase
import kotlinx.coroutines.flow.Flow

class GetCurrentMeasureSystemUnitUseCaseImpl(
    private val measureSystemUnitRepository: MeasureSystemUnitRepository
): GetCurrentMeasureSystemUnitUseCase {
    override suspend fun invoke(ignore: AsyncRequest.Single): MeasureSystemUnit {
        return measureSystemUnitRepository.getCurrentMeasureSystemUnit()
    }

    override fun invoke(ignore: AsyncRequest.Continuous): Flow<MeasureSystemUnit> {
        return measureSystemUnitRepository.getCurrentMeasureSystemUnitFlow()
    }
}