package com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases

import com.github.lucascalheiros.waterreminder.common.util.requests.AsyncRequest
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemUnit
import kotlinx.coroutines.flow.Flow

interface GetCurrentMeasureSystemUnitUseCase {
    suspend operator fun invoke(ignore: AsyncRequest.Single): MeasureSystemUnit
    operator fun invoke(ignore: AsyncRequest.Continuous): Flow<MeasureSystemUnit>
}
