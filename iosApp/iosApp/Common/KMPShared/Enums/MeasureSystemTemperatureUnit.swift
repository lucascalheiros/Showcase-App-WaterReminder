//
//  MeasureSystemTemperatureUnit.swift
//  iosApp
//
//  Created by Lucas Calheiros on 10/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared


extension MeasureSystemTemperatureUnit {
    var swiftEnum: MeasureSystemTemperatureUnit_SV {
        MeasureSystemTemperatureUnit_SV(self)!
    }
}

enum MeasureSystemTemperatureUnit_SV {
    case celsius
    case fahrenheit

    init?(_ value: MeasureSystemTemperatureUnit) {
        switch value {
        case MeasureSystemTemperatureUnit.celsius:
            self = .celsius
        case MeasureSystemTemperatureUnit.fahrenheit:
            self = .fahrenheit
        default:
            return nil
        }
    }
}
