//
//  ThemeProtocol.swift
//  iosApp
//
//  Created by Lucas Calheiros on 04/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

protocol ThemeProtocol {
    var titleLarge: Font { get }
    var titleMedium: Font { get }
    var titleSmall: Font { get }

    var primaryColor: Color { get }
    var onPrimaryColor: Color { get }
    var secondaryColor: Color { get }
    var onSecondaryColor: Color { get }
    var backgroundColor: Color { get }
    var onBackgroundColor: Color { get }
    var surfaceColor: Color { get }
    var onSurfaceColor: Color { get }
}
