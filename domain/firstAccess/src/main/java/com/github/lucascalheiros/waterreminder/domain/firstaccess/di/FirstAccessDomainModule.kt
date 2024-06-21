package com.github.lucascalheiros.waterreminder.domain.firstaccess.di

import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.CompleteFirstAccessFlowUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.GetFirstAccessNotificationDataUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.IsDailyIntakeFirstSetUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.IsFirstAccessCompletedUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.SetFirstAccessNotificationStateUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.SetFirstDailyIntakeUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.SetNotificationFrequencyTimeUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.SetStartNotificationTimeUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.SetStopNotificationTimeUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.impl.CompleteFirstAccessFlowUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.impl.GetFirstAccessNotificationDataUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.impl.IsDailyIntakeFirstSetUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.impl.IsFirstAccessCompletedUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.impl.SetFirstAccessNotificationStateUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.impl.SetFirstDailyIntakeUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.impl.SetNotificationFrequencyTimeUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.impl.SetStartNotificationTimeUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.impl.SetStopNotificationTimeUseCaseImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val firstAccessDomainModule = module {
    singleOf(::CompleteFirstAccessFlowUseCaseImpl) bind CompleteFirstAccessFlowUseCase::class
    singleOf(::SetFirstDailyIntakeUseCaseImpl) bind SetFirstDailyIntakeUseCase::class
    singleOf(::IsFirstAccessCompletedUseCaseImpl) bind IsFirstAccessCompletedUseCase::class
    singleOf(::IsDailyIntakeFirstSetUseCaseImpl) bind IsDailyIntakeFirstSetUseCase::class
    singleOf(::GetFirstAccessNotificationDataUseCaseImpl) bind GetFirstAccessNotificationDataUseCase::class
    singleOf(::SetFirstAccessNotificationStateUseCaseImpl) bind SetFirstAccessNotificationStateUseCase::class
    singleOf(::SetStartNotificationTimeUseCaseImpl) bind SetStartNotificationTimeUseCase::class
    singleOf(::SetStopNotificationTimeUseCaseImpl) bind SetStopNotificationTimeUseCase::class
    singleOf(::SetNotificationFrequencyTimeUseCaseImpl) bind SetNotificationFrequencyTimeUseCase::class
}