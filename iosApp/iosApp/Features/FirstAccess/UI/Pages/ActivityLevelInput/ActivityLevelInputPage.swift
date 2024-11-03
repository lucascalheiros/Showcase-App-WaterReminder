//
//  ActivityLevelInputScreen.swift
//  FirstAccess
//
//  Created by Lucas Calheiros on 10/09/24.
//

import SwiftUI
import Shared

struct ActivityLevelInputPage: View {

    @EnvironmentObject var theme: ThemeManager

    @StateObject var viewModel: ActivityLevelInputViewModel = ActivityLevelInputViewModel()

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
                        viewModel.send(.setActivityLevel(item))
                    }
                }
                .onChange(of: viewModel.state.activityLevel) { _, new in
                    withAnimation {
                        activityLevel = new
                    }
                }
                .onAppear {
                    viewModel.send(.loadData)
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
