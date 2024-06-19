package com.github.lucascalheiros.watertreminder.domain.firstaccess.data

import com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.data.FirstAccessNotificationDataDao
import com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.model.FirstAccessNotificationData
import com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.repositories.FirstAccessNotificationDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalTime

internal class FirstAccessNotificationDataRepositoryImpl(
    private val dao: FirstAccessNotificationDataDao
): FirstAccessNotificationDataRepository {
    override fun firstAccessNotificationFlow(): Flow<FirstAccessNotificationData> {
        return dao.firstAccessNotificationFlow().map {
            FirstAccessNotificationData(
                it.startTime,
                it.stopTime,
                it.frequency,
                it.enabled
            )
        }
    }

    override suspend fun setNotificationStartTime(time: LocalTime) {
        dao.setNotificationStartTime(time)
    }

    override suspend fun setNotificationStopTime(time: LocalTime) {
        dao.setNotificationStopTime(time)
    }

    override suspend fun setNotificationPeriod(time: LocalTime) {
        dao.setNotificationPeriod(time)
    }

    override suspend fun setNotificationState(state: Boolean) {
        dao.setNotificationState(state)
    }
}