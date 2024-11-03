//
//  DailyWaterConsumptionSummary.swift
//  iosApp
//
//  Created by Lucas Calheiros on 10/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared

extension DailyWaterConsumptionSummary: Identifiable {
    public var id: Int32 {
        date.toEpochDays()
    }
}
