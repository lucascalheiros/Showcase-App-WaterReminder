//
//  AddDrinkViewModel.swift
//  iosApp
//
//  Created by Lucas Calheiros on 03/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared
import Combine
import Factory

class AddDrinkViewModel: ObservableObject {

    private var cancellableBag = Set<AnyCancellable>()

    @Injected(\.getWaterSourceTypeUseCase)
    private var getWaterSourceTypeUseCase

    @Injected(\.getDefaultAddWaterSourceInfoUseCase)
    private var getDefaultAddWaterSourceInfoUseCase

    @Injected(\.getTodayWaterConsumptionSummaryUseCase)
    private var getTodayWaterConsumptionSummaryUseCase

    @Injected(\.createWaterSourceTypeUseCase)
    private var createWaterSourceTypeUseCase

    @Published private(set) var state = AddDrinkState()

    func send(_ intent: AddDrinkIntent) {
        Task { @MainActor in
            switch intent {

            case .initData:
                return
            case .onCancelClick:
                state.isDismissed = true
            case .onConfirmClick:
                state.isDismissed = true
                try await createWaterSourceTypeUseCase.create(CreateWaterSourceTypeRequest(name: state.name, themeAwareColor: state.color.themeAwareColor, hydrationFactor: state.hydration))
            case .onNameClick:
                state.showNameInputAlert = true
            case .onNameAlertDismiss:
                state.showNameInputAlert = false
            case .onNameChange(let name):
                state.name = name
            case .onHydrationClick:
                state.showSelectHydrationAlert = true
            case .onHydrationAlertDismiss:
                state.showSelectHydrationAlert = false
            case .onHydrationChange(let hydration):
                state.hydration = hydration
            case .onColorClick:
                state.showSelectColorAlert = true
            case .onColorAlertDismiss:
                state.showSelectColorAlert = false
            case .onColorChange(let color):
                state.showSelectColorAlert = false
                state.color = color
            }
        }
    }
}

extension ThemedColor {
    var themeAwareColor: ThemeAwareColor {
        ThemeAwareColor(onLightColor: lightColor.int32Value, onDarkColor: darkColor.int32Value)
    }
}
