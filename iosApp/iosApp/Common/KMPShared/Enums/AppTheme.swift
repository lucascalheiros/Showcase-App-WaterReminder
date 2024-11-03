//
//  AppTheme.swift
//  iosApp
//
//  Created by Lucas Calheiros on 10/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared

extension AppTheme {
    public var swiftEnum: AppTheme_SV {
        AppTheme_SV(self)!
    }

    public var themeMode: ThemeMode {
        switch self {
        case AppTheme.dark:
            .dark
        case AppTheme.light:
            .light
        default:
            .auto
        }
    }
}

public enum AppTheme_SV: String, CaseIterable, Identifiable {
    public var id: String {
        rawValue
    }

    case dark
    case light
    case auto

    public init?(_ value: AppTheme) {
        switch value {
        case AppTheme.dark:
            self = .dark
        case AppTheme.light:
            self = .light
        default:
            self = .auto
        }
    }
}
