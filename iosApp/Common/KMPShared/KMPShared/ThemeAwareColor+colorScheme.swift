//
//  ThemeAwareColor+colorScheme.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared
import SwiftUI
import DesignSystem

public extension ThemeAwareColor {
    func color(_ colorScheme: ColorScheme) -> Color {
        let colorInt = switch colorScheme {

        case .light:
            onLightColor
        case .dark:
            onDarkColor
        @unknown default:
            onLightColor
        }
        return Color(colorInt: colorInt)
    }
}
