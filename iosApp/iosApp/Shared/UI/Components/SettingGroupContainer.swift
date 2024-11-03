//
//  SettingGroupContainer.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct SettingGroupContainer<Content: View>: View {
    @EnvironmentObject var theme: ThemeManager
    @ViewBuilder var content: Content

    var body: some View {
        VStack(spacing: 0) {
            content
        }
        .background(theme.selectedTheme.surfaceColor)
        .cornerRadius(12)
        .padding(16)
    }
}
