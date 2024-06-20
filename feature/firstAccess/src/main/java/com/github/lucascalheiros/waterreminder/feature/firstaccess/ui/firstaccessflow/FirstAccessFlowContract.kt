package com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.firstaccessflow

interface FirstAccessFlowContract {
    interface View {
        fun navigateToMainFlow()
    }

    interface Presenter {
        fun onConfirmFirstAccessFlow()

    }
}