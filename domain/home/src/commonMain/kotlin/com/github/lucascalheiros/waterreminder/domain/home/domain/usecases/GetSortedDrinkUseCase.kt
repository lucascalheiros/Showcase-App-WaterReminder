package com.github.lucascalheiros.waterreminder.domain.home.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface GetSortedDrinkUseCase {
    @NativeCoroutines
    operator fun invoke(): Flow<List<WaterSourceType>>
}