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
import Factory

class HomeViewModel: ObservableObject {

    private var cancellableBag = Set<AnyCancellable>()

    @Injected(\.getWaterSourceTypeUseCase)
    private var getWaterSourceTypeUseCase

    @Injected(\.getWaterSourceUseCase)
    private var getWaterSourceUseCase

    @Injected(\.getTodayWaterConsumptionSummaryUseCase)
    private var getTodayWaterConsumptionSummaryUseCase

    @Injected(\.registerConsumedWaterUseCase)
    private var registerConsumedWaterUseCase

    @Injected(\.deleteWaterSourceUseCase)
    private var deleteWaterSourceUseCase

    @Injected(\.deleteWaterSourceTypeUseCase)
    private var deleteWaterSourceTypeUseCase

    @Published private(set) var state = HomeState()

    init() {
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
