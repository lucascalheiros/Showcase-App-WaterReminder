//
//  ManageNotificationsViewModel.swift
//  iosApp
//
//  Created by Lucas Calheiros on 18/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared
import Combine

class ManageNotificationsViewModel: ObservableObject {

    private var cancellableBag = Set<AnyCancellable>()
    let getScheduledNotificationUseCase: GetScheduledNotificationsUseCase
    let deleteScheduledNotificationUseCase: DeleteScheduledNotificationUseCase
    let setWeekDayNotificationStateUseCase: SetWeekDayNotificationStateUseCase
    let createScheduleNotificationUseCase: CreateScheduleNotificationUseCase
    var selectedNotifications: [NotificationInfo] {
        state.notificationInfoList.filter {
            state.selectionMap[$0.id] == true
        }
    }

    @Published var state: ManageNotificationsState = ManageNotificationsState()

    init(
        getScheduledNotificationUseCase: GetScheduledNotificationsUseCase,
        deleteScheduledNotificationUseCase: DeleteScheduledNotificationUseCase,
        setWeekDayNotificationStateUseCase: SetWeekDayNotificationStateUseCase,
        createScheduleNotificationUseCase: CreateScheduleNotificationUseCase
    ) {
        self.getScheduledNotificationUseCase = getScheduledNotificationUseCase
        self.deleteScheduledNotificationUseCase = deleteScheduledNotificationUseCase
        self.setWeekDayNotificationStateUseCase = setWeekDayNotificationStateUseCase
        self.createScheduleNotificationUseCase = createScheduleNotificationUseCase
        observeState()
    }

    private func observeState() {
        getScheduledNotificationUseCase.publisher()
            .receive(on: RunLoop.main)
            .sink(
                receiveCompletion: { _ in }, receiveValue: { [weak self] infoList in
                    self?.state.notificationInfoList = infoList
                }
            )
            .store(in: &cancellableBag)
    }

    func send(_ intent: ManageNotificationsIntent) {
        Task { @MainActor in
            switch intent {

            case .showAddNotifications:
                state.showAddNotificationsSheet = true

            case .dismissAddNotifications:
                state.showAddNotificationsSheet = false

            case .confirmAddNotifications(let result):
                await handleAddNotificationsRequest(result)

            case .dismissWeekDayPicker:
                state.popoverData = nil
                state.showWeekDayPickerForSelection = false

            case .showWeekDayPicker(let data):
                state.popoverData = data

            case .setWeekState(let data, let weekState):
                await handleWeekDayPopoverResponse(data, weekState)

            case .enableSelectionMode:
                enableSelectionMode()

            case .disableSelectionMode:
                disableSelectionMode()

            case .selectItem(let data):
                selectItem(data)

            case .unselectItem(let data):
                unselectItem(data)

            case .selectAll:
                selectAll()

            case .unselectAll:
                unselectAll()

            case .deleteSelected:
                await deleteSelectedNotifications()

            case .showWeekDayPickerForSelected:
                state.showWeekDayPickerForSelection = true

            case .setWeekStateForSelection(let weekState):
                state.showWeekDayPickerForSelection = false
                for info in selectedNotifications {
                    try? await setWeekDayNotificationStateUseCase.set(info.dayTime, weekState)
                }

            case .toggleAllSelection:
                enableSelectionMode()
                if state.allSelected {
                    unselectAll()
                } else {
                    selectAll()
                }
            }
        }
    }

    private func handleWeekDayPopoverResponse(_ data: WeekDayPopoverData, _ weekState: WeekState) async {
        switch data.type {
        case .single(let dayTime):
            try? await setWeekDayNotificationStateUseCase.set(dayTime, weekState)

        case .selected:
            for info in selectedNotifications {
                try? await setWeekDayNotificationStateUseCase.set(info.dayTime, weekState)
            }
        }
    }

    private func selectItem(_ info: NotificationInfo) {
        state.selectionMap[info.id] = true
    }

    private func unselectItem(_ info: NotificationInfo) {
        state.selectionMap[info.id] = false
    }

    private func enableSelectionMode() {
        state.isInSelectionMode = true
    }

    private func disableSelectionMode() {
        state.isInSelectionMode = false
        unselectAll()
    }

    private func selectAll() {
        state.notificationInfoList.forEach {
            state.selectionMap[$0.id] = true
        }
    }

    private func unselectAll() {
        state.notificationInfoList.forEach {
            state.selectionMap[$0.id] = false
        }
    }

    private func deleteSelectedNotifications() async {
        for notification in selectedNotifications {
            try? await deleteScheduledNotificationUseCase.delete(DeleteScheduledNotificationRequestSingle(dayTime: notification.dayTime))
        }
    }

    @MainActor
    private func handleAddNotificationsRequest(_ request: AddNotificationResult) async {
        state.showAddNotificationsSheet = false
        switch request {
        case .multiple(let data):
            if data.deleteOther {
                try? await deleteScheduledNotificationUseCase.delete(DeleteScheduledNotificationRequestAll())
            }
            try? await createScheduleNotificationUseCase.create(
                NotificationsPeriod(
                    start: data.startTime.dayTime,
                    stop: data.stopTime.dayTime,
                    period: data.notifyEach.dayTime
                ), data.notificationDays.weekState)
        case .single(let data):
            try? await createScheduleNotificationUseCase.create(data.notificationTime.dayTime, data.notificationDays.weekState)
        }
    }
}

extension ManageNotificationsViewModel {
    convenience init() {
        let injector = SharedInjector()
        self.init(
            getScheduledNotificationUseCase: injector.getScheduledNotificationsUseCase(),
            deleteScheduledNotificationUseCase: injector.deleteScheduledNotificationUseCase(),
            setWeekDayNotificationStateUseCase: injector.setWeekDayNotificationStateUseCase(),
            createScheduleNotificationUseCase: injector.createScheduleNotificationUseCase()
        )
    }
}
