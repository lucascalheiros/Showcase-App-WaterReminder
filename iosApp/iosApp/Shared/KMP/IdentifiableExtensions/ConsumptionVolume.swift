//
//  ConsumptionVolume.swift
//  iosApp
//
//  Created by Lucas Calheiros on 10/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared

extension ConsumptionVolume: Identifiable {
    public var id: String {
        if let volumeFromDay = self as? ConsumptionVolume.FromDay {
            return "\(volumeFromDay.date.year)-\(volumeFromDay.date.month)-\(volumeFromDay.date.dayOfMonth)"
        }
        if let volumeFromMonth = self as? ConsumptionVolume.FromMonth {
            return "\(volumeFromMonth.yearAndMonth.year)-\(volumeFromMonth.yearAndMonth.month)"
        }
        return ""
    }
}
