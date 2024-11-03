//
//  HomeSR.swift
//  iosApp
//
//  Created by Lucas Calheiros on 27/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Core

enum HomeSR: String, StringResourcesProtocol {

    case alertClose
    case alertCancel
    case alertConfirm
    case alertSelectColorTitle
    case alertSelectColorOnLight
    case alertSelectColorOnDark
    case alertSelectDrinkTitle
    case addDrinkTitle
    case addDrinkNameOption
    case addDrinkHydrationOption
    case addDrinkColorOption
    case addCupTitle
    case addCupVolumeOption
    case addCupDrinkOption
    case cupDelete
    case drinkDelete
    case drinkShortcutConfirm
    case cupCardAdd
    case drinkCardAdd
    case selectHydrationAlertTitle
    case nameInputAlertTitle
    case homeTitle

    var key: String {
        rawValue
    }

    var table: String {
        "Home"
    }

    var bundle: Bundle {
        Bundle(for: BundleClass.self)
    }
}

private class BundleClass {

}

extension Text {
    init(_ resource: HomeSR) {
        self.init(resource.text)
    }
}
