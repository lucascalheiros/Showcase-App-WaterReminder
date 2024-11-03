//
//  ProfileSection.swift
//  iosApp
//
//  Created by Lucas Calheiros on 27/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import DesignSystem

struct ProfileSection: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            SettingSectionHeaderTitle(SettingsSR.profileHeaderTitle.text)
            SettingGroupContainer {
                SettingItemContainer(SettingsSR.weightOption.text)
                SettingItemDivider()
                SettingItemContainer(SettingsSR.activityLevelOption.text)
                SettingItemDivider()
                SettingItemContainer(SettingsSR.temperatureLevelOption.text)
                SettingItemDivider()
                SettingItemContainer(SettingsSR.calculatedIntakeOption.text)
            }
        }
    }
}

struct ProfileSection_Preview: PreviewProvider {

    static let themeManager = ThemeManager()

    static var previews: some View {
        ProfileSection()
            .environmentObject(themeManager)
    }
}
