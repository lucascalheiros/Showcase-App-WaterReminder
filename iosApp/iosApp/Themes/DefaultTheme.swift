//
//  DefaultTheme.swift
//  iosApp
//
//  Created by Lucas Calheiros on 04/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct DefaultTheme: ThemeProtocol {
    var titleLarge: Font { Font.system(size: 48) }
    var titleMedium: Font { Font.system(size: 34) }
    var titleSmall: Font { Font.system(size: 24) }

    var primaryColor: Color { Color("DefaultTheme/background") }
    var onPrimaryColor: Color { Color("DefaultTheme/background") }
    var secondaryColor: Color { Color("DefaultTheme/background") }
    var onSecondaryColor: Color { Color("DefaultTheme/background") }
    var backgroundColor: Color { Color("DefaultTheme/background") }
    var onBackgroundColor: Color { Color("DefaultTheme/onBackground") }
    var surfaceColor: Color { Color("DefaultTheme/surface") }
    var onSurfaceColor: Color { Color("DefaultTheme/onSurface") }
}
