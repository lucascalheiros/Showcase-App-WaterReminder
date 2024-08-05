package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface SetNotificationsEnabledUseCase {
    @NativeCoroutines
    suspend operator fun invoke(isEnabled: Boolean)
}