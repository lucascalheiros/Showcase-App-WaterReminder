//
//  SettingItemContainer.swift
//  iosApp
//
//  Created by Lucas Calheiros on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

public struct SettingItemContainer<ContentTitle: View, ContentValue: View, ContentBody: View>: View {
    @EnvironmentObject var theme: ThemeManager
    var title: (() -> ContentTitle)?
    var value: (() -> ContentValue)?
    var content: (() -> ContentBody)?

    public init(
        @ViewBuilder content: @escaping () -> ContentBody
    ) where ContentTitle == AnyView, ContentValue == AnyView {
        self.content = content
    }

    public init(
        @ViewBuilder title: @escaping () -> ContentTitle,
        @ViewBuilder value: @escaping () -> ContentValue
    ) where ContentBody == AnyView {
        self.title = title
        self.value = value
    }

    public init(
        _ title: String,
        @ViewBuilder _ value: @escaping () -> ContentValue
    ) where ContentTitle == Text, ContentBody == AnyView {
        self.init(
            title: { Text(title) },
            value: value
        )
    }

    public init(
        _ title: String,
        _ value: String? = nil
    ) where ContentTitle == Text, ContentValue == Text, ContentBody == AnyView {
        self.title = { Text(title) }
        if let value {
            self.value = { Text(value) }
        }
    }

    public var body: some View {
        HStack(alignment: .center) {
            if let content {
                content()
            } else {
                title?()
                    .layoutPriority(1)
                Spacer()
                value?()
                    .bold()
                    .foregroundStyle(theme.current.primaryColor)
            }
        }
        .contentShape(Rectangle())
        .padding(.horizontal, 16)
        .frame(minHeight: 48)
    }
}

