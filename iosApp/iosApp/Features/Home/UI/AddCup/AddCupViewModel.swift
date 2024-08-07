//
//  AddCupViewModel.swift
//  iosApp
//
//  Created by Lucas Calheiros on 03/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared
import Combine

class AddCupViewModel: ObservableObject {

    static func create() -> AddCupViewModel {
        let injector = WaterManagementInjector()
        return AddCupViewModel(
            getWaterSourceTypeUseCase: injector.getWaterSourceTypeUseCase(),
            getDefaultAddWaterSourceInfoUseCase: injector.getDefaultAddWaterSourceInfoUseCase(),
            getTodayWaterConsumptionSummaryUseCase: injector.getTodayWaterConsumptionSummaryUseCase(),
            createWaterSourceUseCase: injector.createWaterSourceUseCase()
        )
    }
    var cancellableBag = Set<AnyCancellable>()
    let getWaterSourceTypeUseCase: GetWaterSourceTypeUseCase
    let getDefaultAddWaterSourceInfoUseCase: GetDefaultAddWaterSourceInfoUseCase
    let getTodayWaterConsumptionSummaryUseCase: GetTodayWaterConsumptionSummaryUseCase
    let createWaterSourceUseCase: CreateWaterSourceUseCase

    @Published var state = AddCupState()

    init(
        getWaterSourceTypeUseCase: GetWaterSourceTypeUseCase,
        getDefaultAddWaterSourceInfoUseCase: GetDefaultAddWaterSourceInfoUseCase,
        getTodayWaterConsumptionSummaryUseCase: GetTodayWaterConsumptionSummaryUseCase,
        createWaterSourceUseCase: CreateWaterSourceUseCase
    ) {
        self.createWaterSourceUseCase = createWaterSourceUseCase
        self.getWaterSourceTypeUseCase = getWaterSourceTypeUseCase
        self.getDefaultAddWaterSourceInfoUseCase = getDefaultAddWaterSourceInfoUseCase
        self.getTodayWaterConsumptionSummaryUseCase = getTodayWaterConsumptionSummaryUseCase
    }

    func send(_ intent: AddCupIntent) {
        Task { @MainActor in
            switch intent {
            case .initData:
                await initData()

            case .onVolumeClick:
                state.showVolumeInputAlert = true

            case .onVolumeInputConfirm(let volume):
                onVolumeInputConfirm(volume)

            case .onVolumeInputCancel:
                state.showVolumeInputAlert = false
            
            case .onDrinkClick:
                state.showSelectDrinkAlert = true
                do {
                    state.availableDrinks = try await getWaterSourceTypeUseCase.get()
                } catch {
                    return 
                }

            case .onDrinkSelected(let waterSourceType):
                state.drink = waterSourceType
                state.showSelectDrinkAlert = false

            case .onDrinkSelectCancel:
                state.showSelectDrinkAlert = false

            case .onConfirmClick:
                await onConfirm()

            case .onCancelClick:
                state.isDismissed = true
            }
        }
    }

    @MainActor private func initData() async { 
        guard state.volume == nil || state.drink == nil else {
            return
        }
        let defaultInfo: DefaultAddWaterSourceInfo
        do {
            defaultInfo = try await getDefaultAddWaterSourceInfoUseCase.get()
        } catch {
            return
        }
        state.volume = defaultInfo.volume
        state.drink = defaultInfo.waterSourceType
    }


    private func onVolumeInputConfirm(_ volume: Double) {
        if let currentVolume = state.volume {
            state.volume = MeasureSystemVolumeCompanion().create(
                intrinsicValue: volume,
                measureSystemUnit_: currentVolume.volumeUnit()
            )
        }
        state.showVolumeInputAlert = false
    }

    private func onConfirm() async {
        do {
            try await createWaterSourceUseCase.create(CreateWaterSourceRequest(volume: state.volume!, waterSourceType: state.drink!))
        } catch {

        }
        state.isDismissed = true
    }
}

struct AddCupState {
    var isDismissed = false
    var volume: MeasureSystemVolume? = nil
    var drink: WaterSourceType? = nil
    var showVolumeInputAlert: Bool = false
    var showSelectDrinkAlert: Bool = false
    var availableDrinks: [WaterSourceType] = []
}

enum AddCupIntent {
    case initData
    case onVolumeClick
    case onVolumeInputConfirm(Double)
    case onVolumeInputCancel
    case onDrinkClick
    case onDrinkSelected(WaterSourceType)
    case onDrinkSelectCancel
    case onConfirmClick
    case onCancelClick
}
