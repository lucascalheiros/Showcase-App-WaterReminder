package com.github.lucascalheiros.waterreminder.feature.home.ui.adddrink

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ThemeAwareColor

interface AddDrinkContract {
    interface View {
        fun dismissBottomSheet()
        fun setName(value: String)
        fun setHydration(value: Float)
        fun setColor(themeAwareColor: ThemeAwareColor)
        fun showHydrationFactorDialog(value: Float)
    }

    interface Presenter {
        fun initialize()
        fun onCancelClick()
        fun onConfirmClick()
        fun onNameClick()
        fun onNameChange(value: String)
        fun onHydrationClick()
        fun onHydrationChange(value: Float)
        fun onColorClick()
        fun onColorChange(value: ThemeAwareColor)
    }

}

