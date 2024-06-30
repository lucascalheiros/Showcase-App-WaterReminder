package com.github.lucascalheiros.waterreminder.feature.home.ui.adddrink

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BaseBottomSheetDialogFragment
import com.github.lucascalheiros.waterreminder.common.ui.getThemeAwareColor
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ThemeAwareColor
import com.github.lucascalheiros.waterreminder.feature.home.R
import com.github.lucascalheiros.waterreminder.feature.home.databinding.FragmentAddDrinkBottomSheetBinding
import com.github.lucascalheiros.waterreminder.feature.home.ui.adddrink.dialogs.createHydrationInputDialog
import com.github.lucascalheiros.waterreminder.feature.home.ui.adddrink.dialogs.createNameInputDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddDrinkBottomSheetFragment :
    BaseBottomSheetDialogFragment<AddDrinkPresenter, AddDrinkContract.View>(),
    AddDrinkContract.View {

    override val presenter: AddDrinkPresenter by viewModel()

    override val viewContract: AddDrinkContract.View = this

    private var binding: FragmentAddDrinkBottomSheetBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.initialize()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAddDrinkBottomSheetBinding.inflate(inflater, container, false).apply {
        binding = this
        setupUI()
    }.root

    private fun FragmentAddDrinkBottomSheetBinding.setupUI() {
        setupUIListeners()
    }

    private fun FragmentAddDrinkBottomSheetBinding.setupUIListeners() {
        llSettingsContainer.clipToOutline = true
        btnCancel.setOnClickListener {
            presenter.onCancelClick()
        }
        btnConfirm.setOnClickListener {
            presenter.onConfirmClick()
        }
        llOptionName.setOnClickListener {
            context?.createNameInputDialog {
                presenter.onNameChange(it)
            }?.show()
        }
        llOptionHydrationFactor.setOnClickListener {
            presenter.onHydrationClick()
        }
    }

    override fun dismissBottomSheet() {
        dismiss()
    }

    override fun setName(value: String) {
        binding?.tvValueName?.text = value
    }

    override fun setHydration(value: Float) {
        binding?.tvValueHydrationFactor?.text = getString(R.string.hydration_formatted, value)
    }

    override fun setColor(themeAwareColor: ThemeAwareColor) {
        with(binding ?: return) {
            val color = requireContext().getThemeAwareColor(themeAwareColor.onLightColor, themeAwareColor.onDarkColor)
            tvValueName.setTextColor(color)
            tvValueHydrationFactor.setTextColor(color)
            tvValueColor.setTextColor(color)
        }
    }

    override fun showHydrationFactorDialog(value: Float) {
        context?.createHydrationInputDialog(value) {
            presenter.onHydrationChange(it)
        }
    }

    companion object {
        fun newInstance(): AddDrinkBottomSheetFragment {
            return AddDrinkBottomSheetFragment()
        }
    }
}
