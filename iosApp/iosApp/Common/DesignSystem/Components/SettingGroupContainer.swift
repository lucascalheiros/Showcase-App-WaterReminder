//
//  SettingGroupContainer.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

public struct SettingGroupContainer<Content: View>: View {
    @EnvironmentObject var theme: ThemeManager
    @ViewBuilder var content: Content

    public init(@ViewBuilder content: () -> Content) {
        self.content = content()
    }

    public var body: some View {
        VStack(spacing: 0) {
            content
        }
        .font(theme.current.bodyMedium)
        .foregroundStyle(theme.current.onSurfaceColor)
        .background(theme.current.surfaceColor)
        .cornerRadius(12)
    }
}
