//
//  WeekDayPopoverData.swift
//  iosApp
//
//  Created by Lucas Calheiros on 29/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

struct WeekDayPopoverData {
    let type: WeekDayPopoverType
    var weekDayState: WeekDayState
    var dayTimeInMinutes: Int32 {
        switch type {

        case .single(let day):
            day.dayMinutes
        case .selected:
            -1
        }
    }
}
