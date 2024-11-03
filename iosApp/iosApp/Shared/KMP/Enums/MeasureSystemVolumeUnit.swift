//
//  MeasureSystemVolumeUnit.swift
//  iosApp
//
//  Created by Lucas Calheiros on 10/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared


extension MeasureSystemVolumeUnit {
    public var swiftEnum: MeasureSystemVolumeUnit_SV {
        MeasureSystemVolumeUnit_SV(self)!
    }
}

public enum MeasureSystemVolumeUnit_SV {
    case ml
    case oz_us
    case oz_uk

    public init?(_ value: MeasureSystemVolumeUnit) {
        switch value {
        case MeasureSystemVolumeUnit.ml:
            self = .ml
        case MeasureSystemVolumeUnit.ozUs:
            self = .oz_us
        case MeasureSystemVolumeUnit.ozUk:
            self = .oz_uk
        default:
            return nil
        }
    }
}
