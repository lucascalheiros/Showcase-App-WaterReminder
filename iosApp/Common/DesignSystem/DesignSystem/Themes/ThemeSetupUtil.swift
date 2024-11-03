//
//  ThemeSetupUtil.swift
//  iosApp
//
//  Created by Lucas Calheiros on 21/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

public struct ThemeSetupUtil {

    public static func overrideUiStyle(_ uiStyle: UIUserInterfaceStyle) {
        let scenes = UIApplication.shared.connectedScenes
        guard let scene = scenes.first as? UIWindowScene else { return }
        scene.keyWindow?.overrideUserInterfaceStyle = uiStyle
    }

    public static func setupAppBars(_ theme: ThemeProtocol) {
        let onBackgroundColor = UIColor(theme.onBackgroundColor)
        let backgroundColor = UIColor(theme.backgroundColor)
        setNavigationTitleColor(onBackgroundColor, backgroundColor)
        setNavigationBarColor(onBackgroundColor, backgroundColor)
    }

    private static func setNavigationTitleColor(_ tint: UIColor, _ background: UIColor) {
        let standardAppearance = UINavigationBarAppearance()
        standardAppearance.titleTextAttributes = [
            NSAttributedString.Key.foregroundColor : tint
        ]
        standardAppearance.configureWithTransparentBackground()
        standardAppearance.backgroundColor = background.withAlphaComponent(0.7)

        let scrollEdgeAppearance = UINavigationBarAppearance()
        scrollEdgeAppearance.titleTextAttributes = [
            NSAttributedString.Key.foregroundColor : tint
        ]
        scrollEdgeAppearance.backgroundColor = background
        scrollEdgeAppearance.shadowColor = .clear

        UINavigationBar.appearance().standardAppearance = standardAppearance
        UINavigationBar.appearance().compactAppearance = standardAppearance
        UINavigationBar.appearance().scrollEdgeAppearance = scrollEdgeAppearance

    }

    private static func setNavigationBarColor(_ tint: UIColor, _ background: UIColor) {
        let standardAppearance = UITabBarAppearance()
        standardAppearance.backgroundColor = background
        standardAppearance.shadowColor = tint
        let itemAppearance = UITabBarItemAppearance()
        itemAppearance.normal.iconColor = tint.withAlphaComponent(0.5)
        itemAppearance.selected.iconColor = tint
        standardAppearance.inlineLayoutAppearance = itemAppearance
        standardAppearance.stackedLayoutAppearance = itemAppearance
        standardAppearance.compactInlineLayoutAppearance = itemAppearance
        UITabBar.appearance().standardAppearance = standardAppearance
        UITabBar.appearance().scrollEdgeAppearance = standardAppearance
    }

}
