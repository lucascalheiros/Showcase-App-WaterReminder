package com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.datetime.LocalTime

interface SetStartNotificationTimeUseCase {
    @NativeCoroutines
    suspend operator fun invoke(time: LocalTime)
}