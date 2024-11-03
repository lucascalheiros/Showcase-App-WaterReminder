//
//  NotificationManager.swift
//  iosApp
//
//  Created by Lucas Calheiros on 25/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared
import UserNotifications
import SwiftUI
import Combine
import Core

struct NotificationState {
    let enabled: Bool
    let infoList: [NotificationInfo]
}

class NotificationManager {

    private let center = UNUserNotificationCenter.current()

    var notificationState: AnyPublisher<NotificationState?, Never> {
        let injector = SharedInjector()
        return injector.getScheduledNotificationsUseCase().publisher().combineLatest(injector.isNotificationsEnabledUseCase().publisher()) {
            NotificationState(enabled: $1, infoList: $0)
        }
        .catch { _ in Just(nil) }
        .receive(on: RunLoop.main)
        .eraseToAnyPublisher()
    }

    func updateNotificationsState(state: NotificationState?) {
        if let state {
            let manager = NotificationManager()
            if state.enabled {
                Task {
                    await manager.updateScheduled(from: state.infoList)
                }
            } else {
                manager.cancelAllNotifications()
            }
        }
    }

    private func updateScheduled(from notificationInfos: [NotificationInfo]) async {
        if notificationInfos.isEmpty {
            cancelAllNotifications()
        }

        let requests = await center.pendingNotificationRequests()
        let requestsDict = Dictionary(grouping: requests, by: { $0.identifier })

        let info = notificationInfos.notificationDayTimeAndWeekDay
        let infoDict = Dictionary(grouping: info, by: { $0.identifier })

        let toAdd = info.filter { requestsDict[$0.identifier] == nil }
        let toRemove = requests.filter { infoDict[$0.identifier] == nil }.map { $0.identifier }

        center.removePendingNotificationRequests(withIdentifiers: toRemove)
        if !toRemove.isEmpty {
            Logger.verbose("Notifications removed: \(toRemove)")
        }
        await scheduleNotifications(from: toAdd)

    }

    private func cancelAllNotifications() {
        center.removeAllPendingNotificationRequests()
    }

    private func scheduleNotifications(from notifications: [NotificationDayTimeAndWeekDay]) async {
        for notification in notifications {
            await scheduleNotification(notification)
        }
    }

    private func scheduleNotification(_ dayTimeAndWeekDay: NotificationDayTimeAndWeekDay) async {

        let dayTime = dayTimeAndWeekDay.dayTime
        let content = UNMutableNotificationContent()
        content.title = NotificationsSR.notificationTitle.text
        content.body = NotificationsSR.notificationDescription.text
        content.sound = UNNotificationSound.default

        var dateComponents = DateComponents()
        dateComponents.timeZone = TimeZone.current
        dateComponents.hour = Int(dayTime.hour)
        dateComponents.minute = Int(dayTime.minute)
        dateComponents.weekday = dayTimeAndWeekDay.weekDayNumber

        let trigger = UNCalendarNotificationTrigger(dateMatching: dateComponents, repeats: true)

        let request = UNNotificationRequest(identifier: dayTimeAndWeekDay.identifier, content: content, trigger: trigger)

        do {
            try await center.add(request)
            Logger.verbose("Notification scheduled with success \(dayTimeAndWeekDay)")
        } catch {
            Logger.error("Unable to schedule notification \(error.localizedDescription)")
        }
    }

}

private struct NotificationDayTimeAndWeekDay {
    var identifier: String {
        "\(dayTime.dayMinutes)_\(weekDayNumber)"
    }
    let dayTime: DayTime
    let weekDayNumber: Int
}

private extension Array where Element == NotificationInfo {
    var notificationDayTimeAndWeekDay: [NotificationDayTimeAndWeekDay] {
        map { info in
            let weekState = info.weekState
            let days: [(enabled: Bool, weekDayNumber: Int)] = [
                (weekState.sundayEnabled, 1),
                (weekState.mondayEnabled, 2),
                (weekState.tuesdayEnabled, 3),
                (weekState.wednesdayEnabled, 4),
                (weekState.thursdayEnabled, 5),
                (weekState.fridayEnabled, 6),
                (weekState.saturdayEnabled, 7)
            ].filter { $0.enabled }

            return days.map {
                NotificationDayTimeAndWeekDay(dayTime: info.dayTime, weekDayNumber: $0.weekDayNumber)
            }
        }.reduce([], +)
    }
}
