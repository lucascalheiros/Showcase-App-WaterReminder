package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DefaultVolumeShortcuts

interface GetDefaultVolumeShortcutsUseCase {
    suspend operator fun invoke(): DefaultVolumeShortcuts
}