package com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.firstaccessflow

interface FirstAccessFlowContract {
    interface View {
        fun requestPermissions()
        fun navigateToMainFlow()
        fun showConfirmationFailureToast()
    }

    interface Presenter {
        fun onPermissionsHandled()
        fun onConfirmFirstAccessFlow()
    }
}