package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.UserProfile
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface GetCalculatedIntakeUseCase {
    @NativeCoroutines
    operator fun invoke(): Flow<MeasureSystemVolume>
    @NativeCoroutines
    suspend fun single(): MeasureSystemVolume
    @NativeCoroutines
    suspend operator fun invoke(userProfile: UserProfile): MeasureSystemVolume
}