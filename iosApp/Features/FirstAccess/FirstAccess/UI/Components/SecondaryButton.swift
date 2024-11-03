//
//  SecondaryButton.swift
//  FirstAccess
//
//  Created by Lucas Calheiros on 01/10/24.
//

import SwiftUI
import DesignSystem

struct SecondaryButton: ButtonStyle {
    @EnvironmentObject var theme: ThemeManager

    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .padding(EdgeInsets(top: 8, leading: 20, bottom: 8, trailing: 20))
            .background(theme.current.secondaryColor)
            .foregroundStyle(theme.current.onSecondaryColor)
            .clipShape(RoundedRectangle(cornerRadius: 20))
            .font(theme.current.bodySmall)
            .bold()
    }
}
