//
//  WeightOptions.swift
//  FirstAccess
//
//  Created by Lucas Calheiros on 02/10/24.
//

import Shared

enum WeightOptions {
    case kg
    case lbs

    var formattedUnit: String {
        switch self {

        case .kg:
            FirstAccessSR.weightKg.text
        case .lbs:
            FirstAccessSR.weightLbs.text
        }
    }

    var unit: MeasureSystemWeightUnit {
        switch self {

        case .kg:
            MeasureSystemWeightUnit.kilograms
        case .lbs:
            MeasureSystemWeightUnit.pounds
        }
    }

    static func from(_ unit: MeasureSystemWeightUnit) -> WeightOptions {
        switch unit {
            
        case .kilograms:
            WeightOptions.kg
        default:
            WeightOptions.lbs
        }
    }
}

