//
//  Date+Extensions.swift
//  KMPShared
//
//  Created by Lucas Calheiros on 07/09/24.
//

import Shared
import Util

public extension Kotlinx_datetimeLocalDate {
    func toDateSw() -> Date {
        Calendar.current.date(from: DateComponents(year: Int(year), month: Int(monthNumber), day: Int(dayOfMonth)))!.startOfDay()
    }
}



public extension Date {
    var ktLocalDate: Kotlinx_datetimeLocalDate {
        let components = Calendar.current.dateComponents([.year, .month, .day], from: self)
        return Kotlinx_datetimeLocalDate(year: Int32(components.year!), monthNumber: Int32(components.month!), dayOfMonth: Int32(components.day!))
    }
}
