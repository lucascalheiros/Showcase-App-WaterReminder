//
//  SettingsViewModel.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared
import Combine
import Factory

class SettingsViewModel: ObservableObject {

    private var cancellableBag = Set<AnyCancellable>()

    @Injected(\.getDailyWaterConsumptionUseCase)
    private var getDailyWaterConsumptionUseCase

    @Injected(\.getThemeUseCase)
    private var getThemeUseCase

    @Injected(\.getVolumeUnitUseCase)
    private var getVolumeUnitUseCase

    @Injected(\.getWeightUnitUseCase)
    private var getWeightUnitUseCase

    @Injected(\.getTemperatureUnitUseCase)
    private var getTemperatureUnitUseCase

    @Injected(\.setThemeUseCase)
    private var setThemeUseCase

    @Injected(\.saveDailyWaterConsumptionUseCase)
    private var saveDailyWaterConsumptionUseCase

    @Injected(\.getUserProfileUseCase)
    private var getUserProfileUseCase

    @Injected(\.getCalculatedIntakeUseCase)
    private var getCalculatedIntakeUseCase

    @Injected(\.setUserProfileNameUseCase)
    private var setUserProfileNameUseCase

    @Injected(\.setUserProfileWeightUseCase)
    private var setUserProfileWeightUseCase

    @Injected(\.setUserProfileActivityLevelUseCase)
    private var setUserProfileActivityLevelUseCase

    @Injected(\.setUserProfileTemperatureLevelUseCase)
    private var setUserProfileTemperatureLevelUseCase

    @Injected(\.isNotificationsEnabledUseCase)
    private var isNotificationsEnabledUseCase

    @Injected(\.setNotificationsEnabledUseCase)
    private var setNotificationsEnabledUseCase

    @Published var state: SettingState = SettingState()

    init() {
        observeState()
    }

    private func observeState() {
        let measureUnits = getVolumeUnitUseCase.execute().combineLatest(
            getWeightUnitUseCase.execute(),
            getTemperatureUnitUseCase.execute(),
            MeasureUnits.init
        ).eraseToAnyPublisher()

        getDailyWaterConsumptionUseCase.publisher().combineLatest(
            measureUnits,
            getThemeUseCase.publisher(),
            GeneralSectionState.init
        )
        .receive(on: RunLoop.main)
        .sink(receiveCompletion: { _ in }, receiveValue: { [weak self] in
            self?.state.generalSectionState = $0
        })
        .store(in: &cancellableBag)

        isNotificationsEnabledUseCase.publisher()
            .map(RemindNotificationsSectionState.init)
            .receive(on: RunLoop.main)
            .sink(receiveCompletion: { _ in }, receiveValue: { [weak self] in
                self?.state.remindNotificationsSectionState = $0
            })
            .store(in: &cancellableBag)

        getUserProfileUseCase.publisher().combineLatest(
            getCalculatedIntakeUseCase.publisher()
        ) { (profile, intake) in
            ProfileSectionState.init(
                weight: profile.weight,
                activityLevel: ActivityLevel.from(profile.activityLevel),
                temperatureLevel: TemperatureLevel.from(profile.temperatureLevel),
                calculatedIntake: intake
            )
        }
        .receive(on: RunLoop.main)
        .sink(receiveCompletion: { _ in }, receiveValue: { [weak self] in
            self?.state.profileSectionState = $0
        })
        .store(in: &cancellableBag)
    }

    func send(_ intent: SettingIntent) {
        Task { @MainActor in
            switch intent {

            case .setTheme(let theme):
                try await setThemeUseCase.set(theme)
            case .setDailyIntakeVolume(let volume):
                guard let unit = state.generalSectionState?.measureUnits.volumeUnit else {
                    return
                }
                try await saveDailyWaterConsumptionUseCase.save(MeasureSystemVolumeCompanion().create(intrinsicValue: volume, measureSystemUnit_: unit))
            case .setWeight(let weight):
                if let unit = state.profileSectionState?.weight.weightUnit() {
                    try await setUserProfileWeightUseCase.set(MeasureSystemWeightCompanion().create(intrinsicValue: weight, measureSystemUnit_: unit))
                }
            case .setDailyToCalculateIntake:
                if let value = state.profileSectionState?.calculatedIntake {
                    try await saveDailyWaterConsumptionUseCase.save(value)
                }
            case .setActivityLevel(let value):
                try await setUserProfileActivityLevelUseCase.set(value.activityLevel)

            case .setTemperatureLevel(let value):
                try await setUserProfileTemperatureLevelUseCase.set(value.level)

            }
        }
    }
}

struct SettingState {
    var generalSectionState: GeneralSectionState?
    var remindNotificationsSectionState: RemindNotificationsSectionState?
    var profileSectionState: ProfileSectionState?
}

enum SettingIntent {
    case setTheme(AppTheme)
    case setDailyIntakeVolume(Double)
    case setWeight(Double)
    case setDailyToCalculateIntake
    case setActivityLevel(ActivityLevel)
    case setTemperatureLevel(TemperatureLevel)
}

struct MeasureUnits {
    let volumeUnit: MeasureSystemVolumeUnit
    let weightUnit: MeasureSystemWeightUnit
    let temperatureUnit: MeasureSystemTemperatureUnit
}

struct GeneralSectionState {
    var dailyIntake: DailyWaterConsumption?
    var measureUnits: MeasureUnits
    var appTheme: AppTheme
}

struct RemindNotificationsSectionState {
    var isNotificationEnabled: Bool
}

struct ProfileSectionState {
    var weight: MeasureSystemWeight
    var activityLevel: ActivityLevel
    var temperatureLevel: TemperatureLevel
    var calculatedIntake: MeasureSystemVolume
}
