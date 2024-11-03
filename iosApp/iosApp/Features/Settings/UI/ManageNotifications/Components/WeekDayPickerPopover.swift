//
//  WeekDayPickerPopover.swift
//  iosApp
//
//  Created by Lucas Calheiros on 27/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct WeekDayPickerPopover: View {
    let weekDayPopover: Namespace.ID
    @EnvironmentObject var theme: ThemeManager
    @State private var _weekState: WeekDayState?
    var popoverData: WeekDayPopoverData?
    var sendIntent: (ManageNotificationsIntent) -> Void

    var body: some View {
        if let popoverData {
            Rectangle()
                .foregroundColor(.clear)
                .contentShape(Rectangle())
                .onTapGesture {
                    if let _weekState, _weekState != popoverData.weekDayState {
                        sendIntent(.setWeekState(
                            popoverData,
                            _weekState.weekState))
                    }
                    sendIntent(.dismissWeekDayPicker)
                }
            WeekDayPicker(weekDayState: $_weekState)
                .padding()
                .background {
                    RoundedRectangle(cornerRadius: 10)
                        .fill(theme.current.surfaceColor)
                        .shadow(radius: 6)
                }
                .matchedGeometryEffect(
                    id: popoverData.dayTimeInMinutes,
                    in: weekDayPopover,
                    properties: .position,
                    anchor: .trailing,
                    isSource: false
                )
                .onAppear {
                    _weekState = popoverData.weekDayState
                }
                .transition(
                    .opacity.combined(with: .scale)
                    .animation(.bouncy(duration: 0.25, extraBounce: 0.2))
                )
        }
    }
}
