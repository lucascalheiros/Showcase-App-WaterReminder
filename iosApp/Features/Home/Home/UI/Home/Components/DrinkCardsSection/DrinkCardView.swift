//
//  DrinkCardView.swift
//  Home
//
//  Created by Lucas Calheiros on 08/09/24.
//

import SwiftUI
import DesignSystem
import Shared

struct DrinkCardView: View {
    @Environment(\.colorScheme) var colorScheme
    var drink: WaterSourceType
    var onDelete: () -> Void
    var onClick: () -> Void

    var body: some View {
        CardView {
            Text(drink.name)
                .foregroundStyle(drink.color(colorScheme))
                .padding(.horizontal, 8)

        }
        .overlay(
            RoundedRectangle(cornerRadius: 10)
                .stroke(.gray, lineWidth: 1)
        )
        .contextMenu {
            Button {
                onDelete()
            } label: {
                Text(.drinkDelete)
                ImageResources.deleteIcon.image()
            }
        }
        .onTapGesture {
            onClick()
        }
    }
}
