//
//  AppStorageKey.swift
//  iosApp
//
//  Created by Lucas Calheiros on 18/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

enum AppStorageKey: String {
    case appThemeMode
}

extension AppStorageKey {
    func set(_ value: Any?) {
        UserDefaults.standard.set(value, forKey: rawValue)
    }
}
