//
//  KMPExtensions.swift
//  iosApp
//
//  Created by Lucas Calheiros on 03/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared
import Combine
import KMPNativeCoroutinesCombine
import KMPNativeCoroutinesAsync

extension CreateWaterSourceUseCase {
    public func create(_ request: CreateWaterSourceRequest) async throws {
        try await asyncFunction(for: CreateWaterSourceUseCaseNativeKt.invoke(self, request: request))
    }
}

extension GetDefaultAddWaterSourceInfoUseCase {
    public func get() async throws -> DefaultAddWaterSourceInfo {
        try await asyncFunction(for: GetDefaultAddWaterSourceInfoUseCaseNativeKt.invoke(self))
    }
}

extension GetWaterSourceTypeUseCase {
    public func get() async throws -> [WaterSourceType] {
        try await asyncFunction(for: GetWaterSourceTypeUseCaseNativeKt.single(self))
    }
}

extension GetWaterSourceTypeUseCase {
    public var publisher: AnyPublisher<[WaterSourceType], any Error> {
        createPublisher(for: GetWaterSourceTypeUseCaseNativeKt.invoke(self))
    }
}

extension GetWaterSourceUseCase {
    public var publisher: AnyPublisher<[WaterSource], any Error> {
        createPublisher(for: GetWaterSourceUseCaseNativeKt.invoke(self))
    }
}

extension GetTodayWaterConsumptionSummaryUseCase {
    public var publisher: AnyPublisher<DailyWaterConsumptionSummary, any Error> {
        createPublisher(for: GetTodayWaterConsumptionSummaryUseCaseNativeKt.invoke(self))
    }
}

extension RegisterConsumedWaterUseCase {
    public func register(_ volume: MeasureSystemVolume, _ waterSourceType: WaterSourceType) async throws {
        try await asyncFunction(for: RegisterConsumedWaterUseCaseNativeKt.invoke(self, volume: volume, waterSourceType: waterSourceType))
    }

    public func register(_ volume: MeasureSystemVolume, _ waterSourceType: WaterSourceType, _ timestamp: Int64) async throws {
        try await asyncFunction(for: RegisterConsumedWaterUseCaseNativeKt.invoke(self, volume: volume, waterSourceType: waterSourceType, timestamp: timestamp))
    }
}

extension GetDefaultVolumeShortcutsUseCase {
    public func get() async throws -> DefaultVolumeShortcuts {
        try await asyncFunction(for: GetDefaultVolumeShortcutsUseCaseNativeKt.invoke(self))
    }
}

extension DeleteWaterSourceUseCase {
    public func delete(_ waterSourceId: Int64) async throws {
        try await asyncFunction(for: DeleteWaterSourceUseCaseNativeKt.invoke(self, waterSourceId: waterSourceId))
    }
}

extension DeleteWaterSourceTypeUseCase {
    public func delete(_ waterSourceTypeId: Int64) async throws {
        try await asyncFunction(for: DeleteWaterSourceTypeUseCaseNativeKt.invoke(self, waterSourceId: waterSourceTypeId))
    }
}

extension GetDailyWaterConsumptionSummaryUseCase {
    public var publisher: AnyPublisher<[DailyWaterConsumptionSummary], any Error> {
        createPublisher(for: GetDailyWaterConsumptionSummaryUseCaseNativeKt.invoke(self))
    }
}

extension DeleteConsumedWaterUseCase {
    public func delete(_ consumedWaterId: Int64) async throws {
        try await asyncFunction(for: DeleteConsumedWaterUseCaseNativeKt.invoke(self, consumedWaterId: consumedWaterId))
    }
}

extension GetHistoryChartDataUseCase {
    public func publisher(option: ChartOption_SV, date: Date) -> AnyPublisher<HistoryChartData, any Error> {
        createPublisher(for: GetHistoryChartDataUseCaseNativeKt.invoke(self, option: option.ktValue, baselineDate: date.ktLocalDate))
    }
}

extension GetDailyWaterConsumptionUseCase {
    public func publisher() -> AnyPublisher<DailyWaterConsumption?, any Error> {
        createPublisher(for: GetDailyWaterConsumptionUseCaseNativeKt.invoke(self))
    }
}

extension GetThemeUseCase {
    public func publisher() -> AnyPublisher<AppTheme, any Error> {
        createPublisher(for: GetThemeUseCaseNativeKt.invoke(self))
    }
}

extension GetVolumeUnitUseCase {
    public func execute() -> AnyPublisher<MeasureSystemVolumeUnit, any Error> {
        createPublisher(for: GetVolumeUnitUseCaseNativeKt.invoke(self))
    }

    public func execute() async throws -> MeasureSystemVolumeUnit {
        try await asyncFunction(for: GetVolumeUnitUseCaseNativeKt.single(self))
    }
}

extension GetWeightUnitUseCase {
    public func execute() -> AnyPublisher<MeasureSystemWeightUnit, any Error> {
        createPublisher(for: GetWeightUnitUseCaseNativeKt.invoke(self))
    }

    public func execute() async throws -> MeasureSystemWeightUnit {
        try await asyncFunction(for: GetWeightUnitUseCaseNativeKt.single(self))
    }
}

extension GetTemperatureUnitUseCase {
    public func execute() -> AnyPublisher<MeasureSystemTemperatureUnit, any Error> {
        createPublisher(for: GetTemperatureUnitUseCaseNativeKt.invoke(self))
    }

    public func execute() async throws -> MeasureSystemTemperatureUnit {
        try await asyncFunction(for: GetTemperatureUnitUseCaseNativeKt.single(self))
    }
}

extension SetThemeUseCase {
    public func set(_ data: AppTheme) async throws {
        try await asyncFunction(for: SetThemeUseCaseNativeKt.invoke(self, appTheme: data))
    }
}

extension SaveDailyWaterConsumptionUseCase {
    public func save(_ data: MeasureSystemVolume) async throws {
        try await asyncFunction(for: SaveDailyWaterConsumptionUseCaseNativeKt.invoke(self, volume: data))
    }
}

extension GetUserProfileUseCase {
    public func publisher() -> AnyPublisher<UserProfile, any Error> {
        createPublisher(for: GetUserProfileUseCaseNativeKt.invoke(self))
    }
}

extension GetCalculatedIntakeUseCase {
    public func publisher() -> AnyPublisher<MeasureSystemVolume, any Error> {
        createPublisher(for: GetCalculatedIntakeUseCaseNativeKt.invoke(self))
    }
}

extension SetUserProfileNameUseCase {
    public func set(_ data: String) async throws {
        try await asyncFunction(for: SetUserProfileNameUseCaseNativeKt.invoke(self, name: data))
    }
}

extension SetUserProfileWeightUseCase {
    public func set(_ data: MeasureSystemWeight) async throws {
        try await asyncFunction(for: SetUserProfileWeightUseCaseNativeKt.invoke(self, weight: data))
    }
}

extension SetUserProfileActivityLevelUseCase {
    public func set(_ data: Shared.ActivityLevel) async throws {
        try await asyncFunction(for: SetUserProfileActivityLevelUseCaseNativeKt.invoke(self, activityLevel: data))
    }
}

extension SetUserProfileTemperatureLevelUseCase {
    public func set(_ data: AmbienceTemperatureLevel) async throws {
        try await asyncFunction(for: SetUserProfileTemperatureLevelUseCaseNativeKt.invoke(self, temperatureLevel: data))
    }
}


extension IsNotificationsEnabledUseCase {
    public func publisher() -> AnyPublisher<Bool, any Error> {
        createPublisher(for: IsNotificationsEnabledUseCaseNativeKt.invoke(self)).map { $0 == KotlinBoolean(true) }.eraseToAnyPublisher()
    }
}

extension SetNotificationsEnabledUseCase {
    public func set(_ data: Bool) async throws {
        try await asyncFunction(for: SetNotificationsEnabledUseCaseNativeKt.invoke(self, isEnabled: data))
    }
}

extension GetScheduledNotificationsUseCase {
    public func publisher() -> AnyPublisher<[NotificationInfo], any Error> {
        createPublisher(for: GetScheduledNotificationsUseCaseNativeKt.invoke(self))
    }
}

extension DeleteScheduledNotificationUseCase {
    public func delete(_ request: DeleteScheduledNotificationRequest) async throws {
        try await asyncFunction(for: DeleteScheduledNotificationUseCaseNativeKt.invoke(self, request: request))
    }

}

extension SetWeekDayNotificationStateUseCase {
    public func set(_ dayTime: DayTime, _ weekState: WeekState) async throws {
        try await asyncFunction(for: SetWeekDayNotificationStateUseCaseNativeKt.invoke(self, dayTime: dayTime, weekState: weekState))
    }
}

extension CreateScheduleNotificationUseCase {
    public func create(_ dayTime: DayTime, _ weekState: WeekState) async throws {
        try await asyncFunction(for: CreateScheduleNotificationUseCaseNativeKt.invoke(self, dayTime: dayTime, weekState: weekState))
    }    

    public func create(_ period: NotificationsPeriod, _ weekState: WeekState) async throws {
        try await asyncFunction(for: CreateScheduleNotificationUseCaseNativeKt.invoke(self, period: period, weekState: weekState))
    }
}
 
public extension CreateWaterSourceTypeUseCase {
    func create(_ request: CreateWaterSourceTypeRequest) async throws {
        try await asyncFunction(for: CreateWaterSourceTypeUseCaseNativeKt.invoke(self, request: request))
    }
}

public extension IsFirstAccessCompletedUseCase {
    func execute() async throws -> Bool {
        try await asyncFunction(for: IsFirstAccessCompletedUseCaseNativeKt.single(self)).boolValue
    }

    func execute() -> AnyPublisher<Bool, any Error>  {
        createPublisher(for: IsFirstAccessCompletedUseCaseNativeKt.invoke(self)).map {
            $0.boolValue
        }.eraseToAnyPublisher()
    }
}

public extension CompleteFirstAccessFlowUseCase {
    func execute() async throws {
        try await asyncFunction(for: CompleteFirstAccessFlowUseCaseNativeKt.invoke(self))
    }
}

public extension GetFirstAccessNotificationDataUseCase {
    func execute() -> AnyPublisher<FirstAccessNotificationData, any Error> {
        createPublisher(for: GetFirstAccessNotificationDataUseCaseNativeKt.invoke(self))
    }
}

public extension IsDailyIntakeFirstSetUseCase {
    func execute() async throws -> Bool {
        try await asyncFunction(for: IsDailyIntakeFirstSetUseCaseNativeKt.invoke(self)).boolValue
    }
}

public extension SetFirstAccessNotificationStateUseCase {
    func execute(value: Bool) async throws {
        try await asyncFunction(for: SetFirstAccessNotificationStateUseCaseNativeKt.invoke(self, value: value))
    }
}

public extension SetFirstDailyIntakeUseCase {
    func execute() async throws {
        try await asyncFunction(for: SetFirstDailyIntakeUseCaseNativeKt.invoke(self))
    }
}

public extension SetNotificationFrequencyTimeUseCase {
    func execute(_ time: Kotlinx_datetimeLocalTime) async throws {
        try await asyncFunction(for: SetNotificationFrequencyTimeUseCaseNativeKt.invoke(self, time: time))
    }
}

public extension SetStartNotificationTimeUseCase {
    func execute(_ time: Kotlinx_datetimeLocalTime) async throws {
        try await asyncFunction(for: SetStartNotificationTimeUseCaseNativeKt.invoke(self, time: time))
    }
}

public extension SetStopNotificationTimeUseCase {
    func execute(_ time: Kotlinx_datetimeLocalTime) async throws {
        try await asyncFunction(for: SetStopNotificationTimeUseCaseNativeKt.invoke(self, time: time))
    }
}

public extension SetWeightUnitUseCase {
    func execute(_ unit: MeasureSystemWeightUnit) async throws {
        try await asyncFunction(for: SetWeightUnitUseCaseNativeKt.invoke(self, unit: unit))
    }
}

public extension SetTemperatureUnitUseCase {
    func execute(_ unit: MeasureSystemTemperatureUnit) async throws {
        try await asyncFunction(for: SetTemperatureUnitUseCaseNativeKt.invoke(self, unit: unit))
    }
}

public extension SetVolumeUnitUseCase {
    func execute(_ unit: MeasureSystemVolumeUnit) async throws {
        try await asyncFunction(for: SetVolumeUnitUseCaseNativeKt.invoke(self, unit: unit))
    }
}
