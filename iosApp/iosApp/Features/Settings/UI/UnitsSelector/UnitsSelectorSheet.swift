//
//  UnitsSelectorSheet.swift
//  iosApp
//
//  Created by Lucas Calheiros on 04/09/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct UnitsSelectorSheet: View {

    @EnvironmentObject var theme: ThemeManager

    @StateObject var viewModel: UnitsSelectorViewModel = .init()

    var body: some View {
        VStack(alignment: .center, spacing: 0) {
            Text(.unitsTitle)
                .font(theme.current.titleSmall)
            VStack(alignment: .leading, spacing: 0) {
                SettingSectionHeaderTitle(SettingsSR.unitsVolume.text)
                SettingGroupContainer {
                    volumeUnitOptionView(MeasureSystemVolumeUnit.ml)
                    SettingItemDivider()
                    volumeUnitOptionView(MeasureSystemVolumeUnit.ozUk)
                    SettingItemDivider()
                    volumeUnitOptionView(MeasureSystemVolumeUnit.ozUs)
                }
                SettingSectionHeaderTitle(SettingsSR.unitsWeight.text)
                SettingGroupContainer {
                    weightUnitOptionView(MeasureSystemWeightUnit.kilograms)
                    SettingItemDivider()
                    weightUnitOptionView(MeasureSystemWeightUnit.pounds)
                }
                SettingSectionHeaderTitle(SettingsSR.unitsTemperature.text)
                SettingGroupContainer {
                    temperatureUnitOptionView(MeasureSystemTemperatureUnit.celsius)
                    SettingItemDivider()
                    temperatureUnitOptionView(MeasureSystemTemperatureUnit.fahrenheit)
                }
            }
            Spacer()
        }
        .padding(16)
        .background(theme.current.backgroundColor)
        .onAppear {
            viewModel.send(.loadData)
        }
    }

    func unitOptionView(
        _ label: String,
        _ isChecked: Bool,
        _ onChange: @escaping (Bool) -> Void) -> some View {
            SettingItemContainer {
                Toggle("", isOn: isChecked.bindingWith(onChange))
                    .toggleStyle(CheckCircleButtonToggleStyle())
                Text(label)
                Spacer()
            }
        }

    func volumeUnitOptionView(_ unit: MeasureSystemVolumeUnit) -> some View {
        unitOptionView(
            unit.shortUnitNamed,
            unit == viewModel.state.volumeUnit
        ) {
            if $0 {
                viewModel.send(.setVolumeUnit(unit))
            }
        }
    }

    func weightUnitOptionView(_ unit: MeasureSystemWeightUnit) -> some View {
        unitOptionView(
            unit.shortUnitFormatted,
            unit == viewModel.state.weightUnit
        ) {
            if $0 {
                viewModel.send(.setWeightUnit(unit))
            }
        }
    }

    func temperatureUnitOptionView(_ unit: MeasureSystemTemperatureUnit) -> some View {
        unitOptionView(
            unit.shortUnitFormatted,
            unit == viewModel.state.temperatureUnit
        ) {
            if $0 {
                viewModel.send(.setTemperatureUnit(unit))
            }
        }
    }
}


struct UnitsSelectorSheet_Preview: PreviewProvider {
    static var previews: some View {
        UnitsSelectorSheet()
            .environmentObject(ThemeManager())
    }
}
