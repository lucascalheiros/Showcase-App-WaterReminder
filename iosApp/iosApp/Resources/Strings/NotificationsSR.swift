//
//  NotificationsSR.swift
//  iosApp
//
//  Created by Lucas Calheiros on 27/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

enum NotificationsSR: String, StringResourcesProtocol {
    
    case notificationTitle
    case notificationDescription

    var key: String {
        rawValue
    }

    var table: String {
        "Notifications"
    }

}
