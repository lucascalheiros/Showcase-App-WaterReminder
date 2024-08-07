//
//  WaterSourceType+colorScheme.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

extension WaterSourceType {
    func color(_ colorScheme: ColorScheme) -> Color {
        let colorInt = switch colorScheme {

        case .light:
            lightColor
        case .dark:
            darkColor
        @unknown default:
            lightColor
        }
        return Color(colorInt: colorInt)
    }
}
