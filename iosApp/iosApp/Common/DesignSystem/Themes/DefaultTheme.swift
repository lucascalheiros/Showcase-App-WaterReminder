//
//  DefaultTheme.swift
//  iosApp
//
//  Created by Lucas Calheiros on 04/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

public struct DefaultTheme: ThemeProtocol {
    public var titleLarge: Font { Font.system(size: 48) }
    public var titleMedium: Font { Font.system(size: 34) }
    public var titleSmall: Font { Font.system(size: 24) }
    public var bodyLarge: Font { Font.system(size: 20) }
    public var bodyMedium: Font { Font.system(size: 18) }
    public var bodySmall: Font { Font.system(size: 16) }
    public var caption: Font { Font.system(size: 12) }

    public var primaryColor: Color { Color("DefaultTheme/primary") }
    public var onPrimaryColor: Color { Color("DefaultTheme/onPrimary") }
    public var secondaryColor: Color { Color("DefaultTheme/secondary") }
    public var onSecondaryColor: Color { Color("DefaultTheme/onSecondary") }
    public var backgroundColor: Color { Color("DefaultTheme/background") }
    public var onBackgroundColor: Color { Color("DefaultTheme/onBackground") }
    public var surfaceHighColor: Color { Color("DefaultTheme/dialogBackground") }
    public var surfaceColor: Color { Color("DefaultTheme/surface") }
    public var onSurfaceColor: Color { Color("DefaultTheme/onSurface") }
    public var dialogBackground: Color { Color("DefaultTheme/dialogBackground") }
    public var onDialogBackground: Color { Color("DefaultTheme/onDialogBackground") }
    public var dialogActions: Color { Color("DefaultTheme/dialogActions") }

    public init() {
        
    }
}
