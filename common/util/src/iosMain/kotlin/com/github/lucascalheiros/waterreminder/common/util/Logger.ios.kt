package com.github.lucascalheiros.waterreminder.common.util

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

actual fun setupLogs() {
    Napier.base(DebugAntilog())
}