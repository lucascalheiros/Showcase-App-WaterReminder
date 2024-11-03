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

    var body: some View {
        VStack(alignment: .center, spacing: 0) {
            Text(.unitsTitle)
                .font(theme.current.titleSmall)
            VStack(alignment: .leading, spacing: 0) {
                SettingSectionHeaderTitle(SettingsSR.unitsVolume.text)
                SettingGroupContainer {
                    SettingItemContainer {
                        Toggle("", isOn: true.bindingWithDismiss {

                        })
                        .toggleStyle(CheckCircleButtonToggleStyle())
                        Text(MeasureSystemVolumeUnit.ml.shortUnitFormatted)
                        Spacer()
                    }
                    SettingItemDivider()
                    SettingItemContainer {
                        Toggle("", isOn: true.bindingWithDismiss {

                        })
                        .toggleStyle(CheckCircleButtonToggleStyle())
                        Text(MeasureSystemVolumeUnit.ozUk.shortUnitNamed)
                        Spacer()
                    }
                    SettingItemDivider()
                    SettingItemContainer {
                        Toggle("", isOn: true.bindingWithDismiss {

                        })
                        .toggleStyle(CheckCircleButtonToggleStyle())
                        Text(MeasureSystemVolumeUnit.ozUs.shortUnitNamed)
                        Spacer()
                    }
                }
                SettingSectionHeaderTitle(SettingsSR.unitsWeight.text)
                SettingGroupContainer {
                    SettingItemContainer {
                        Toggle("", isOn: true.bindingWithDismiss {

                        })
                        .toggleStyle(CheckCircleButtonToggleStyle())
                        Text(MeasureSystemWeightUnit.kilograms.shortUnitFormatted)
                        Spacer()
                    }
                    SettingItemDivider()
                    SettingItemContainer {
                        Toggle("", isOn: true.bindingWithDismiss {

                        })
                        .toggleStyle(CheckCircleButtonToggleStyle())
                        Text(MeasureSystemWeightUnit.pounds.shortUnitFormatted)
                        Spacer()
                    }
                }
                SettingSectionHeaderTitle(SettingsSR.unitsTemperature.text)
                SettingGroupContainer {
                    SettingItemContainer {
                        Toggle("", isOn: true.bindingWithDismiss {

                        })
                        .toggleStyle(CheckCircleButtonToggleStyle())
                        Text(MeasureSystemTemperatureUnit.celsius.shortUnitFormatted)
                        Spacer()
                    }
                    SettingItemDivider()
                    SettingItemContainer {
                        Toggle("", isOn: true.bindingWithDismiss {

                        })
                        .toggleStyle(CheckCircleButtonToggleStyle())
                        Text(MeasureSystemTemperatureUnit.fahrenheit.shortUnitFormatted)
                        Spacer()
                    }
                }
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
