//
//  StyledText.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct StyledText: View {
    @EnvironmentObject var theme: ThemeManager
    let text: String

    init(_ text: String, _ style: TextStyle = .titleSmall) {
        self.text = text
    }

    var body: some View {
        Text(text)
            .font(theme.selectedTheme.titleSmall)
            .foregroundStyle(theme.selectedTheme.onBackgroundColor)
    }
}

enum TextStyle {
    case titleLarge
    case titleMedium
    case titleSmall
}
