//
//  NotificationInputViewModel.swift
//  iosApp
//
//  Created by Lucas Calheiros on 02/11/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Factory
import Combine

class NotificationInputViewModel: ObservableObject {

    @Injected(\.getFirstAccessNotificationDataUseCase)
    private var getFirstAccessNotificationDataUseCase

    @Injected(\.setFirstAccessNotificationStateUseCase)
    private var setFirstAccessNotificationStateUseCase

    @Injected(\.setNotificationFrequencyTimeUseCase)
    private var setNotificationFrequencyTimeUseCase

    @Injected(\.setStartNotificationTimeUseCase)
    private var setStartNotificationTimeUseCase

    @Injected(\.setStopNotificationTimeUseCase)
    private var setStopNotificationTimeUseCase

    private var cancellableBag: Set<AnyCancellable> = []

    @Published private(set) var state: NotificationInputState = .init()

    func send(_ intent: NotificationInputIntent) {
        Task {
            switch intent {
            case .loadData:
                await loadData()
            case .setStartTime(let date):
                await setStartTime(date)
            case .setStopTime(let date):
                await setStopTime(date)
            case .setFrequency(let date):
                await setFrequency(date)
            case .setDisabled(let isDisabled):
                await setDisableNotification(isDisabled)
            }
        }
    }

    @MainActor
    private func loadData() async {
        var stateIterator =  getFirstAccessNotificationDataUseCase
            .execute()
            .values
            .makeAsyncIterator()
        guard let newState = try? await stateIterator.next() else {
            return
        }
        state.startTime = newState.startTime.toDateSw()
        state.stopTime = newState.stopTime.toDateSw()
        state.frequency = newState.frequencyTime.toDateSw()
        state.isDisabled = !newState.shouldEnable
    }

    @MainActor
    private func setStartTime(_ date: Date) async {
        state.startTime = date
        try? await setStartNotificationTimeUseCase.execute(date.ktLocalTime)
    }

    @MainActor
    private func setStopTime(_ date: Date) async {
        state.stopTime = date
        try? await setStopNotificationTimeUseCase.execute(date.ktLocalTime)
    }

    @MainActor
    private func setFrequency(_ date: Date) async {
        state.frequency = date
        try? await setNotificationFrequencyTimeUseCase.execute(date.ktLocalTime)
    }

    @MainActor
    private func setDisableNotification(_ value: Bool) async {
        state.isDisabled = value
        try? await setFirstAccessNotificationStateUseCase.execute(value: !value)
    }
}

struct NotificationInputState: Equatable {
    var startTime: Date = Calendar.current.date(bySettingHour: 8, minute: 0, second: 0, of: Date())!
    var stopTime: Date = Calendar.current.date(bySettingHour: 20, minute: 0, second: 0, of: Date())!
    var frequency: Date = Calendar.current.date(bySettingHour: 2, minute: 0, second: 0, of: Date())!
    var isDisabled: Bool = false
}

enum NotificationInputIntent {
    case loadData
    case setStartTime(Date)
    case setStopTime(Date)
    case setFrequency(Date)
    case setDisabled(Bool)
}
