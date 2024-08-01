package com.github.lucascalheiros.waterreminder.data.notificationprovider.framework

interface AlarmManagerWrapper {
    fun createAlarmSchedule(dayTimeInMinutes: Int)
    fun cancelAlarmSchedule(dayTimeInMinutes: Int)
}