//
//  AppTheme+ColorScheme+UIStyle.swift
//  iosApp
//
//  Created by Lucas Calheiros on 21/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared
import KMPShared

extension AppTheme_SV {
    var colorScheme: ColorScheme? {
        switch self {

        case .dark:
                .dark
        case .light:
                .light
        default:
                .none
        }
    }

    var uiStyle: UIUserInterfaceStyle {
        switch self {

        case .dark:
                .dark
        case .light:
                .light
        default:
                .unspecified
        }
    }
}
