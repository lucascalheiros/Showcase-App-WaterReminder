//
//  HistorySR.swift
//  History
//
//  Created by Lucas Calheiros on 09/09/24.
//

import SwiftUI

enum HistorySR: String, StringResourcesProtocol {

    case historyTitle
    case chartPeriod
    case chartOptionYear
    case chartOptionMonth
    case chartOptionWeek
    case chartXDate
    case chartYVolume
    case chartYExpectedVolume
    case dayHeaderToday
    case dayHeaderYesterday
    case deleteConsumedWater

    var key: String {
        rawValue
    }

    var table: String {
        "History"
    }
}


extension Text {
    init(_ resource: HistorySR) {
        self.init(resource.text)
    }
}
