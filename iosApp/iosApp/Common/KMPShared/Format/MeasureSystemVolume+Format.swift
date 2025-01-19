//
//  MeasureSystemVolume+Format.swift
//  iosApp
//
//  Created by Lucas Calheiros on 04/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared
import Foundation

public extension MeasureSystemVolume {
    var shortUnitFormatted: String {
        volumeUnit().shortUnitFormatted
    }

    var shortValueFormatted: String {
        let value = intrinsicValue()

        return switch volumeUnit().swiftEnum {
        case .ml:
            String(format: NSLocalizedString("ml_short_value", tableName: "MeasureSystem", comment: ""), value)

        case .oz_uk, .oz_us:
            if modf(value).1 == 0.0 {
                String(format: NSLocalizedString("oz_short_value_integer", tableName: "MeasureSystem", comment: ""), value)
            } else {
                String(format: NSLocalizedString("oz_short_value", tableName: "MeasureSystem", comment: ""), value)
            }
        }
    }

    var shortValueAndUnitFormatted: String {
        return switch volumeUnit().swiftEnum {
        case .ml:
            String(localized: "ml_short_value_and_unit\(shortValueFormatted)", table: "MeasureSystem")

        case .oz_uk, .oz_us:
            String(localized: "oz_short_value_and_unit\(shortValueFormatted)", table: "MeasureSystem")

        }
    }
}

public extension MeasureSystemVolumeUnit {
    var shortUnitFormatted: String {
        switch swiftEnum {
        case .ml:
            String(localized: "ml_short", table: "MeasureSystem")

        case .oz_uk, .oz_us:
            String(localized: "oz_short", table: "MeasureSystem")

        }
    }

    var shortUnitNamed: String {
        switch swiftEnum {
        case .ml:
            String(localized: "ml_short", table: "MeasureSystem")

        case .oz_uk:
            String(localized: "oz_uk_short_named", table: "MeasureSystem")

        case .oz_us:
            String(localized: "oz_us_short_named", table: "MeasureSystem")

        }
    }
}

public extension MeasureSystemTemperatureUnit {
    var shortUnitFormatted: String {
        switch swiftEnum {

        case .celsius:
            String(localized: "celsius_short", table: "MeasureSystem")

        case .fahrenheit:
            String(localized: "fahrenheit_short", table: "MeasureSystem")

        }
    }
}

public extension MeasureSystemWeightUnit {
    var shortUnitFormatted: String {
        switch swiftEnum {

        case .kilograms:
            String(localized: "kg_short", table: "MeasureSystem")

        case .grams:
            String(localized: "g_short", table: "MeasureSystem")

        case .pounds:
            String(localized: "lbs_short", table: "MeasureSystem")

        }
    }
}

public extension MeasureSystemWeight {
    var shortUnitFormatted: String {
        weightUnit().shortUnitFormatted
    }

    var shortValueAndUnitFormatted: String {
        String(format: NSLocalizedString("weight_short_value_and_format", tableName: "MeasureSystem", comment: ""), intrinsicValue(), shortUnitFormatted)
    }
}
