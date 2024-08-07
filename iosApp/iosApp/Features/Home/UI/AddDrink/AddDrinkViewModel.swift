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

    static func create() -> AddDrinkViewModel {
        let injector = WaterManagementInjector()
        return AddDrinkViewModel(
            getWaterSourceTypeUseCase: injector.getWaterSourceTypeUseCase(),
            getDefaultAddWaterSourceInfoUseCase: injector.getDefaultAddWaterSourceInfoUseCase(),
            getTodayWaterConsumptionSummaryUseCase: injector.getTodayWaterConsumptionSummaryUseCase()
        )
    }
    var cancellableBag = Set<AnyCancellable>()
    let getWaterSourceTypeUseCase: GetWaterSourceTypeUseCase
    let getDefaultAddWaterSourceInfoUseCase: GetDefaultAddWaterSourceInfoUseCase
    let getTodayWaterConsumptionSummaryUseCase: GetTodayWaterConsumptionSummaryUseCase

    @Published var state = AddDrinkState()

    init(
        getWaterSourceTypeUseCase: GetWaterSourceTypeUseCase,
        getDefaultAddWaterSourceInfoUseCase: GetDefaultAddWaterSourceInfoUseCase,
        getTodayWaterConsumptionSummaryUseCase: GetTodayWaterConsumptionSummaryUseCase
    ) {
        self.getWaterSourceTypeUseCase = getWaterSourceTypeUseCase
        self.getDefaultAddWaterSourceInfoUseCase = getDefaultAddWaterSourceInfoUseCase
        self.getTodayWaterConsumptionSummaryUseCase = getTodayWaterConsumptionSummaryUseCase
        observeStateProducer()
    }

    private func observeStateProducer() {

    }

    func send(_ intent: AddDrinkIntent) {
        Task { @MainActor in
            switch intent {

            case .initData:
                return
            case .onCancelClick:
                return
            case .onConfirmClick:
                return
            case .onNameClick:
                return
            case .onNameAlertDismiss:
                return
            case .onNameChange(_):
                return
            case .onHydrationClick:
                return
            case .onHydrationAlertDismiss:
                return
            case .onHydrationChange(_):
                return
            case .onColorClick:
                return
            case .onColorAlertDismiss:
                return
            case .onColorChange(_):
                return
            }
        }
    }
}

struct AddDrinkState {
    var isDismissed = false
    var name: String = ""
    var hydration: Float? = nil
    var color: ThemeAwareColor? = nil
    var showNameInputAlert: Bool = false
    var showSelectHydrationAlert: Bool = false
    var showSelectColorAlert: Bool = false
}

enum AddDrinkIntent {
    case initData
    case onCancelClick
    case onConfirmClick
    case onNameClick
    case onNameAlertDismiss
    case onNameChange(String)
    case onHydrationClick
    case onHydrationAlertDismiss
    case onHydrationChange(Float)
    case onColorClick
    case onColorAlertDismiss
    case onColorChange(ThemeAwareColor)
}
