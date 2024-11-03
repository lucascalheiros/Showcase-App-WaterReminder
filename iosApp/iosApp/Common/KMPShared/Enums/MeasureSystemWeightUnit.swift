//
//  MeasureSystemWeightUnit.swift
//  iosApp
//
//  Created by Lucas Calheiros on 10/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared


extension MeasureSystemWeightUnit {
    var swiftEnum: MeasureSystemWeightUnit_SV {
        MeasureSystemWeightUnit_SV(self)!
    }
}

enum MeasureSystemWeightUnit_SV {
    case kilograms
    case grams
    case pounds

    init?(_ value: MeasureSystemWeightUnit) {
        switch value {
        case MeasureSystemWeightUnit.kilograms:
            self = .kilograms
        case MeasureSystemWeightUnit.grams:
            self = .grams
        case MeasureSystemWeightUnit.pounds:
            self = .pounds
        default:
            return nil
        }
    }
}
