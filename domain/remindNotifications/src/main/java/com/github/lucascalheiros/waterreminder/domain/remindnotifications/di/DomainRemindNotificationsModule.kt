package com.github.lucascalheiros.waterreminder.domain.remindnotifications.di

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.CreateScheduleNotificationUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.DeleteScheduledNotificationUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.GetScheduledNotificationsUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.GetWeekDayNotificationStateUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.IsNotificationsEnabledUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.SetNotificationsEnabledUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.SetWeekDayNotificationStateUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.impl.CreateScheduleNotificationUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.impl.DeleteScheduledNotificationUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.impl.GetScheduledNotificationsUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.impl.GetWeekDayNotificationStateUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.impl.IsNotificationsEnabledUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.impl.SetNotificationsEnabledUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.impl.SetWeekDayNotificationStateUseCaseImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val domainRemindNotificationsModule  = module {
    singleOf(::DeleteScheduledNotificationUseCaseImpl) bind DeleteScheduledNotificationUseCase::class
    singleOf(::GetScheduledNotificationsUseCaseImpl) bind GetScheduledNotificationsUseCase::class
    singleOf(::CreateScheduleNotificationUseCaseImpl) bind CreateScheduleNotificationUseCase::class
    singleOf(::GetWeekDayNotificationStateUseCaseImpl) bind GetWeekDayNotificationStateUseCase::class
    singleOf(::SetWeekDayNotificationStateUseCaseImpl) bind SetWeekDayNotificationStateUseCase::class
    singleOf(::IsNotificationsEnabledUseCaseImpl) bind IsNotificationsEnabledUseCase::class
    singleOf(::SetNotificationsEnabledUseCaseImpl) bind SetNotificationsEnabledUseCase::class
}