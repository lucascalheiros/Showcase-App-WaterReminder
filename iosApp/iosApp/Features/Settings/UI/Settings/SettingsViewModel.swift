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

class SettingsViewModel: ObservableObject {

    private var cancellableBag = Set<AnyCancellable>()
    let getDailyWaterConsumptionUseCase: GetDailyWaterConsumptionUseCase
    let getThemeUseCase: GetThemeUseCase
    let getVolumeUnitUseCase: GetVolumeUnitUseCase
    let getWeightUnitUseCase: GetWeightUnitUseCase
    let getTemperatureUnitUseCase: GetTemperatureUnitUseCase
    let setThemeUseCase: SetThemeUseCase
    let saveDailyWaterConsumptionUseCase: SaveDailyWaterConsumptionUseCase
    let getUserProfileUseCase: GetUserProfileUseCase
    let getCalculatedIntakeUseCase: GetCalculatedIntakeUseCase
    let setUserProfileNameUseCase: SetUserProfileNameUseCase
    let setUserProfileWeightUseCase: SetUserProfileWeightUseCase
    let setUserProfileActivityLevelUseCase: SetUserProfileActivityLevelUseCase
    let setUserProfileTemperatureLevelUseCase: SetUserProfileTemperatureLevelUseCase
    let isNotificationsEnabledUseCase: IsNotificationsEnabledUseCase
    let setNotificationsEnabledUseCase: SetNotificationsEnabledUseCase
    @Published var state: SettingState? = nil

    init(
        getDailyWaterConsumptionUseCase: GetDailyWaterConsumptionUseCase,
        getThemeUseCase: GetThemeUseCase,
        getVolumeUnitUseCase: GetVolumeUnitUseCase,
        getWeightUnitUseCase: GetWeightUnitUseCase,
        getTemperatureUnitUseCase: GetTemperatureUnitUseCase,
        setThemeUseCase: SetThemeUseCase,
        saveDailyWaterConsumptionUseCase: SaveDailyWaterConsumptionUseCase,
        getUserProfileUseCase: GetUserProfileUseCase,
        getCalculatedIntakeUseCase: GetCalculatedIntakeUseCase,
        setUserProfileNameUseCase: SetUserProfileNameUseCase,
        setUserProfileWeightUseCase: SetUserProfileWeightUseCase,
        setUserProfileActivityLevelUseCase: SetUserProfileActivityLevelUseCase,
        setUserProfileTemperatureLevelUseCase: SetUserProfileTemperatureLevelUseCase,
        isNotificationsEnabledUseCase: IsNotificationsEnabledUseCase,
        setNotificationsEnabledUseCase: SetNotificationsEnabledUseCase
    ) {
        self.getDailyWaterConsumptionUseCase = getDailyWaterConsumptionUseCase
        self.getThemeUseCase = getThemeUseCase
        self.getVolumeUnitUseCase = getVolumeUnitUseCase
        self.getWeightUnitUseCase = getWeightUnitUseCase
        self.getTemperatureUnitUseCase = getTemperatureUnitUseCase
        self.setThemeUseCase = setThemeUseCase
        self.saveDailyWaterConsumptionUseCase = saveDailyWaterConsumptionUseCase
        self.getUserProfileUseCase = getUserProfileUseCase
        self.getCalculatedIntakeUseCase = getCalculatedIntakeUseCase
        self.setUserProfileNameUseCase = setUserProfileNameUseCase
        self.setUserProfileWeightUseCase = setUserProfileWeightUseCase
        self.setUserProfileActivityLevelUseCase = setUserProfileActivityLevelUseCase
        self.setUserProfileTemperatureLevelUseCase = setUserProfileTemperatureLevelUseCase
        self.isNotificationsEnabledUseCase = isNotificationsEnabledUseCase
        self.setNotificationsEnabledUseCase = setNotificationsEnabledUseCase
        observeState()
    }

    private func observeState() {
        let measureUnits = getVolumeUnitUseCase.execute().combineLatest(
            getWeightUnitUseCase.execute(),
            getTemperatureUnitUseCase.execute(),
            MeasureUnits.init
        ).eraseToAnyPublisher()

        let generalSection = getDailyWaterConsumptionUseCase.publisher().combineLatest(
            measureUnits,
            getThemeUseCase.publisher(),
            GeneralSectionState.init
        )

        let remindNotificationsSection = isNotificationsEnabledUseCase.publisher().map(RemindNotificationsSectionState.init)
        
        let profileSection = getUserProfileUseCase.publisher().combineLatest(
            getCalculatedIntakeUseCase.publisher(),
            ProfileSectionState.init
        )

        generalSection.combineLatest(
            remindNotificationsSection,
            profileSection,
            SettingState.init
        )
        .receive(on: RunLoop.main)
        .sink(receiveCompletion: { _ in }, receiveValue: { [weak self] in
            self?.state = $0
        })
        .store(in: &cancellableBag)
    }

    func send(_ intent: SettingIntent) {
        Task { @MainActor in
            switch intent {

            case .setTheme(let theme):
                try await setThemeUseCase.set(theme)
            case .setDailyIntakeVolume(let volume):
                guard let unit = state?.generalSectionState?.measureUnits.volumeUnit else {
                    return
                }
                try await saveDailyWaterConsumptionUseCase.save(MeasureSystemVolumeCompanion().create(intrinsicValue: volume, measureSystemUnit_: unit))
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
}

extension SettingsViewModel {
    convenience init() {
        let injector = SharedInjector()
        self.init(
            getDailyWaterConsumptionUseCase: injector.getDailyWaterConsumptionUseCase(),
            getThemeUseCase: injector.getThemeUseCase(),
            getVolumeUnitUseCase: injector.getVolumeUnitUseCase(),
            getWeightUnitUseCase: injector.getWeightUnitUseCase(),
            getTemperatureUnitUseCase: injector.getTemperatureUnitUseCase(),
            setThemeUseCase: injector.setThemeUseCase(),
            saveDailyWaterConsumptionUseCase: injector.saveDailyWaterConsumptionUseCase(),
            getUserProfileUseCase: injector.getUserProfileUseCase(),
            getCalculatedIntakeUseCase: injector.getCalculatedIntakeUseCase(),
            setUserProfileNameUseCase: injector.setUserProfileNameUseCase(),
            setUserProfileWeightUseCase: injector.setUserProfileWeightUseCase(),
            setUserProfileActivityLevelUseCase: injector.setUserProfileActivityLevelUseCase(),
            setUserProfileTemperatureLevelUseCase: injector.setUserProfileTemperatureLevelUseCase(),
            isNotificationsEnabledUseCase: injector.isNotificationsEnabledUseCase(),
            setNotificationsEnabledUseCase: injector.setNotificationsEnabledUseCase()
        )
    }
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
    var userProfile: UserProfile
    var calculatedIntake: MeasureSystemVolume
}
