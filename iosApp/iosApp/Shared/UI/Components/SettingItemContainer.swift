//
//  SettingItemContainer.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct SettingItemContainer<ContentTitle: View, ContentValue: View>: View {
    @EnvironmentObject var theme: ThemeManager
    @ViewBuilder var title: ContentTitle
    @ViewBuilder var value: ContentValue

    var body: some View {
        HStack(alignment: .center) {
            title
            Spacer()
            value
        }
        .contentShape(Rectangle())
        .padding(.horizontal, 16)
        .frame(minHeight: 48)
    }
}
