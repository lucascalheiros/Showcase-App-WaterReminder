package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DefaultAddWaterSourceInfo
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface GetDefaultAddWaterSourceInfoUseCase {
    @NativeCoroutines
    suspend operator fun invoke(): DefaultAddWaterSourceInfo
}
