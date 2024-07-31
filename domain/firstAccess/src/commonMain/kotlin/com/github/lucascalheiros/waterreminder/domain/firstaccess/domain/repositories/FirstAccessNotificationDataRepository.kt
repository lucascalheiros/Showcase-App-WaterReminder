package com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.repositories

import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.model.FirstAccessNotificationData
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalTime

interface FirstAccessNotificationDataRepository {
    fun firstAccessNotificationFlow(): Flow<FirstAccessNotificationData>
    suspend fun setNotificationStartTime(time: LocalTime)
    suspend fun setNotificationStopTime(time: LocalTime)
    suspend fun setNotificationPeriod(time: LocalTime)
    suspend fun setNotificationState(state: Boolean)
}