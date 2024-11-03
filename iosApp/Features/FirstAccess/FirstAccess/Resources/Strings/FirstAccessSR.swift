//
//  FirstAccessSR.swift
//  FirstAccess
//
//  Created by Lucas Calheiros on 11/09/24.
//

import Core
import SwiftUI

enum FirstAccessSR: String, StringResourcesProtocol {
    
    case welcomeTitle
    case weightInputTitle
    case activityLevelInputTitle
    case temperatureLevelInputTitle
    case notificationInputTitle
    case confirmationTitle
    case next
    case back
    case confirm
    case weightKg
    case weightLbs

    var key: String {
        rawValue
    }

    var table: String {
        "FirstAccess"
    }

    var bundle: Bundle {
        Bundle(for: BundleClass.self)
    }

}

private class BundleClass {}

extension Text {
    init(_ resource: FirstAccessSR) {
        self.init(resource.text)
    }
}
