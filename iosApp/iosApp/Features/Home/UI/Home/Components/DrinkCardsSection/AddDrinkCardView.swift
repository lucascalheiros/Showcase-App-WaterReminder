//
//  AddDrinkCardView.swift
//  Home
//
//  Created by Lucas Calheiros on 08/09/24.
//

import SwiftUI

struct AddDrinkCardView: View {
    var onClick: () -> Void

    var body: some View {
        CardView {
            Text(.drinkCardAdd)
                .padding(.horizontal, 8)
        }
        .overlay(
            RoundedRectangle(cornerRadius: 10)
                .stroke(.gray, lineWidth: 1)
        )
        .onTapGesture {
            onClick()
        }
    }
}
