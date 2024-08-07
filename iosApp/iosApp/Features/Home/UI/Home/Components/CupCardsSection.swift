//
//  CupCardsSection.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct CupCardsSection: View {
    var cups: [CupCard]
    var addCupSheetVisible: Binding<Bool>
    var sendIntent: (HomeIntent) -> Void

    var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            LazyHGrid(rows: [
                GridItem(.fixed(100)),
                GridItem(.fixed(100))
            ], content: {
                ForEach(cups) { card in
                    CupCardView(card: card)
                        .animation(.interactiveSpring(), value: cups.count)
                        .contextMenu {
                            if case .item(let cup) = card {
                                Button {
                                    sendIntent(.onDeleteCupClick(cup))
                                } label: {
                                    Text("Delete")
                                    ImageResources.deleteIcon.image()
                                }
                            }
                        }
                        .sheet(isPresented: addCupSheetVisible) {
                            AddCupBottomSheet(onDismiss: { sendIntent(.onAddCupDismissed) })
                                .presentationDetents([.medium])
                                .onDisappear(perform: {
                                    sendIntent(.onAddCupDismissed)
                                })
                        }
                        .onTapGesture {
                            switch card {

                            case .item(let waterSource):
                                sendIntent(.onCupClick(waterSource))
                            case .addItem:
                                sendIntent(.onAddCupClick)
                            }
                        }
                }
            }).padding(.horizontal, 16)
        }
    }
}

private struct CupCardView: View {
    @EnvironmentObject var theme: ThemeManager
    @Environment(\.colorScheme) var colorScheme
    let card: CupCard

    var body: some View {
        CardView {
            VStack {
                switch card {
                case .item(let drink):
                    let color = drink.waterSourceType.color(colorScheme)
                    HStack {
                        Text(card.name)
                            .font(theme.selectedTheme.titleSmall)
                            .foregroundStyle(color)
                        Spacer()
                    }
                    Spacer()
                    HStack {
                        Spacer()
                        Text(card.volume ?? "")
                            .font(theme.selectedTheme.titleMedium)
                            .foregroundStyle(color)
                    }
                case .addItem:
                    Text(card.name)
                        .font(theme.selectedTheme.titleMedium)
                }
            }.padding()
        }
        .cupCardFrame()
        .overlay(
            RoundedRectangle(cornerRadius: 10)
                .stroke(.gray, lineWidth: 1)
        )
    }
}


private extension CardView {
    func cupCardFrame() -> some View {
        let width = (UIScreen.screenWidth - 40) / 2
        return frame(
            minWidth: width,
            maxWidth: width,
            minHeight: 0,
            maxHeight: .infinity,
            alignment: .center
        )
    }
}
