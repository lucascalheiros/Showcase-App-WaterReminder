package com.github.lucascalheiros.waterreminder.data.notificationprovider.data

import kotlinx.coroutines.flow.Flow

interface NotificationEnabledDataSource {
    suspend fun isNotificationEnabled(): Boolean
    fun isNotificationEnabledFlow(): Flow<Boolean>
    suspend fun setNotificationEnabled(isEnabled: Boolean)
}