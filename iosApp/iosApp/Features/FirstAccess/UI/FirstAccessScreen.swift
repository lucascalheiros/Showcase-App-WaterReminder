//
//  FirstAccessScreen.swift
//  FirstAccess
//
//  Created by Lucas Calheiros on 10/09/24.
//

import SwiftUI

public struct FirstAccessScreen: View {

    @EnvironmentObject var theme: ThemeManager

    @StateObject var viewModel: FirstAccessViewModel = .init()

    @State private var screen: FirstAccessScreens = .welcome
    
    public init() {
    }
    
    public var body: some View {
        VStack {
            ScrollViewReader { value in
                ScrollView(.horizontal) {
                    LazyHStack {
                        WelcomePage()
                            .screenId(.welcome)
                        WeightInputPage()
                            .screenId(.weightInput)
                        ActivityLevelInputPage()
                            .screenId(.activityLevelInput)
                        TemperatureLevelInputPage()
                            .screenId(.temperatureLevelInput)
                        NotificationInputPage()
                            .screenId(.notificationInput)
                        FirstAccessConfirmationPage()
                            .screenId(.confirmation)
                    }
                    .font(theme.current.bodyLarge)
                    .scrollTargetLayout()
                }
                .scrollDisabled(true)
                .scrollTargetBehavior(.viewAligned)
                .scrollIndicators(.never)
                .onChange(of: screen) { old, new in
                    value.animatedScrollTo(new)
                }
            }
            navigationButtons
        }
        
    }
    
    var currentIndex: Int {
        FirstAccessScreens.allCases.firstIndex(of: screen) ?? 0
    }

    var isFirst: Bool {
        currentIndex == 0
    }

    var isLast: Bool {
        currentIndex == FirstAccessScreens.allCases.count - 1
    }

    func navigateBack() {
        withAnimation {
            screen = FirstAccessScreens.allCases[currentIndex - 1]
        }
    }

    func navigateForward() {
        withAnimation {
            screen = FirstAccessScreens.allCases[currentIndex + 1]
        }
    }

    var navigationButtons: some View {
        HStack {
            if !isFirst {
                Button(FirstAccessSR.back.text) {
                    navigateBack()
                }
                .buttonStyle(SecondaryButton())
            }
            Spacer()
            if isLast {
                Button(FirstAccessSR.confirm.text) {
                    viewModel.send(.completeFirstAccessFlow)
                }
                .buttonStyle(PrimaryButton())
            } else {
                Button(FirstAccessSR.next.text) {
                    navigateForward()
                }
                .buttonStyle(PrimaryButton())
            }
        }
        .safeAreaPadding(.horizontal)
    }
}

struct FirstAccessScreen_Preview: PreviewProvider {
    static var previews: some View {
        FirstAccessScreen().environmentObject(ThemeManager())
    }
}
