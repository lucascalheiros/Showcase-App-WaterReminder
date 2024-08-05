package com.github.lucascalheiros.waterreminder.domain.watermanagement.di

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
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl.CreateWaterSourceTypeUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl.CreateWaterSourceUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl.DeleteConsumedWaterUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl.DeleteWaterSourceTypeUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl.DeleteWaterSourceUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl.GetConsumedWaterUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl.GetDailyWaterConsumptionSummaryUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl.GetDailyWaterConsumptionUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl.GetDefaultAddDrinkInfoUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl.GetDefaultAddWaterSourceInfoUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl.GetDefaultVolumeShortcutsUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl.GetTodayWaterConsumptionSummaryUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl.GetWaterSourceTypeUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl.GetWaterSourceUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl.RegisterConsumedWaterUseCaseImpl
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl.SaveDailyWaterConsumptionUseCaseImpl
import com.github.lucascalheiros.waterreminder.measuresystem.di.domainMeasureSystemModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val domainWaterManagementModule = module {
    singleOf(::CreateWaterSourceUseCaseImpl) bind CreateWaterSourceUseCase::class
    singleOf(::GetWaterSourceUseCaseImpl) bind GetWaterSourceUseCase::class
    singleOf(::GetWaterSourceTypeUseCaseImpl) bind GetWaterSourceTypeUseCase::class
    singleOf(::DeleteWaterSourceUseCaseImpl) bind DeleteWaterSourceUseCase::class
    singleOf(::GetConsumedWaterUseCaseImpl) bind GetConsumedWaterUseCase::class
    singleOf(::GetDailyWaterConsumptionUseCaseImpl) bind GetDailyWaterConsumptionUseCase::class
    singleOf(::SaveDailyWaterConsumptionUseCaseImpl) bind SaveDailyWaterConsumptionUseCase::class
    singleOf(::RegisterConsumedWaterUseCaseImpl) bind RegisterConsumedWaterUseCase::class
    singleOf(::GetDailyWaterConsumptionSummaryUseCaseImpl) bind GetDailyWaterConsumptionSummaryUseCase::class
    singleOf(::DeleteConsumedWaterUseCaseImpl) bind DeleteConsumedWaterUseCase::class
    singleOf(::GetDefaultAddWaterSourceInfoUseCaseImpl) bind GetDefaultAddWaterSourceInfoUseCase::class
    singleOf(::CreateWaterSourceTypeUseCaseImpl) bind CreateWaterSourceTypeUseCase::class
    singleOf(::GetDefaultAddDrinkInfoUseCaseImpl) bind GetDefaultAddDrinkInfoUseCase::class
    singleOf(::GetDefaultVolumeShortcutsUseCaseImpl) bind GetDefaultVolumeShortcutsUseCase::class
    singleOf(::DeleteWaterSourceTypeUseCaseImpl) bind DeleteWaterSourceTypeUseCase::class
    singleOf(::GetTodayWaterConsumptionSummaryUseCaseImpl) bind GetTodayWaterConsumptionSummaryUseCase::class
} + domainMeasureSystemModule
