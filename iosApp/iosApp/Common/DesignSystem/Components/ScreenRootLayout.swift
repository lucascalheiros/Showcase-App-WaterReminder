//
//  ScreenRootLayout.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

public struct ScreenRootLayout<Content: View>: View {
    @EnvironmentObject var theme: ThemeManager
    var content: Content

    public init(@ViewBuilder content: () -> Content) {
        self.content = content()
    }

    public var body: some View {
        ZStack {
            theme.current.backgroundColor.edgesIgnoringSafeArea(.all)
            NavigationStack {
                VStack(spacing: 0) {
                    content
                }.background(theme.current.backgroundColor)
            }           
            .tint(theme.current.onBackgroundColor)
            .foregroundStyle(theme.current.onBackgroundColor)
        }
    }
}

