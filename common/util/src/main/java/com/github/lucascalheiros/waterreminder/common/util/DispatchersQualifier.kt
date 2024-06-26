package com.github.lucascalheiros.waterreminder.common.util

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue

enum class DispatchersQualifier(override val value: QualifierValue): Qualifier {
    Main("MainDispatcher"),
    Io("IoDispatcher"),
    Default("DefaultDispatcher")
}