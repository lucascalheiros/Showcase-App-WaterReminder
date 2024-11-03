//
//  MultipleNotifications.swift
//  iosApp
//
//  Created by Lucas Calheiros on 29/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

struct MultipleNotifications {
    var startTime: Date = Calendar.current.date(from: DateComponents(hour: 8))!
    var stopTime: Date = Calendar.current.date(from: DateComponents(hour: 20))!
    var notifyEach: Date = Calendar.current.date(from: DateComponents(hour: 2))!
    var notificationDays: WeekDayState = WeekDayState.everyDay()
    var deleteOther: Bool = false
}
