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
    func create(_ request: CreateWaterSourceRequest) async throws {
        try await asyncFunction(for: CreateWaterSourceUseCaseNativeKt.invoke(self, request: request))
    }
}

extension GetDefaultAddWaterSourceInfoUseCase {
    func get() async throws -> DefaultAddWaterSourceInfo {
        try await asyncFunction(for: GetDefaultAddWaterSourceInfoUseCaseNativeKt.invoke(self))
    }
}

extension GetWaterSourceTypeUseCase {
    func get() async throws -> [WaterSourceType] {
        try await asyncFunction(for: GetWaterSourceTypeUseCaseNativeKt.single(self))
    }
}

extension GetWaterSourceTypeUseCase {
    var publisher: AnyPublisher<[WaterSourceType], any Error> {
        createPublisher(for: GetWaterSourceTypeUseCaseNativeKt.invoke(self))
    }
}

extension GetWaterSourceUseCase {
    var publisher: AnyPublisher<[WaterSource], any Error> {
        createPublisher(for: GetWaterSourceUseCaseNativeKt.invoke(self))
    }
}

extension GetTodayWaterConsumptionSummaryUseCase {
    var publisher: AnyPublisher<DailyWaterConsumptionSummary, any Error> {
        createPublisher(for: GetTodayWaterConsumptionSummaryUseCaseNativeKt.invoke(self))
    }
}

extension RegisterConsumedWaterUseCase {
    func register(_ volume: MeasureSystemVolume, _ waterSourceType: WaterSourceType) async throws {
        try await asyncFunction(for: RegisterConsumedWaterUseCaseNativeKt.invoke(self, volume: volume, waterSourceType: waterSourceType))
    }
}

extension GetDefaultVolumeShortcutsUseCase {
    func get() async throws -> DefaultVolumeShortcuts {
        try await asyncFunction(for: GetDefaultVolumeShortcutsUseCaseNativeKt.invoke(self))
    }
}

extension DeleteWaterSourceUseCase {
    func delete(_ waterSourceId: Int64) async throws {
        try await asyncFunction(for: DeleteWaterSourceUseCaseNativeKt.invoke(self, waterSourceId: waterSourceId))
    }
}

extension DeleteWaterSourceTypeUseCase {
    func delete(_ waterSourceTypeId: Int64) async throws {
        try await asyncFunction(for: DeleteWaterSourceTypeUseCaseNativeKt.invoke(self, waterSourceId: waterSourceTypeId))
    }
}

extension GetDailyWaterConsumptionSummaryUseCase {
    var publisher: AnyPublisher<[DailyWaterConsumptionSummary], any Error> {
        createPublisher(for: GetDailyWaterConsumptionSummaryUseCaseNativeKt.invoke(self))
    }
}

extension DeleteConsumedWaterUseCase {
    func delete(_ consumedWaterId: Int64) async throws {
        try await asyncFunction(for: DeleteConsumedWaterUseCaseNativeKt.invoke(self, consumedWaterId: consumedWaterId))
    }
}
