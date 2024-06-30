package com.github.lucascalheiros.waterreminder.feature.home.ui.adddrink

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ThemeAwareColor

interface AddDrinkContract {
    interface View {
        fun dismissBottomSheet()
        fun setName(value: String)
        fun setHydration(value: Float)
        fun setColor(themeAwareColor: ThemeAwareColor)
        fun showHydrationFactorDialog(value: Float)
        fun showColorSelectorDialog(themeAwareColor: ThemeAwareColor)
        fun setConfirmState(isEnabled: Boolean)
    }

    interface Presenter {
        fun initialize()
        fun onCancelClick()
        fun onConfirmClick()
        fun onNameChange(value: String)
        fun onHydrationClick()
        fun onHydrationChange(value: Float)
        fun onColorClick()
        fun onColorChange(value: ThemeAwareColor)
    }

}

