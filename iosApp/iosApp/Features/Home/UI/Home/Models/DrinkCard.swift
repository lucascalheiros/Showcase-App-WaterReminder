//
//  DrinkCard.swift
//  Home
//
//  Created by Lucas Calheiros on 08/09/24.
//

import Shared

enum DrinkCard: Identifiable {
    case item(WaterSourceType)
    case addItem

    var id: Int64 {
        switch self {

        case .item(let data):
            data.waterSourceTypeId
        case .addItem:
            -1

        }
    }
}
