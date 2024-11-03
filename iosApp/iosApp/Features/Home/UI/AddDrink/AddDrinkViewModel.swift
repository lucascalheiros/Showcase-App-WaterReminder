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

class AddDrinkViewModel: ObservableObject {

    var cancellableBag = Set<AnyCancellable>()
    let getWaterSourceTypeUseCase: GetWaterSourceTypeUseCase
    let getDefaultAddWaterSourceInfoUseCase: GetDefaultAddWaterSourceInfoUseCase
    let getTodayWaterConsumptionSummaryUseCase: GetTodayWaterConsumptionSummaryUseCase
    let createWaterSourceTypeUseCase: CreateWaterSourceTypeUseCase

    @Published private(set) var state = AddDrinkState()

    init(
        getWaterSourceTypeUseCase: GetWaterSourceTypeUseCase,
        getDefaultAddWaterSourceInfoUseCase: GetDefaultAddWaterSourceInfoUseCase,
        getTodayWaterConsumptionSummaryUseCase: GetTodayWaterConsumptionSummaryUseCase,
        createWaterSourceTypeUseCase: CreateWaterSourceTypeUseCase
    ) {
        self.getWaterSourceTypeUseCase = getWaterSourceTypeUseCase
        self.getDefaultAddWaterSourceInfoUseCase = getDefaultAddWaterSourceInfoUseCase
        self.getTodayWaterConsumptionSummaryUseCase = getTodayWaterConsumptionSummaryUseCase
        self.createWaterSourceTypeUseCase = createWaterSourceTypeUseCase
    }

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

extension AddDrinkViewModel {
    convenience init() {
        let injector = WaterManagementInjector()
        self.init(
            getWaterSourceTypeUseCase: injector.getWaterSourceTypeUseCase(),
            getDefaultAddWaterSourceInfoUseCase: injector.getDefaultAddWaterSourceInfoUseCase(),
            getTodayWaterConsumptionSummaryUseCase: injector.getTodayWaterConsumptionSummaryUseCase(),
            createWaterSourceTypeUseCase: injector.createWaterSourceTypeUseCase()
        )
    }
}
