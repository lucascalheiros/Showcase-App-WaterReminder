//
//  ThemeManager.swift
//  iosApp
//
//  Created by Lucas Calheiros on 04/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Combine

public class ThemeManager: ObservableObject {
    @Published public var current: ThemeProtocol = DefaultTheme()
    @Published public var mode: ThemeMode = .auto
    var cancellableBag = Set<AnyCancellable>()

    public init() {}

    public func setTheme(_ theme: ThemeProtocol) {
        current = theme
    }

    public func setThemeMode(_ mode: ThemeMode?) {
        self.mode = mode ?? .auto
    }
}

public enum ThemeMode {
    case dark
    case light
    case auto

    public var scheme: ColorScheme? {
        switch self {

        case .dark:
                .dark
        case .light:
                .light
        default:
                .none
        }
    }

    public var style: UIUserInterfaceStyle {
        switch self {

        case .dark:
                .dark
        case .light:
                .light
        default:
                .unspecified
        }
    }
}
