//
//  ActivityLevel.swift
//  iosApp
//
//  Created by Lucas Calheiros on 02/11/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared

public enum ActivityLevel: CaseIterable, Identifiable {
    case none
    case light
    case moderate
    case heavy

    public var id: String {
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

    static func from(_ level: Shared.ActivityLevel) -> ActivityLevel {
        switch level {
        case .sedentary: return .none
        case .light: return .light
        case .moderate: return .moderate
        case .heavy: return .heavy
        default: return .light
        }
    }

    var activityLevel: Shared.ActivityLevel {
        switch self {
        case .none: return .sedentary
        case .light: return .light
        case .moderate: return .moderate
        case .heavy: return .heavy
        }
    }
}
