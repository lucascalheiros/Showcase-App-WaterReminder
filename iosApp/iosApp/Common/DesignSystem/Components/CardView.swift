//
//  CardView.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

public struct CardView<Content: View>: View {
    @EnvironmentObject var theme: ThemeManager
    let content: Content

    public init(@ViewBuilder content: () -> Content) {
        self.content = content()
    }

    public var body: some View {
        ZStack(alignment: .center) {
            content
        }
        .frame(
            minWidth: 0,
            maxWidth: .infinity,
            minHeight: 0,
            maxHeight: .infinity,
            alignment: .center
        )
        .background(theme.current.surfaceColor)
        .cornerRadius(10)
    }
}
