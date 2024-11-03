//
//  NotificationInfo.swift
//  iosApp
//
//  Created by Lucas Calheiros on 18/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared

extension NotificationInfo: Identifiable {
    public var id: Int32 {
        return dayTime.dayMinutes
    }
}
