package com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.usecases

interface SetFirstAccessNotificationStateUseCase {
    suspend operator fun invoke(value: Boolean)
}