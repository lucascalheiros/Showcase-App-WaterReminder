//
//  HomeScreen.swift
//  iosApp
//
//  Created by Lucas Calheiros on 02/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

public struct HomeScreen: View {
    @EnvironmentObject var theme: ThemeManager
    @StateObject var homeViewModel = HomeViewModel()

    var state: HomeState {
        homeViewModel.state
    }

    public init() {}

    public var body: some View {
        ScreenRootLayout {
            VStack {
                Spacer()
                TodaySummaryCard(todaySummaryCard: homeViewModel.state.todaySummary)
                    .frame(
                        minWidth: UIScreen.screenWidth - 32,
                        maxWidth: UIScreen.screenWidth - 32,
                        minHeight: 0,
                        maxHeight: UIScreen.screenWidth - 32
                    ).padding(.horizontal, 16)
                    .aspectRatio(1.0, contentMode: .fit)
                Spacer()
                CupCardsSection(
                    cards: state.cups,
                    addCupSheetVisible: state.addCupSheetVisible,
                    sendIntent: { homeViewModel.send($0) }
                )
                Spacer()
                DrinkCardsSection(
                    cards: state.drinks,
                    addDrinkSheetVisible: state.addDrinkSheetVisible,
                    drinkShortcutSheetVisible: state.drinkShortcutSheetVisible,
                    sendIntent: { homeViewModel.send($0) }
                )
                Spacer()
            }
            .navigationTitle(HomeSR.homeTitle.text)
            .foregroundStyle(theme.current.onBackgroundColor)
        }
        .ignoresSafeArea(.keyboard)
    }

}
