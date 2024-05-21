package com.github.lucascalheiros.waterremindermvp.feature.home.ui.addwatersource

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.lucascalheiros.waterremindermvp.common.appcore.format.shortValueAndUnitFormatted
import com.github.lucascalheiros.waterremindermvp.common.appcore.mvp.BaseBottomSheetDialogFragment
import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolume
import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterremindermvp.common.ui.getThemeAwareColor
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterremindermvp.feature.home.R
import com.github.lucascalheiros.waterremindermvp.feature.home.databinding.FragmentAddWaterSourceBottomSheetBinding
import com.github.lucascalheiros.waterremindermvp.feature.home.ui.addwatersource.dialogs.createSelectWaterSourceDialog
import com.github.lucascalheiros.waterremindermvp.feature.home.ui.addwatersource.dialogs.createVolumeInputDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddWaterSourceBottomSheetFragment :
    BaseBottomSheetDialogFragment<AddWaterSourcePresenter, AddWaterSourceContract.View>(),
    AddWaterSourceContract.View {

    override val presenter: AddWaterSourcePresenter by viewModel()
    override val viewContract: AddWaterSourceContract.View = this

    private var binding: FragmentAddWaterSourceBottomSheetBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAddWaterSourceBottomSheetBinding.inflate(inflater, container, false).apply {
        binding = this
        setupUI()

    }.root

    private fun FragmentAddWaterSourceBottomSheetBinding.setupUI() {
        llOptionType.setOnClickListener {
            presenter.onSelectWaterSourceTypeOptionClick()
        }

        llOptionVolume.setOnClickListener {
            presenter.onVolumeOptionClick()
        }

        btnCancel.setOnClickListener {
            presenter.onCancelClick()
        }

        btnConfirm.setOnClickListener {
            presenter.onConfirmClick()
        }
    }

    override fun showSelectWaterSourceDialog(waterSourceTypeList: List<WaterSourceType>) {
        context?.createSelectWaterSourceDialog(waterSourceTypeList) {
            presenter.onWaterSourceTypeSelected(it)
        }?.show()
    }

    override fun showVolumeInputDialog(unit: MeasureSystemVolumeUnit) {
        context?.createVolumeInputDialog(unit) {
            presenter.onVolumeSelected(it)
        }?.show()
    }

    override fun setSelectedVolume(volume: MeasureSystemVolume) {
        with(binding ?: return) {
            tvValueVolume.text = volume.shortValueAndUnitFormatted(requireContext())
        }
    }

    override fun setSelectedWaterSourceType(waterSourceType: WaterSourceType) {
        val color = waterSourceType.run {
            requireContext().getThemeAwareColor(
                lightColor,
                darkColor
            )
        }.toInt()
        with(binding ?: return) {
            tvValueType.text = waterSourceType.name
            tvValueVolume.setTextColor(color)
            tvValueType.setTextColor(color)
        }
    }

    override fun dismissBottomSheet() {
        dismiss()
    }

    override fun showOperationErrorToast(event: AddWaterSourceContract.ErrorEvent) {
        val textRes = when (event) {
            AddWaterSourceContract.ErrorEvent.DataLoadingFailed -> R.string.data_load_failure
            AddWaterSourceContract.ErrorEvent.SaveFailed -> R.string.save_failure
        }
        Toast.makeText(context, textRes, Toast.LENGTH_LONG).show()
    }

    companion object {
        fun newInstance(): AddWaterSourceBottomSheetFragment {
            return AddWaterSourceBottomSheetFragment()
        }
    }
}