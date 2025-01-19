//
//  ProfileSection.swift
//  iosApp
//
//  Created by Lucas Calheiros on 27/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared
import SwiftUI

struct ProfileSection: View {
    @EnvironmentObject var theme: ThemeManager
    var state: ProfileSectionState
    var sendIntent: (SettingIntent) -> Void
    @State var weightAlertInput: Double?
    @State var showWeightAlert: Bool = false
    @State var showCalculatedIntakeConfirmation: Bool = false

    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            SettingSectionHeaderTitle(
                SettingsSR.profileHeaderTitle.text
            )
            SettingGroupContainer {
                SettingItemContainer(
                    SettingsSR.weightOption.text,
                    state.weight.shortValueAndUnitFormatted
                ).onTapGesture {
                    weightAlertInput = state.weight.intrinsicValue()
                    showWeightAlert = true
                }
                SettingItemDivider()
                SettingItemContainer(
                    SettingsSR.activityLevelOption.text
                ) {
                    Menu {
                        ForEach(ActivityLevel.allCases) { value in
                            Button {
                                sendIntent(.setActivityLevel(value))
                            } label: {
                                Text(value.title)
                            }
                        }
                    } label: {
                        Text(state.activityLevel.title)
                            .bold()
                            .foregroundStyle(theme.current.primaryColor)
                    }
                }
                SettingItemDivider()
                SettingItemContainer(
                    SettingsSR.temperatureLevelOption.text
                ) {
                    Menu {
                        ForEach(TemperatureLevel.allCases) { value in
                            Button {
                                sendIntent(.setTemperatureLevel(value))
                            } label: {
                                Text(value.title)
                            }
                        }
                    } label: {
                        Text(state.temperatureLevel.title)
                            .bold()
                            .foregroundStyle(theme.current.primaryColor)
                    }
                }
                SettingItemDivider()
                SettingItemContainer(
                    SettingsSR.calculatedIntakeOption.text,
                    state.calculatedIntake.shortValueAndUnitFormatted
                ).onTapGesture {
                    showCalculatedIntakeConfirmation = true
                }
            }
        }
        .alert(String(localized: "Weight Input"), isPresented: $showWeightAlert, presenting: weightAlertInput) { _ in
            TextField("", value: $weightAlertInput, format: .number)
                .keyboardType(.decimalPad)
            Button("Cancel", action: {
                weightAlertInput = nil
                showWeightAlert = false
            })
            Button("Confirm", action: {
                if let weightAlertInput = weightAlertInput {
                    sendIntent(.setWeight(weightAlertInput))
                }
                showWeightAlert = false
            }).disabled(weightAlertInput == nil)
        }
        .alert(String(localized: "Do you want to set the calculated intake as your daily intake?"), isPresented: $showCalculatedIntakeConfirmation) { 
            Button("Cancel", action: {
                showCalculatedIntakeConfirmation = false
            })
            Button("Confirm", action: {
                sendIntent(.setDailyToCalculateIntake)
                showCalculatedIntakeConfirmation = false
            })
        }
    }
}

struct ProfileSection_Preview: PreviewProvider {

    static let themeManager = ThemeManager()

    static var previews: some View {
        ProfileSection(state: ProfileSectionState(weight: MeasureSystemWeightCompanion().create(intrinsicValue: 50, measureSystemUnit_: MeasureSystemWeightUnit.kilograms), activityLevel: .heavy, temperatureLevel: .cold, calculatedIntake: MeasureSystemVolumeCompanion().create(intrinsicValue: 2000, measureSystemUnit_: MeasureSystemVolumeUnit.ml))
        ) { _ in }
            .environmentObject(themeManager)
    }
}
