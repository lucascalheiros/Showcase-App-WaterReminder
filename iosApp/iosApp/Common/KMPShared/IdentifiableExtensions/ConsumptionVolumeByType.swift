//
//  ConsumptionVolumeByType.swift
//  iosApp
//
//  Created by Lucas Calheiros on 10/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared

extension ConsumptionVolumeByType: Identifiable {
    public var id: String {
        return "\(waterSourceType.waterSourceTypeId)\(volume.intrinsicValue())\(volume.volumeUnit())"
    }
}

