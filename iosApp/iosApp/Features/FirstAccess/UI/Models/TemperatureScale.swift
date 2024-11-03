//
//  TemperatureScale.swift
//  iosApp
//
//  Created by Lucas Calheiros on 02/11/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared

enum TemperatureScale {
    case celsius
    case fahrenheit

    static func from(_ unit: MeasureSystemTemperatureUnit) -> TemperatureScale {
        switch unit {
        case .celsius: return .celsius
        case .fahrenheit: return .fahrenheit
        default: return .celsius
        }
    }

    var unit: MeasureSystemTemperatureUnit {
        switch self {
        case .celsius: return .celsius
        case .fahrenheit: return .fahrenheit
        }
    }
}
