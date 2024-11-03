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

    public var primaryColor: Color { Color("DefaultTheme/primary", bundle: bundle) }
    public var onPrimaryColor: Color { Color("DefaultTheme/onPrimary", bundle: bundle) }
    public var secondaryColor: Color { Color("DefaultTheme/secondary", bundle: bundle) }
    public var onSecondaryColor: Color { Color("DefaultTheme/onSecondary", bundle: bundle) }
    public var backgroundColor: Color { Color("DefaultTheme/background", bundle: bundle) }
    public var onBackgroundColor: Color { Color("DefaultTheme/onBackground", bundle: bundle) }
    public var surfaceHighColor: Color { Color("DefaultTheme/dialogBackground", bundle: bundle) }
    public var surfaceColor: Color { Color("DefaultTheme/surface", bundle: bundle) }
    public var onSurfaceColor: Color { Color("DefaultTheme/onSurface", bundle: bundle) }
    public var dialogBackground: Color { Color("DefaultTheme/dialogBackground", bundle: bundle) }
    public var onDialogBackground: Color { Color("DefaultTheme/onDialogBackground", bundle: bundle) }
    public var dialogActions: Color { Color("DefaultTheme/dialogActions", bundle: bundle) }

    public init() {
        
    }
}
