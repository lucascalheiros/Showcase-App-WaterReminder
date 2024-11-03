//
//  CupCardsSection.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared
import DesignSystem
import Util

struct CupCardsSection: View {
    var cards: [CupCard]
    var addCupSheetVisible: Bool
    var sendIntent: (HomeIntent) -> Void

    var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            LazyHGrid(rows: [
                GridItem(.fixed(100)),
                GridItem(.fixed(100))
            ], content: {
                ForEach(cards) { card in
                    CupCardView(card: card)
                        .animation(.interactiveSpring(), value: cards.count)
                        .contextMenu {
                            if case .item(let cup) = card {
                                Button {
                                    sendIntent(.onDeleteCupClick(cup))
                                } label: {
                                    Text(.cupDelete)
                                    ImageResources.deleteIcon.image()
                                }
                            }
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
        .sheet(isPresented: addCupSheetVisible.bindingWithDismiss {
            sendIntent(.onAddCupDismissed)
        }) {
            AddCupBottomSheet(onDismiss: { sendIntent(.onAddCupDismissed) })
                .presentationDetents([.medium])
                .onDisappear(perform: {
                    sendIntent(.onAddCupDismissed)
                })
        }
    }
}
