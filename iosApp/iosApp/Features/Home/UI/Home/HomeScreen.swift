//
//  HomeScreen.swift
//  iosApp
//
//  Created by Lucas Calheiros on 02/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct HomeScreen: View {
    @StateObject var homeViewModel = HomeViewModel.create()
    var homeState: HomeState {
        homeViewModel.state
    }

    var body: some View {
        ScreenRootLayout {
            HStack {
                StyledText("Today")
                Spacer()
            }.padding(.horizontal, 16)
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
                cups: homeState.cups,
                addCupSheetVisible: $homeViewModel.state.addCupSheetVisible,
                sendIntent: { homeViewModel.send($0) }
            )
            Spacer()
            DrinkCardsSection(
                drinks: homeState.drinks,
                addDrinkSheetVisible: $homeViewModel.state.addDrinkSheetVisible,
                drinkShortcutSheetVisible: $homeViewModel.state.drinkShortcutSheetVisible,
                sendIntent: { homeViewModel.send($0) }
            )
            Spacer()
        }
        .ignoresSafeArea(.keyboard)
    }

}
