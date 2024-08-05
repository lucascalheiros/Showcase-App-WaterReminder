package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DefaultAddDrinkInfo
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface GetDefaultAddDrinkInfoUseCase {
    @NativeCoroutines
    suspend operator fun invoke(): DefaultAddDrinkInfo
}
