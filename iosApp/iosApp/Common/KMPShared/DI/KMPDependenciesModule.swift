//
//  KMPDependenciesModule.swift
//  KMPShared
//
//  Created by Lucas Calheiros on 06/10/24.
//

import Shared
import Factory

extension Container {
    var sharedInjector: Factory<SharedInjector> {
        self { SharedInjector() }
            .singleton
    }
    var createWaterSourceUseCase: Factory<CreateWaterSourceUseCase> {
        self { Container.shared.sharedInjector().createWaterSourceUseCase() }
    }
    var getDefaultAddWaterSourceInfoUseCase: Factory<GetDefaultAddWaterSourceInfoUseCase> {
        self { Container.shared.sharedInjector().getDefaultAddWaterSourceInfoUseCase() }
    }
    var getWaterSourceTypeUseCase: Factory<GetWaterSourceTypeUseCase> {
        self { Container.shared.sharedInjector().getWaterSourceTypeUseCase() }
    }
    var getWaterSourceUseCase: Factory<GetWaterSourceUseCase> {
        self { Container.shared.sharedInjector().getWaterSourceUseCase() }
    }
    var getTodayWaterConsumptionSummaryUseCase: Factory<GetTodayWaterConsumptionSummaryUseCase> {
        self { Container.shared.sharedInjector().getTodayWaterConsumptionSummaryUseCase() }
    }
    var registerConsumedWaterUseCase: Factory<RegisterConsumedWaterUseCase> {
        self { Container.shared.sharedInjector().registerConsumedWaterUseCase() }
    }
    var getDefaultVolumeShortcutsUseCase: Factory<GetDefaultVolumeShortcutsUseCase> {
        self { Container.shared.sharedInjector().getDefaultVolumeShortcutsUseCase() }
    }
    var deleteWaterSourceUseCase: Factory<DeleteWaterSourceUseCase> {
        self { Container.shared.sharedInjector().deleteWaterSourceUseCase() }
    }
    var deleteWaterSourceTypeUseCase: Factory<DeleteWaterSourceTypeUseCase> {
        self { Container.shared.sharedInjector().deleteWaterSourceTypeUseCase() }
    }
    var getDailyWaterConsumptionSummaryUseCase: Factory<GetDailyWaterConsumptionSummaryUseCase> {
        self { Container.shared.sharedInjector().getDailyWaterConsumptionSummaryUseCase() }
    }
    var deleteConsumedWaterUseCase: Factory<DeleteConsumedWaterUseCase> {
        self { Container.shared.sharedInjector().deleteConsumedWaterUseCase() }
    }
    var getHistoryChartDataUseCase: Factory<GetHistoryChartDataUseCase> {
        self { Container.shared.sharedInjector().getHistoryChartDataUseCase() }
    }
    var getDailyWaterConsumptionUseCase: Factory<GetDailyWaterConsumptionUseCase> {
        self { Container.shared.sharedInjector().getDailyWaterConsumptionUseCase() }
    }
    var getThemeUseCase: Factory<GetThemeUseCase> {
        self { Container.shared.sharedInjector().getThemeUseCase() }
    }
    var getVolumeUnitUseCase: Factory<GetVolumeUnitUseCase> {
        self { Container.shared.sharedInjector().getVolumeUnitUseCase() }
    }
    var getWeightUnitUseCase: Factory<GetWeightUnitUseCase> {
        self { Container.shared.sharedInjector().getWeightUnitUseCase() }
    }
    var getTemperatureUnitUseCase: Factory<GetTemperatureUnitUseCase> {
        self { Container.shared.sharedInjector().getTemperatureUnitUseCase() }
    }
    var setTemperatureUnitUseCase: Factory<SetTemperatureUnitUseCase> {
        self { Container.shared.sharedInjector().setTemperatureUnitUseCase() }
    }
    var setThemeUseCase: Factory<SetThemeUseCase> {
        self { Container.shared.sharedInjector().setThemeUseCase() }
    }
    var saveDailyWaterConsumptionUseCase: Factory<SaveDailyWaterConsumptionUseCase> {
        self { Container.shared.sharedInjector().saveDailyWaterConsumptionUseCase() }
    }
    var getUserProfileUseCase: Factory<GetUserProfileUseCase> {
        self { Container.shared.sharedInjector().getUserProfileUseCase() }
    }
    var setWeightUnitUseCase: Factory<SetWeightUnitUseCase> {
        self { Container.shared.sharedInjector().setWeightUnitUseCase() }
    }
    var getCalculatedIntakeUseCase: Factory<GetCalculatedIntakeUseCase> {
        self { Container.shared.sharedInjector().getCalculatedIntakeUseCase() }
    }
    var setUserProfileNameUseCase: Factory<SetUserProfileNameUseCase> {
        self { Container.shared.sharedInjector().setUserProfileNameUseCase() }
    }
    var setUserProfileWeightUseCase: Factory<SetUserProfileWeightUseCase> {
        self { Container.shared.sharedInjector().setUserProfileWeightUseCase() }
    }
    var setUserProfileActivityLevelUseCase: Factory<SetUserProfileActivityLevelUseCase> {
        self { Container.shared.sharedInjector().setUserProfileActivityLevelUseCase() }
    }
    var setUserProfileTemperatureLevelUseCase: Factory<SetUserProfileTemperatureLevelUseCase> {
        self { Container.shared.sharedInjector().setUserProfileTemperatureLevelUseCase() }
    }
    var isNotificationsEnabledUseCase: Factory<IsNotificationsEnabledUseCase> {
        self { Container.shared.sharedInjector().isNotificationsEnabledUseCase() }
    }
    var setNotificationsEnabledUseCase: Factory<SetNotificationsEnabledUseCase> {
        self { Container.shared.sharedInjector().setNotificationsEnabledUseCase() }
    }
    var getScheduledNotificationsUseCase: Factory<GetScheduledNotificationsUseCase> {
        self { Container.shared.sharedInjector().getScheduledNotificationsUseCase() }
    }
    var deleteScheduledNotificationUseCase: Factory<DeleteScheduledNotificationUseCase> {
        self { Container.shared.sharedInjector().deleteScheduledNotificationUseCase() }
    }
    var setWeekDayNotificationStateUseCase: Factory<SetWeekDayNotificationStateUseCase> {
        self { Container.shared.sharedInjector().setWeekDayNotificationStateUseCase() }
    }
    var createScheduleNotificationUseCase: Factory<CreateScheduleNotificationUseCase> {
        self { Container.shared.sharedInjector().createScheduleNotificationUseCase() }
    }
    var createWaterSourceTypeUseCase: Factory<CreateWaterSourceTypeUseCase> {
        self { Container.shared.sharedInjector().createWaterSourceTypeUseCase() }
    }
    var completeFirstAccessFlowUseCase: Factory<CompleteFirstAccessFlowUseCase> {
        self { Container.shared.sharedInjector().completeFirstAccessFlowUseCase() }
    }
    var getFirstAccessNotificationDataUseCase: Factory<GetFirstAccessNotificationDataUseCase> {
        self { Container.shared.sharedInjector().getFirstAccessNotificationDataUseCase() }
    }
    var isDailyIntakeFirstSetUseCase: Factory<IsDailyIntakeFirstSetUseCase> {
        self { Container.shared.sharedInjector().isDailyIntakeFirstSetUseCase() }
    }
    var setFirstAccessNotificationStateUseCase: Factory<SetFirstAccessNotificationStateUseCase> {
        self { Container.shared.sharedInjector().setFirstAccessNotificationStateUseCase() }
    }
    var setFirstDailyIntakeUseCase: Factory<SetFirstDailyIntakeUseCase> {
        self { Container.shared.sharedInjector().setFirstDailyIntakeUseCase() }
    }
    var setNotificationFrequencyTimeUseCase: Factory<SetNotificationFrequencyTimeUseCase> {
        self { Container.shared.sharedInjector().setNotificationFrequencyTimeUseCase() }
    }
    var setStartNotificationTimeUseCase: Factory<SetStartNotificationTimeUseCase> {
        self { Container.shared.sharedInjector().setStartNotificationTimeUseCase() }
    }
    var setStopNotificationTimeUseCase: Factory<SetStopNotificationTimeUseCase> {
        self { Container.shared.sharedInjector().setStopNotificationTimeUseCase() }
    }
    var setVolumeUnitUseCase: Factory<SetVolumeUnitUseCase> {
        self { Container.shared.sharedInjector().setVolumeUnitUseCase() }
    }
}
