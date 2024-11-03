//
//  CupCard.swift
//  Home
//
//  Created by Lucas Calheiros on 08/09/24.
//

import Shared

enum CupCard: Identifiable {
    case item(WaterSource)
    case addItem

    var id: Int64 {
        switch self {

        case .item(let data):
            data.waterSourceId
        case .addItem:
            -1
        }
    }
}
