//
//  SingleNotification.swift
//  iosApp
//
//  Created by Lucas Calheiros on 29/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

struct SingleNotification {
    var notificationTime: Date = Date()
    var notificationDays: WeekDayState = WeekDayState.everyDay()
}
