package com.github.lucascalheiros.waterreminder.domain.firstaccess

import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.CompleteFirstAccessFlowUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.GetFirstAccessNotificationDataUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.IsDailyIntakeFirstSetUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.IsFirstAccessCompletedUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.SetFirstAccessNotificationStateUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.SetFirstDailyIntakeUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.SetNotificationFrequencyTimeUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.SetStartNotificationTimeUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.SetStopNotificationTimeUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class FirstAccessInjector: KoinComponent {
    fun completeFirstAccessFlowUseCase(): CompleteFirstAccessFlowUseCase = get()
    fun setFirstDailyIntakeUseCase(): SetFirstDailyIntakeUseCase = get()
    fun isFirstAccessCompletedUseCase(): IsFirstAccessCompletedUseCase = get()
    fun isDailyIntakeFirstSetUseCase(): IsDailyIntakeFirstSetUseCase = get()
    fun getFirstAccessNotificationDataUseCase(): GetFirstAccessNotificationDataUseCase = get()
    fun setFirstAccessNotificationStateUseCase(): SetFirstAccessNotificationStateUseCase = get()
    fun setStartNotificationTimeUseCase(): SetStartNotificationTimeUseCase = get()
    fun setStopNotificationTimeUseCase(): SetStopNotificationTimeUseCase = get()
    fun setNotificationFrequencyTimeUseCase(): SetNotificationFrequencyTimeUseCase = get()
}