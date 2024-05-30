package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemUnit
import com.github.lucascalheiros.waterremindermvp.common.util.requests.AsyncRequest
import kotlinx.coroutines.flow.Flow

interface GetCurrentMeasureSystemUnitUseCase {
    suspend operator fun invoke(ignore: AsyncRequest.Single): MeasureSystemUnit
    operator fun invoke(ignore: AsyncRequest.Continuous): Flow<MeasureSystemUnit>
}
