package com.github.lucascalheiros.waterreminder.domain.home.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import kotlinx.coroutines.flow.Flow

interface GetSortedDrinkUseCase {
    operator fun invoke(): Flow<List<WaterSourceType>>
}