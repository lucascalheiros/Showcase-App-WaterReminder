package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ConsumedWater
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.ConsumedWaterRequest
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface GetConsumedWaterUseCase {
    @NativeCoroutines
    operator fun invoke(input: ConsumedWaterRequest.FromTimeInterval): Flow<List<ConsumedWater>>
    @NativeCoroutines
    operator fun invoke(input: ConsumedWaterRequest.ItemsFromLastDays): Flow<List<ConsumedWater>>
}
