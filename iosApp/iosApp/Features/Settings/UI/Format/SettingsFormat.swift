//
//  SettingsFormat.swift
//  iosApp
//
//  Created by Lucas Calheiros on 25/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared

extension WeekDayState {
    var formatWeekDaysDisplayText: String {
        var weekDaysList = [WeekDay]()
        if sunday {
            weekDaysList.append(WeekDay.sunday)
        }
        if monday {
            weekDaysList.append(WeekDay.monday)
        }
        if tuesday {
            weekDaysList.append(WeekDay.tuesday)
        }
        if wednesday {
            weekDaysList.append(WeekDay.wednesday)
        }
        if thursday {
            weekDaysList.append(WeekDay.thursday)
        }
        if friday {
            weekDaysList.append(WeekDay.friday)
        }
        if saturday {
            weekDaysList.append(WeekDay.saturday)
        }
        return weekDaysList.formatWeekDaysDisplayText
    }
}

extension Array where Element == WeekDay {
    var formatWeekDaysDisplayText: String {
        let text: String? = switch count {
        case 7: SettingsSR.everyDay.text
        case 5: contains([WeekDay.monday, WeekDay.tuesday, WeekDay.wednesday, WeekDay.thursday, WeekDay.friday]) ?
            SettingsSR.mondayToFriday.text : nil
        case 2: contains([WeekDay.sunday, WeekDay.saturday]) ?
            SettingsSR.weekend.text : nil
        case 0: SettingsSR.noNotification.text

        default:
            nil
        }
        return text ?? self.map { $0.formatShortNumbered }.joined(separator: ", ")
    }
}

extension DayTime {
    var formattedTimeShort: String {
        var components = DateComponents()
        components.hour = Int(hour)
        components.minute = Int(minute)
        let formatter = DateFormatter()
        formatter.dateStyle = .none
        formatter.timeStyle = .short
        let date = Calendar.current.date(from: components)!
        return formatter.string(from: date)
    }
}

extension WeekDay {
    var formatShortNumbered: String {
        switch self {
        case WeekDay.sunday: SettingsSR.sundayShort.text
        case WeekDay.monday: SettingsSR.mondayShort.text
        case WeekDay.tuesday: SettingsSR.tuesdayShort.text
        case WeekDay.wednesday: SettingsSR.wednesdayShort.text
        case WeekDay.thursday: SettingsSR.thursdayShort.text
        case WeekDay.friday: SettingsSR.fridayShort.text
        case WeekDay.saturday: SettingsSR.saturdayShort.text
        default:
            ""
        }
    }
}

extension Date {
    var dayTime: DayTime {
        DayTime(hour: Int32(hour), minute: Int32(minute))
    }

    var minute: Int {
        Calendar.current.component(.minute, from: self)
    }

    var hour: Int {
        Calendar.current.component(.hour, from: self)
    }
}

extension NotificationInfo {
    var formattedTimeShort: String {
        dayTime.formattedTimeShort
    }

    var formatWeekDaysDisplayText: String {
        weekState.enabledWeekDays().formatWeekDaysDisplayText
    }
}

extension WeekState {
    var weekDayState: WeekDayState {
        WeekDayState(sunday: sundayEnabled, monday: mondayEnabled, tuesday: tuesdayEnabled, wednesday: wednesdayEnabled, thursday: thursdayEnabled, friday: fridayEnabled, saturday: saturdayEnabled)
    }
}

extension WeekDayState {
    var weekState: WeekState {
        WeekState(sundayEnabled: sunday, mondayEnabled: monday, tuesdayEnabled: tuesday, wednesdayEnabled: wednesday, thursdayEnabled: thursday, fridayEnabled: friday, saturdayEnabled: saturday)
    }
}
