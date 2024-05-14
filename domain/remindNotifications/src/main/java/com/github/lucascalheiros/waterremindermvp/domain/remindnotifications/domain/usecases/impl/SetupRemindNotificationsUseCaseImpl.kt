package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.impl

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.frameworks.NotificationProvider
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.SetupRemindNotificationsUseCase

internal class SetupRemindNotificationsUseCaseImpl(
    private val notificationProvider: NotificationProvider
) : SetupRemindNotificationsUseCase {
    override suspend fun invoke() {
        notificationProvider.setup()
    }
}