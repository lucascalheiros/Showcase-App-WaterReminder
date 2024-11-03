//
//  WeightInputScreen.swift
//  FirstAccess
//
//  Created by Lucas Calheiros on 10/09/24.
//

import SwiftUI
import DesignSystem
import Shared
import KMPShared

struct WeightInputPage: View {
    @EnvironmentObject var theme: ThemeManager
    @State var index: Int? = 70
    @State var weightOptions: WeightOptions = WeightOptions.kg


    var body: some View {
        VStack(alignment: .center) {
            Text("Your body weight is key to determining your daily water needs.")
                .safeAreaPadding(.horizontal)
            HStack {
                Spacer()
                    .layoutPriority(1)
                Text(String(index ?? 0))
                    .layoutPriority(2)
                Text(weightOptions.formattedUnit)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .layoutPriority(1)
            }
            ZStack(alignment: .center) {
                weightRulePicker
                Line()
                    .size(width: 5, height: 50)
                    .stroke(theme.current.primaryColor, lineWidth: 4)
                    .frame(width: 5, alignment: .center)
            }
            .frame(height: 50)
            Picker("", selection: $weightOptions) {
                Text(.weightKg).tag(WeightOptions.kg)
                Text(.weightLbs).tag(WeightOptions.lbs)
            }
            .pickerStyle(.segmented)
            .padding()
            .onChange(of: weightOptions) { old, new in
                let value = Double(index ?? 0)
                let weightValue = MeasureSystemWeightCompanion()
                    .create(intrinsicValue: value, measureSystemUnit_: old.unit)
                    .toUnit(unit___: new.unit)
                withAnimation {
                    index = Int(weightValue.intrinsicValue())
                }
            }
        }
        .multilineTextAlignment(.center)
        .frame(width: UIScreen.screenWidth)
    }

    var weightRulePicker: some View {
        ScrollViewReader { value in
            ScrollView(.horizontal) {
                LazyHStack {
                    ForEach(0..<501) { index in
                        if index % 10 == 0 {
                            Line()
                                .size(width: 1, height: 40)
                                .stroke(theme.current.onBackgroundColor, lineWidth: 3)
                                .id(index)
                        } else if index % 5 == 0 {
                            Line()
                                .size(width: 1, height: 30)
                                .stroke(theme.current.onBackgroundColor, lineWidth: 2)
                                .id(index)
                        } else {
                            Line()
                                .size(width: 1, height: 16)
                                .stroke(theme.current.onBackgroundColor, lineWidth: 1)
                                .id(index)
                        }
                    }
                }
                .padding(EdgeInsets(
                    top: 0,
                    leading: UIScreen.screenWidth / 2,
                    bottom: 0,
                    trailing: UIScreen.screenWidth / 2
                ))
                .bold()
                .scrollTargetLayout()
            }
            .scrollPosition(id: $index, anchor: .center)
            .scrollTargetBehavior(.viewAligned)
            .scrollIndicators(.never)
        }
    }
}

struct WeightInputScreen_Preview: PreviewProvider {
    static var previews: some View {
        WeightInputPage().environmentObject(ThemeManager())
    }
}
