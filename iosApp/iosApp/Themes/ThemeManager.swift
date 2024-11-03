//
//  ThemeManager.swift
//  iosApp
//
//  Created by Lucas Calheiros on 04/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

class ThemeManager: ObservableObject {
    @Published var selectedTheme: ThemeProtocol = DefaultTheme()

    func setTheme(_ theme: ThemeProtocol) {
        selectedTheme = theme
    }
}
