//
//  MainRouter.swift
//  iosApp
//
//  Created by Lucas Calheiros on 20/09/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI

final class MainRouter: ObservableObject {

    public enum Destination: Codable, Hashable {
        case firstAccessFlow
        case mainFlow
    }

    @Published var navPath = NavigationPath()

    func navigate(to destination: Destination) {
        navPath.append(destination)
    }

    func navigateBack() {
        navPath.removeLast()
    }

    func navigateToRoot() {
        navPath.removeLast(navPath.count)
    }
}
