package com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface SetFirstAccessNotificationStateUseCase {
    @NativeCoroutines
    suspend operator fun invoke(value: Boolean)
}