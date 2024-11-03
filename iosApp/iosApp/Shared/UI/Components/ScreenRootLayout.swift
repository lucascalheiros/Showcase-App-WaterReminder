//
//  ScreenRootLayout.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ScreenRootLayout<Content: View>: View {
    @EnvironmentObject var theme: ThemeManager
    @ViewBuilder var content: Content

    var body: some View {
        ZStack {
            theme.selectedTheme.backgroundColor.edgesIgnoringSafeArea(.all)
            VStack(spacing: 0) {
                content
            }.background(theme.selectedTheme.backgroundColor)
        }
    }
}

