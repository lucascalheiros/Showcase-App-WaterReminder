//
//  AddNotificationResult.swift
//  iosApp
//
//  Created by Lucas Calheiros on 29/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

enum AddNotificationResult {
    case single(SingleNotification)
    case multiple(MultipleNotifications)
}