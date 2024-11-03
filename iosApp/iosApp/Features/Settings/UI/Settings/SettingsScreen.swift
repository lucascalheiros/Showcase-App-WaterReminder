//
//  SettingsScreen.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct SettingsScreen: View {
    var body: some View {
        ScreenRootLayout {
            HStack {
                StyledText("Settings")
                Spacer()
            }.padding(.horizontal, 16)
            Spacer()
        }
    }
}
