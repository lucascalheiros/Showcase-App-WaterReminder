//
//  TemperatureLevel.swift
//  iosApp
//
//  Created by Lucas Calheiros on 02/11/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared

enum TemperatureLevel: CaseIterable, Identifiable {
    case cold
    case moderate
    case warm
    case hot

    var id: String {
        title
    }

    var title: String {
        switch self {

        case .cold:
            "Cold"
        case .moderate:
            "Moderate"
        case .warm:
            "Warm"
        case .hot:
            "Hot"
        }
    }

    func description(_ scale: TemperatureScale) -> String {
        let unit = switch scale {
        case .celsius:
            MeasureSystemTemperatureUnit.celsius
        case .fahrenheit:
            MeasureSystemTemperatureUnit.fahrenheit
        }

        return switch self {

        case .cold:
            String(
                format: "Less than %.0f°",
                20.0.inCelsius().toUnit(unit: unit).intrinsicValue()
            )
        case .moderate:
            String(
                format: "%.0f to %.0f°",
                20.0.inCelsius().toUnit(unit: unit).intrinsicValue(),
                25.0.inCelsius().toUnit(unit: unit).intrinsicValue()
            )
        case .warm:
            String(
                format: "%.0f to %.0f°",
                26.0.inCelsius().toUnit(unit: unit).intrinsicValue(),
                30.0.inCelsius().toUnit(unit: unit).intrinsicValue()
            )
        case .hot:
            String(
                format: "More than %.0f°",
                30.0.inCelsius().toUnit(unit: unit).intrinsicValue()
            )
        }
    }

    static func from(_ level: AmbienceTemperatureLevel) -> TemperatureLevel {
        switch level {
        case .cold:
            return .cold
        case .moderate:
            return .moderate
        case .warm:
            return .warm
        case .hot:
            return .hot
        default:
            return .moderate
        }
    }

    var level: AmbienceTemperatureLevel {
        switch self {
        case .cold:
            return .cold
        case .moderate:
            return .moderate
        case .warm:
            return .warm
        case .hot:
            return .hot
        }
    }
}
