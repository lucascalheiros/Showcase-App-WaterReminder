//
//  Date+Extensions.swift
//  iosApp
//
//  Created by Lucas Calheiros on 10/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

extension Date {
    public func daysInWeekOf(_ anyDateFromWeek: Date) -> [Date] {
        let (start, end) = getWeekStartAndEnd()
        return daysBetween(start, end)
    }

    public func getWeekStartAndEnd() -> (start: Date, end: Date) {
        let calendar = Calendar.current
        let components = calendar.dateComponents([.yearForWeekOfYear, .weekOfYear], from: self)
        let weekStart = calendar.date(from: components)!
        let weekEnd = calendar.date(byAdding: .day, value: 6, to: weekStart)!
        return (start: weekStart, end: weekEnd)
    }

    public func isBetween(_ date1: Date, and date2: Date) -> Bool {
        return (min(date1, date2) ... max(date1, date2)).contains(self)
    }

    public func numberOfDaysBetween(_ from: Date, _ to: Date) -> Int {
        let fromDate = from.startOfDay()
        let toDate = to.startOfDay()
        let numberOfDays = Calendar.current.dateComponents([.day], from: fromDate, to: toDate)
        return numberOfDays.day!
    }

    public func daysBetween(_ from: Date, _ to: Date) -> [Date] {
        let calendar = Calendar.current
        return (0...numberOfDaysBetween(from, to)).map {
            calendar.date(byAdding: .day, value: $0, to: from)!
        }
    }

    public func startOfDay() -> Date {
        let calendar = Calendar.current
        return calendar.startOfDay(for: self)
    }
}
