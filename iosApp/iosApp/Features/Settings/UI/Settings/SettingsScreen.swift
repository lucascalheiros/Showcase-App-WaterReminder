//
//  SettingsScreen.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

public struct SettingsScreen: View {
    @EnvironmentObject var theme: ThemeManager
    @StateObject var settingsViewModel = SettingsViewModel()
  
    public init() {
    }

    public var body: some View {
        ScreenRootLayout {
            ScrollView {
                VStack(alignment: .leading, spacing: 0) {
                    if let state = settingsViewModel.state?.generalSectionState {
                        GeneralSection(state: state, sendIntent: settingsViewModel.send)
                    }

                    if let state = settingsViewModel.state?.remindNotificationsSectionState {
                        RemindNotificationsSection(state: state, sendIntent: settingsViewModel.send)
                    }
                    
                    ProfileSection()

                }
                .padding(.horizontal, 16)
            }
            .navigationTitle(SettingsSR.settingsTitle.text)
        }
    }
    
}

struct SettingsScreen_Preview: PreviewProvider {

    static let themeManager = ThemeManager()

    static var previews: some View {
        SettingsScreen()
            .environmentObject(themeManager)
    }
}
