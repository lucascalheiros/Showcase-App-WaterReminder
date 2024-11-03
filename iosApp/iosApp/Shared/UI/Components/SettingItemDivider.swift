//
//  SettingItemDivider.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct SettingItemDivider: View {
    @EnvironmentObject var theme: ThemeManager

    var body: some View {
        Divider()
            .background(theme.selectedTheme.onBackgroundColor)
    }
}
