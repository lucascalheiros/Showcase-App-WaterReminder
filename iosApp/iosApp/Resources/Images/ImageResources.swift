//
//  ImageResources.swift
//  iosApp
//
//  Created by Lucas Calheiros on 01/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

enum ImageResources: CaseIterable {
    case dropIcon
    case settingIcon
    case barChartIcon
    case deleteIcon

    func image() -> Image {
        switch self {

        case .dropIcon:
            Image(systemName: "drop.fill")

        case .settingIcon:
            Image(systemName: "gearshape.fill")

        case .barChartIcon:
            Image(systemName: "chart.bar.fill")

        case .deleteIcon:
            Image(systemName: "trash")

        }
    }

}
