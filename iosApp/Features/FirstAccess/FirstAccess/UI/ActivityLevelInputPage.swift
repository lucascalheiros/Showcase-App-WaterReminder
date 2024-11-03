//
//  ActivityLevelInputScreen.swift
//  FirstAccess
//
//  Created by Lucas Calheiros on 10/09/24.
//

import SwiftUI
import DesignSystem

struct ActivityLevelInputPage: View {
    @EnvironmentObject var theme: ThemeManager
    @State var activityLevel = ActivityLevel.light

    var body: some View {
        ZStack {
            VStack(alignment: .center) {
                Text("Adjusting your water intake based on your activity level helps to maintain proper hydration.")
                ForEach(ActivityLevel.allCases) { item in
                    SelectableCard(
                        title: item.title,
                        description: item.description,
                        isSelected: activityLevel == item
                    ).onTapGesture {
                        withAnimation {
                            activityLevel = item
                        }
                    }
                }
            }
            .padding()
        }
        .safeAreaPadding(.horizontal)
        .multilineTextAlignment(.center)
        .frame(width: UIScreen.screenWidth)
    }

}

struct ActivityLevelInputScreen_Preview: PreviewProvider {
    static var previews: some View {
        ActivityLevelInputPage().environmentObject(ThemeManager())
    }
}

enum ActivityLevel: CaseIterable, Identifiable {
    case none
    case light
    case moderate
    case heavy

    var id: String {
        title
    }

    var title: String {
        switch self {

        case .none:
            "Sedentary"
        case .light:
            "Lightly Active"
        case .moderate:
            "Moderately Active"
        case .heavy:
            "Heavily Active"
        }
    }

    var description: String {
        switch self {

        case .none:
            "Less than 30 minutes of intentional physical activity per week."
        case .light:
            "Less than 30 minutes of light activity per day."
        case .moderate:
            "Approximately 30 to 60 minutes per day of moderate-intensity activities."
        case .heavy:
            "At least 20 to 30 minutes per day of vigorous-intensity activities."
        }
    }
}
