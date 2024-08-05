package com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface IsDailyIntakeFirstSetUseCase {
    @NativeCoroutines
    suspend operator fun invoke(): Boolean
}