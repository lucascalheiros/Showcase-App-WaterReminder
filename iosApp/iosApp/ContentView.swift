import SwiftUI
import Shared
import Combine
import DesignSystem
import Home
import History
import Settings
import FirstAccess

struct ContentView: View {
    @StateObject var themeManager = ThemeManager()
    let notificationManager = NotificationManager()
    let themeMode = SharedInjector().getThemeUseCase().publisher().map {
        $0.themeMode
    }.catch { _ in Just(nil) }.eraseToAnyPublisher().receive(on: RunLoop.main)

    var body: some View {
        ZStack {
            themeManager.current.backgroundColor.edgesIgnoringSafeArea(.all)
            FirstAccessScreen()
        }
        .setupTheme(themeManager)
        .environmentObject(themeManager)
        .onReceive(notificationManager.notificationState, perform: notificationManager.updateNotificationsState)
        .onReceive(themeMode, perform: themeManager.setThemeMode)
    }

    var mainAppScreen: some View {
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
    }
}

private extension View {
    func setupTheme(_ themeManager: ThemeManager) -> some View {
        onAppear {
            ThemeSetupUtil.setupAppBars(themeManager.current)
            ThemeSetupUtil.overrideUiStyle(themeManager.mode.style)
        }
        .onChange(of: themeManager.mode.style) { old, new in
            ThemeSetupUtil.overrideUiStyle(new)
        }
        .preferredColorScheme(themeManager.mode.scheme)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var themeManager = ThemeManager()

    static var previews: some View {
        ContentView()
            .environmentObject(themeManager)
    }
}

