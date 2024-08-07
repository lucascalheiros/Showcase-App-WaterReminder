//
//  Color+colorInt.swift
//  iosApp
//
//  Created by Lucas Calheiros on 04/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI


extension Color {
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
}
