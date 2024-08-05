package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DefaultVolumeShortcuts
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface GetDefaultVolumeShortcutsUseCase {
    @NativeCoroutines
    suspend operator fun invoke(): DefaultVolumeShortcuts
}