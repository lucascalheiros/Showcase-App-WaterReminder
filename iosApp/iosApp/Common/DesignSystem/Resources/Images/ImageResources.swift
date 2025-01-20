//
//  ImageResources.swift
//  iosApp
//
//  Created by Lucas Calheiros on 01/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

public enum ImageResources: CaseIterable {
    case dropIcon
    case settingIcon
    case barChartIcon
    case deleteIcon
    case lightMode
    case darkMode
    case autoMode
    case arrowRight
    case arrowLeft
    case notificationAdd
    case verticalMore
    case checkMarkCircle
    case circle
    case calendar
    case checkMarkSquare
    case cancelCircle
    case square
    case addCircle

    public func image() -> Image {
        switch self {

        case .dropIcon:
            Image(systemName: "drop.fill")

        case .settingIcon:
            Image(systemName: "gearshape.fill")

        case .barChartIcon:
            Image(systemName: "chart.bar.fill")

        case .deleteIcon:
            Image(systemName: "trash")

        case .lightMode:
            Image("light_mode")

        case .darkMode:
            Image("dark_mode")

        case .autoMode:
            Image("auto_mode")

        case .arrowRight:
            Image(systemName: "chevron.right")

        case .arrowLeft:
            Image(systemName: "chevron.left")

        case .notificationAdd:
            Image(systemName: "plus")
            
        case .verticalMore:
            Image(systemName: "ellipsis")
       
        case .checkMarkCircle:
            Image(systemName: "checkmark.circle.fill")

        case .circle:
            Image(systemName: "circle")

        case .calendar:
            Image(systemName: "calendar")

        case .checkMarkSquare:
            Image(systemName: "checkmark.square")

        case .cancelCircle:
            Image(systemName: "xmark.circle") 

        case .square:
            Image(systemName: "square")

        case .addCircle:
            Image(systemName: "plus.circle")
        }
    }

}
