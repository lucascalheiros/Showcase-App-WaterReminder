package com.github.lucascalheiros.waterreminder.domain.watermanagement

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.CreateWaterSourceTypeUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.CreateWaterSourceUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.DeleteConsumedWaterUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.DeleteWaterSourceTypeUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.DeleteWaterSourceUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetConsumedWaterUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDailyWaterConsumptionSummaryUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDailyWaterConsumptionUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDefaultAddDrinkInfoUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDefaultAddWaterSourceInfoUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDefaultVolumeShortcutsUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetTodayWaterConsumptionSummaryUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetWaterSourceTypeUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetWaterSourceUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.RegisterConsumedWaterUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.SaveDailyWaterConsumptionUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class WaterManagementInjector: KoinComponent {
    fun getWaterSourceTypeUseCase(): GetWaterSourceTypeUseCase = get()
    fun getWaterSourceUseCase(): GetWaterSourceUseCase = get()
    fun getTodayWaterConsumptionSummaryUseCase(): GetTodayWaterConsumptionSummaryUseCase = get()
    fun getDefaultAddWaterSourceInfoUseCase(): GetDefaultAddWaterSourceInfoUseCase = get()
    fun getDefaultAddDrinkInfoUseCase(): GetDefaultAddDrinkInfoUseCase = get()
    fun getDefaultVolumeShortcutsUseCase(): GetDefaultVolumeShortcutsUseCase = get()
    fun getDailyWaterConsumptionSummaryUseCase(): GetDailyWaterConsumptionSummaryUseCase = get()
    fun saveDailyWaterConsumptionUseCase(): SaveDailyWaterConsumptionUseCase = get()
    fun registerConsumedWaterUseCase(): RegisterConsumedWaterUseCase = get()
    fun createWaterSourceUseCase(): CreateWaterSourceUseCase = get()
    fun createWaterSourceTypeUseCase(): CreateWaterSourceTypeUseCase = get()
    fun deleteWaterSourceTypeUseCase(): DeleteWaterSourceTypeUseCase = get()
    fun deleteWaterSourceUseCase(): DeleteWaterSourceUseCase = get()
    fun deleteConsumedWaterUseCase(): DeleteConsumedWaterUseCase = get()
    fun getConsumedWaterUseCase(): GetConsumedWaterUseCase = get()
    fun getDailyWaterConsumptionUseCase(): GetDailyWaterConsumptionUseCase = get()
}