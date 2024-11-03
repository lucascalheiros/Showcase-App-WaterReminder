//
//  GeneralSection.swift
//  iosApp
//
//  Created by Lucas Calheiros on 27/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared
import DesignSystem
import KMPShared

struct GeneralSection: View {
    @EnvironmentObject var theme: ThemeManager
    @State var dailyIntakeVolumeInput: Double? = nil
    @State var showDailyIntakeAlert: Bool = false
    var state: GeneralSectionState
    var sendIntent: (SettingIntent) -> Void

    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            SettingSectionHeaderTitle(SettingsSR.generalHeaderTitle.text)
            SettingGroupContainer {
                SettingItemContainer(
                    SettingsSR.dailyWaterIntakeOption.text,
                    state.dailyIntake?.expectedVolume.shortValueAndUnitFormatted
                )
                .volumeInputAlert(
                    showAlert: $showDailyIntakeAlert,
                    volumeInput: $dailyIntakeVolumeInput,
                    onCancel: { showDailyIntakeAlert = false },
                    onConfirm: { sendIntent(.setDailyIntakeVolume($0)) }
                )
                .onTapGesture {
                    dailyIntakeVolumeInput = state.dailyIntake?.expectedVolume.intrinsicValue() ?? 0.0
                    showDailyIntakeAlert = true
                }

                SettingItemDivider()
                SettingItemContainer(
                    SettingsSR.unitsOption.text,
                    state.measureUnits.formatted
                )
                SettingItemDivider()
                SettingItemContainer(SettingsSR.themeOption.text) {
                    Menu {
                        ForEach(AppTheme_SV.allCases) { theme in
                            Button {
                                sendIntent(.setTheme(theme.ktValue))
                            } label: {
                                Text(theme.formatted)
                                theme.icon
                            }
                        }
                    } label: {
                        Text(state.appTheme.swiftEnum.formatted)
                            .bold()
                            .foregroundStyle(theme.current.primaryColor)
                    }
                }
            }
        }

    }
}

private extension MeasureUnits {
    var formatted: String {
        return "\(volumeUnit.shortUnitFormatted), \(temperatureUnit.shortUnitFormatted), \(weightUnit.shortUnitFormatted)"
    }
}

private extension AppTheme_SV {
    var formatted: String {
        switch self {

        case .dark:
            SettingsSR.themeOptionDark.text

        case .light:
            SettingsSR.themeOptionLight.text

        case .auto:
            SettingsSR.themeOptionAuto.text
        }
    }

    var icon: Image {
        switch self {

        case .dark:
            ImageResources.darkMode.image()

        case .light:
            ImageResources.lightMode.image()

        case .auto:
            ImageResources.autoMode.image()
        }
    }

    var ktValue: AppTheme {
        switch self {

        case .dark:
            AppTheme.dark

        case .light:
            AppTheme.light

        case .auto:
            AppTheme.auto_
        }
    }
}


struct GeneralSection_Preview: PreviewProvider {

    static let themeManager = ThemeManager()

    static var previews: some View {
        GeneralSection(state: GeneralSectionState(
            dailyIntake: DailyWaterConsumption(
            dailyWaterConsumptionId: 1,
            expectedVolume: MeasureSystemVolumeCompanion().create(intrinsicValue: 2000.0, measureSystemUnit: MeasureSystemUnit.uk),
            date: 0),
            measureUnits: MeasureUnits(volumeUnit: MeasureSystemVolumeUnit.ml, weightUnit: MeasureSystemWeightUnit.grams, temperatureUnit: MeasureSystemTemperatureUnit.celsius),
            appTheme: AppTheme.dark
        ), sendIntent: { _ in })
            .environmentObject(themeManager)
    }
}
