//
//  CardView.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct CardView<Content: View>: View {
    @EnvironmentObject var theme: ThemeManager
    @ViewBuilder let content: Content?

    var body: some View {
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
        .background(theme.selectedTheme.surfaceColor)
        .cornerRadius(10)
    }
}
