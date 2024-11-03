//
//  ThemeProtocol.swift
//  iosApp
//
//  Created by Lucas Calheiros on 04/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

public protocol ThemeProtocol {
    var titleLarge: Font { get }
    var titleMedium: Font { get }
    var titleSmall: Font { get }
    var bodyLarge: Font { get }
    var bodyMedium: Font { get }
    var bodySmall: Font { get }
    var caption: Font { get }

    var primaryColor: Color { get }
    var onPrimaryColor: Color { get }
    var secondaryColor: Color { get }
    var onSecondaryColor: Color { get }
    var backgroundColor: Color { get }
    var onBackgroundColor: Color { get }
    var surfaceHighColor: Color { get }
    var surfaceColor: Color { get }
    var onSurfaceColor: Color { get }
    var dialogBackground: Color { get }
    var onDialogBackground: Color { get }
    var dialogActions: Color { get }
}
