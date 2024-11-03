//
//  Color+colorInt.swift
//  iosApp
//
//  Created by Lucas Calheiros on 04/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI


public extension Color {
    init(colorInt: Int64) {
        if colorInt > 0xFFFFFF {
            let alpha = Double((colorInt >> 24) & 0xFF) / 255.0
            let red = Double((colorInt >> 16) & 0xFF) / 255.0
            let green = Double((colorInt >> 8) & 0xFF) / 255.0
            let blue = Double(colorInt & 0xFF) / 255.0
            self.init(red: red, green: green, blue: blue, opacity: alpha)
        } else {
            let red = Double((colorInt >> 16) & 0xFF) / 255.0
            let green = Double((colorInt >> 8) & 0xFF) / 255.0
            let blue = Double(colorInt & 0xFF) / 255.0
            self.init(red: red, green: green, blue: blue)
        }
    }

    init(colorInt: Int32) {
        self.init(colorInt: Int64(colorInt))
    }

    var components: (red: CGFloat, green: CGFloat, blue: CGFloat, alpha: CGFloat) {
        let uiColor = UIColor(self)

        var red: CGFloat = 0
        var green: CGFloat = 0
        var blue: CGFloat = 0
        var alpha: CGFloat = 0

        uiColor.getRed(&red, green: &green, blue: &blue, alpha: &alpha)
        return (red, green, blue, alpha)
    }

    var int32Value: Int32 {
        let (red, green, blue, alpha) = components
        let intRed = (red * 255.0).int32
        let intGreen = (green * 255.0).int32
        let intBlue = (blue * 255.0).int32
        let intAlpha = (alpha * 255.0).int32

        return (intAlpha << 24) | (intRed << 16) | (intGreen << 8) | intBlue
    }

    var intValue: Int {
        let (red, green, blue, alpha) = components
        let intRed = (red * 255.0).int
        let intGreen = (green * 255.0).int
        let intBlue = (blue * 255.0).int
        let intAlpha = (alpha * 255.0).int

        return (intAlpha << 24) | (intRed << 16) | (intGreen << 8) | intBlue
    }
}
