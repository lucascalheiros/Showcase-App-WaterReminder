//
//  AddDrinkState.swift
//  Home
//
//  Created by Lucas Calheiros on 08/09/24.
//

import DesignSystem
import SwiftUI

struct AddDrinkState {
    var isDismissed = false
    var name: String = ""
    var hydration: Float = 1.0
    var color: ThemedColor = ThemedColor(lightColor: Color.blue, darkColor: Color.cyan)
    var showNameInputAlert: Bool = false
    var showSelectHydrationAlert: Bool = false
    var showSelectColorAlert: Bool = false
    var isConfirmDisabled: Bool {
        name.isEmpty
    }
}
