//
//  LocalDate+Extensions.swift
//  KMPShared
//
//  Created by Lucas Calheiros on 07/09/24.
//

import Shared

public extension Kotlinx_datetimeLocalDate {
    func toDateSw() -> Date {
        Calendar.current.date(from: DateComponents(year: Int(year), month: Int(monthNumber), day: Int(dayOfMonth)))!.startOfDay()
    }
}

public extension Kotlinx_datetimeLocalTime {
    func toDateSw() -> Date {
        Calendar.current.date(bySettingHour: Int(hour), minute: Int(minute), second: Int(second), of: Date())!
    }
}


public extension Date {
    var ktLocalDate: Kotlinx_datetimeLocalDate {
        let components = Calendar.current.dateComponents([.year, .month, .day], from: self)
        return Kotlinx_datetimeLocalDate(
            year: Int32(components.year!),
            monthNumber: Int32(components.month!),
            dayOfMonth: Int32(components.day!)
        )
    }

    var ktLocalTime: Kotlinx_datetimeLocalTime {
        let components = Calendar.current.dateComponents([.hour, .minute, .second], from: self)
        return Kotlinx_datetimeLocalTime(
            hour: Int32(components.hour!),
            minute: Int32(components.minute!),
            second: Int32(components.second!),
            nanosecond: Int32(0)
        )
    }
}
