//
//  ActivityLevelInputViewModel.swift
//  iosApp
//
//  Created by Lucas Calheiros on 02/11/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Factory
import Combine
import Shared

class ActivityLevelInputViewModel: ObservableObject {

    @Injected(\.getUserProfileUseCase)
    private var getUserProfileUseCase

    @Injected(\.setUserProfileActivityLevelUseCase)
    private var setUserProfileActivityLevelUseCase

    @Published private(set) var state: ActivityLevelInputState = .init()

    private var cancellableBag: Set<AnyCancellable> = []

    func send(_ intent: ActivityLevelInputIntent) {
        Task {
            switch intent {
            case .loadData:
                loadData()
            case .setActivityLevel(let activityLevel):
                await setActivityLevel(activityLevel)
            }
        }
    }

    private func loadData() {
        getUserProfileUseCase.publisher()
            .first()
            .catch { _ in Empty<UserProfile, Never>() }
            .receive(on: RunLoop.main)
            .sink(receiveValue: { [weak self] profile in
                self?.state.activityLevel = ActivityLevel.from(profile.activityLevel)
            })
            .store(in: &cancellableBag)
    }

    @MainActor
    private func setActivityLevel(_ activityLevel: ActivityLevel) async {
        state.activityLevel = activityLevel
        try? await setUserProfileActivityLevelUseCase.set(activityLevel.activityLevel)
    }

}

struct ActivityLevelInputState {
    var activityLevel: ActivityLevel = .light
}

enum ActivityLevelInputIntent {
    case loadData
    case setActivityLevel(ActivityLevel)
}
