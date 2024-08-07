import SwiftUI
import Shared

struct ContentView: View {
    @StateObject var themeManager = ThemeManager()

    var body: some View {
        ZStack {
            themeManager.selectedTheme.backgroundColor.edgesIgnoringSafeArea(.all)
            TabView {
                HomeScreen()
                    .tabItem {
                        ImageResources.dropIcon.image()
                    }
                HistoryScreen()
                    .tabItem {
                        ImageResources.barChartIcon.image()
                    }
                SettingsScreen()
                    .tabItem {
                        ImageResources.settingIcon.image()
                    }
            }
            .onAppear() {
                let theme = themeManager.selectedTheme
                let standardAppearance = UITabBarAppearance()
                standardAppearance.backgroundColor = UIColor(theme.backgroundColor)
                standardAppearance.shadowColor = UIColor(theme.onBackgroundColor)

                let itemAppearance = UITabBarItemAppearance()
                itemAppearance.normal.iconColor = UIColor(theme.onBackgroundColor).withAlphaComponent(0.5)
                itemAppearance.selected.iconColor = UIColor(theme.onBackgroundColor)
                standardAppearance.inlineLayoutAppearance = itemAppearance
                standardAppearance.stackedLayoutAppearance = itemAppearance
                standardAppearance.compactInlineLayoutAppearance = itemAppearance
                UITabBar.appearance().standardAppearance = standardAppearance
                UITabBar.appearance().scrollEdgeAppearance = standardAppearance
            }
        }.environmentObject(themeManager)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
