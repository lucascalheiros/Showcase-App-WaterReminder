//
//  RemindNotificationsSection.swift
//  iosApp
//
//  Created by Lucas Calheiros on 27/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct RemindNotificationsSection: View {
    @EnvironmentObject var theme: ThemeManager
    @State private var notificationEnabled = true
    @State var showManageNotifications: Bool = false
    @State var missingAlertPermission: Bool = false
    @State var missingSoundPermission: Bool = false
    @State var showAlertPermissionDenied: Bool = false
    @State var showAlertSoundPermissionDenied: Bool = false
    var state: RemindNotificationsSectionState
    var sendIntent: (SettingIntent) -> Void

    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            SettingSectionHeaderTitle(SettingsSR.remindNotificationsHeaderTitle.text)
            SettingGroupContainer {
                SettingItemContainer {
                    Toggle(SettingsSR.notificationsEnabledOption.text, isOn: $notificationEnabled)
                        .tint(theme.current.primaryColor)
                }
                SettingItemDivider()
                SettingItemContainer(SettingsSR.manageNotificationsOption.text) {
                    ImageResources.arrowRight.image()
                }
                .onTapGesture {
                    showManageNotifications.toggle()
                }
                .navigationDestination(isPresented: $showManageNotifications, destination: { ManageNotificationsScreen() })
                if missingAlertPermission {
                    SettingItemDivider()
                    SettingItemContainer(SettingsSR.missingAlertPermission.text) {
                        
                    }
                    .onTapGesture {
                        Task {
                            await requestAlertPermissions()
                        }
                    }
                    .alert(isPresented: $showAlertPermissionDenied) {
                        Alert(
                            title:  Text(SettingsSR.alertPermissionDeniedDescription.text) ,
                            message: Text(SettingsSR.alertPermissionDeniedDescription.text),
                            primaryButton: .default(Text(SettingsSR.alertPermissionSettingsOption.text)) {
                                if let url = URL(string: UIApplication.openSettingsURLString) {
                                    UIApplication.shared.open(url)
                                }
                            },
                            secondaryButton: .cancel()
                        )
                    }
                }
                if !missingAlertPermission && missingSoundPermission {
                    SettingItemDivider()
                    SettingItemContainer(SettingsSR.missingAlertSoundPermission.text) {
                        
                    }
                    .onTapGesture {
                        Task {
                            await requestAlertPermissions()
                        }
                    }
                    .alert(isPresented: $showAlertSoundPermissionDenied) {
                        Alert(
                            title:  Text(SettingsSR.alertSoundPermissionDeniedTitle.text) ,
                            message: Text(SettingsSR.alertSoundPermissionDeniedDescription.text),
                            primaryButton: .default(Text(SettingsSR.alertPermissionSettingsOption.text)) {
                                if let url = URL(string: UIApplication.openSettingsURLString) {
                                    UIApplication.shared.open(url)
                                }
                            },
                            secondaryButton: .cancel()
                        )
                    }
                }
            }
            .task {
                await updateAlertPermissions()
            }
            .onReceive(NotificationCenter.default.publisher(for: UIApplication.didBecomeActiveNotification)) { _ in
                Task {
                    await updateAlertPermissions()
                }
            }
        }
    }

    @MainActor
    func requestAlertPermissions() async {
        let center = UNUserNotificationCenter.current()

        do {
            try await center.requestAuthorization(options: [.alert, .sound])
        } catch {
            Logger.error("Notification permission request failed \(error.localizedDescription)")
        }
        await updateAlertPermissions()
        if missingAlertPermission {
            showAlertPermissionDenied = true
        } else if missingSoundPermission {
            showAlertSoundPermissionDenied = true
        }
    }

    @MainActor
    func updateAlertPermissions() async {
        let center = UNUserNotificationCenter.current()
        let settings = await center.notificationSettings()
        missingAlertPermission = settings.alertSetting != .enabled
        missingSoundPermission = settings.soundSetting != .enabled
    }
}


struct RemindNotificationsSection_Preview: PreviewProvider {

    static let themeManager = ThemeManager()

    static var previews: some View {
        RemindNotificationsSection(state: RemindNotificationsSectionState(isNotificationEnabled: false), sendIntent: { _ in})
            .environmentObject(themeManager)
    }
}
