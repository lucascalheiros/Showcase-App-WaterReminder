//
//  MeasureSystemVolume+Format.swift
//  iosApp
//
//  Created by Lucas Calheiros on 04/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared
import Foundation

extension MeasureSystemVolume {
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

extension MeasureSystemVolumeUnit {
    var shortUnitFormatted: String {
        switch swiftEnum {
        case .ml:
            String(localized: "ml_short", table: "MeasureSystem")

        case .oz_uk, .oz_us:
            String(localized: "oz_short", table: "MeasureSystem")

        }
    }

    var swiftEnum: MeasureSystemVolumeUnit_SV {
        MeasureSystemVolumeUnit_SV(self)!
    }

}

extension MeasureSystemVolumeUnit_SV {
    init?(_ value: MeasureSystemVolumeUnit) {
        switch value {
        case MeasureSystemVolumeUnit.ml:
            self = .ml
        case MeasureSystemVolumeUnit.ozUs:
            self = .oz_us
        case MeasureSystemVolumeUnit.ozUk:
            self = .oz_uk
        default:
            return nil
        }
    }
}

enum MeasureSystemVolumeUnit_SV {
    case ml
    case oz_us
    case oz_uk
}

