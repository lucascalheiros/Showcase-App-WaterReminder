//
//  FirstAccessSR.swift
//  FirstAccess
//
//  Created by Lucas Calheiros on 11/09/24.
//

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

}


extension Text {
    init(_ resource: FirstAccessSR) {
        self.init(resource.text)
    }
}
