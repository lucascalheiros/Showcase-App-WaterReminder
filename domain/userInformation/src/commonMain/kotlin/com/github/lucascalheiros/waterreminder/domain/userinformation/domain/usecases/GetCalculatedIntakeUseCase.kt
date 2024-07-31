package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.UserProfile
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import kotlinx.coroutines.flow.Flow

interface GetCalculatedIntakeUseCase {
    operator fun invoke(): Flow<MeasureSystemVolume>
    suspend fun single(): MeasureSystemVolume
    suspend operator fun invoke(userProfile: UserProfile): MeasureSystemVolume
}