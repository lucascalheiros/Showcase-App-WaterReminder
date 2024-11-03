//
//  HomeState.swift
//  Home
//
//  Created by Lucas Calheiros on 08/09/24.
//

import Shared

struct HomeState {
    var cups = [CupCard]()
    var drinks = [DrinkCard]()
    var todaySummary: DailyWaterConsumptionSummary? = nil
    var addCupSheetVisible: Bool = false
    var addDrinkSheetVisible: Bool = false
    var drinkShortcutSheetVisible: WaterSourceType? = nil
}
