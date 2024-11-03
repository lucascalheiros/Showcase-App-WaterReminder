//
//  ChartOption.swift
//  iosApp
//
//  Created by Lucas Calheiros on 10/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared

extension ChartOption {
    public var swiftValue: ChartOption_SV {
        let value: ChartOption_SV? = switch self {

        case ChartOption.week:
                .week
        case ChartOption.month:
                .month
        case ChartOption.year:
                .year
        default:
            nil
        }
        return value!
    }
}

public enum ChartOption_SV {
    case week
    case month
    case year

    public var ktValue: ChartOption {
        switch self {

        case .week:
            ChartOption.week
        case .month:
            ChartOption.month
        case .year:
            ChartOption.year
        }
    }
}
