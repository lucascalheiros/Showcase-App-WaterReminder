//
//  HomeViewModel.swift
//  iosApp
//
//  Created by Lucas Calheiros on 02/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared
import SwiftUI
import Combine

class HomeViewModel: ObservableObject {

    static func create() -> HomeViewModel {
        let injector = WaterManagementInjector()
        return HomeViewModel(
            getWaterSourceTypeUseCase: injector.getWaterSourceTypeUseCase(),
            getWaterSourceUseCase: injector.getWaterSourceUseCase(),
            getTodayWaterConsumptionSummaryUseCase: injector.getTodayWaterConsumptionSummaryUseCase(),
            registerConsumedWaterUseCase: injector.registerConsumedWaterUseCase(),
            deleteWaterSourceUseCase: injector.deleteWaterSourceUseCase(),
            deleteWaterSourceTypeUseCase: injector.deleteWaterSourceTypeUseCase()
        )
    }
    var cancellableBag = Set<AnyCancellable>()
    let getWaterSourceTypeUseCase: GetWaterSourceTypeUseCase
    let getWaterSourceUseCase: GetWaterSourceUseCase
    let getTodayWaterConsumptionSummaryUseCase: GetTodayWaterConsumptionSummaryUseCase
    let registerConsumedWaterUseCase: RegisterConsumedWaterUseCase
    let deleteWaterSourceUseCase: DeleteWaterSourceUseCase
    let deleteWaterSourceTypeUseCase: DeleteWaterSourceTypeUseCase

    @Published var state = HomeState()

    init(
        getWaterSourceTypeUseCase: GetWaterSourceTypeUseCase,
        getWaterSourceUseCase: GetWaterSourceUseCase,
        getTodayWaterConsumptionSummaryUseCase: GetTodayWaterConsumptionSummaryUseCase,
        registerConsumedWaterUseCase: RegisterConsumedWaterUseCase,
        deleteWaterSourceUseCase: DeleteWaterSourceUseCase,
        deleteWaterSourceTypeUseCase: DeleteWaterSourceTypeUseCase
    ) {
        self.getWaterSourceTypeUseCase = getWaterSourceTypeUseCase
        self.getWaterSourceUseCase = getWaterSourceUseCase
        self.getTodayWaterConsumptionSummaryUseCase = getTodayWaterConsumptionSummaryUseCase
        self.registerConsumedWaterUseCase = registerConsumedWaterUseCase
        self.deleteWaterSourceUseCase = deleteWaterSourceUseCase
        self.deleteWaterSourceTypeUseCase = deleteWaterSourceTypeUseCase
        observeStateProducer()
    }

    private func observeStateProducer() {
        getWaterSourceTypeUseCase.publisher.combineLatest(
            getWaterSourceUseCase.publisher,
            getTodayWaterConsumptionSummaryUseCase.publisher
        )
        .receive(on: RunLoop.main)
        .sink(receiveCompletion: { _ in }, receiveValue: { [weak self] waterSourceType, waterSource, todaySummary in
            self?.state.cups = waterSource.map { CupCard.item($0) } + [CupCard.addItem]
            self?.state.drinks = waterSourceType.map { DrinkCard.item($0) } + [DrinkCard.addItem]
            self?.state.todaySummary = todaySummary
        }).store(in: &cancellableBag)
    }

    func send(_ intent: HomeIntent) {
        Task { @MainActor in
            switch intent {

            case .onCupClick(let cup):
                try await registerConsumedWaterUseCase.register(cup.volume, cup.waterSourceType)
           
            case .onDeleteCupClick(let cup):
                try await deleteWaterSourceUseCase.delete(cup.waterSourceId)

            case .onMoveCupToPosition(_, position: let position):
                return
            
            case .onDrinkClick(let item):
                state.drinkShortcutSheetVisible = item

            case .onDrinkShortcutDismissed:
                state.drinkShortcutSheetVisible = nil

            case .onMoveDrinkToPosition(_, position: let position):
                return

            case .onDeleteDrinkClick(let drink):
                try await deleteWaterSourceTypeUseCase.delete(drink.waterSourceTypeId)

            case .onAddCupClick:
                state.addCupSheetVisible = true
            
            case .onAddCupDismissed:
                state.addCupSheetVisible = false

            case .onAddDrinkClick:
                state.addDrinkSheetVisible = true

            case .onAddDrinkDismissed:
                state.addDrinkSheetVisible = false

            }
        }
    }
}

struct HomeState {
    var cups = [CupCard]()
    var drinks = [DrinkCard]()
    var todaySummary: DailyWaterConsumptionSummary? = nil
    var addCupSheetVisible: Bool = false
    var addDrinkSheetVisible: Bool = false
    var drinkShortcutSheetVisible: WaterSourceType? = nil
}

enum HomeIntent {
    case onAddCupClick
    case onAddCupDismissed
    case onCupClick(WaterSource)
    case onDeleteCupClick(WaterSource)
    case onMoveCupToPosition(WaterSource, position: Int)
    case onDrinkClick(WaterSourceType)
    case onDrinkShortcutDismissed
    case onMoveDrinkToPosition(WaterSourceType, position: Int)
    case onAddDrinkClick
    case onAddDrinkDismissed
    case onDeleteDrinkClick(WaterSourceType)
}

enum CupCard: Identifiable {
    case item(WaterSource)
    case addItem

    var id: Int64 {
        switch self {

        case .item(let data):
            data.waterSourceId
        case .addItem:
            -1
        }
    }

    var name: String {
        switch self {
        case .item(let item):
            item.waterSourceType.name
        case .addItem:
            "Add"
        }
    }

    var volume: String? {
        switch self {
        case .item(let item):
            item.volume.shortValueAndUnitFormatted
        case .addItem:
            nil
        }
    }

}

enum DrinkCard: Identifiable {
    case item(WaterSourceType)
    case addItem

    var id: Int64 {
        switch self {

        case .item(let data):
            data.waterSourceTypeId
        case .addItem:
            -1

        }
    }

    var name: String {
        switch self {
        case .item(let item):
            item.name
        case .addItem:
            "Add"
        }
    }

}
