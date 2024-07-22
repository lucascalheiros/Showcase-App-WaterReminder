package com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.weightinput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.lucascalheiros.waterreminder.common.appcore.format.shortUnitFormatted
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BaseFragment
import com.github.lucascalheiros.waterreminder.common.ui.pickers.horizontalrule.HorizontalRuleView
import com.github.lucascalheiros.waterreminder.feature.firstaccess.databinding.FragmentWeightInputBinding
import com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.introducing.delayedFadeIn
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeightUnit
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt


class WeightInputFragment : BaseFragment<WeightInputPresenter, WeightInputContract.View>(),
    WeightInputContract.View {

    override val presenter: WeightInputPresenter by viewModel()

    override val viewContract: WeightInputContract.View = this

    private var binding: FragmentWeightInputBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentWeightInputBinding.inflate(inflater, container, false).apply {
        binding = this
        root.alpha = 0f
        clContent.alpha = 0f
        setupListeners()
    }.root

    override fun onResume() {
        super.onResume()
        binding?.root?.delayedFadeIn()
        binding?.clContent?.delayedFadeIn(1_000)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun setIntrinsicWeight(weight: Double) {
        val weightInt = weight.roundToInt()
        binding?.horizontalRuleView?.setSelectedValue(weightInt, false)
    }

    override fun setWeightUnit(weightUnit: MeasureSystemWeightUnit) {
        binding?.tvUnit?.text = weightUnit.shortUnitFormatted(requireContext())
    }

    private fun FragmentWeightInputBinding.setupListeners() {
        horizontalRuleView.listener = object : HorizontalRuleView.OnHorizontalRuleChangeListener {
            override fun onValueChanging(value: Int) {
                binding?.tvWeightValue?.text = value.toString()
            }
            override fun onValueStopChanging(value: Int) {
                presenter.setIntrinsicWeight(value.toDouble())
            }
        }
        btnKg.setOnClickListener {
            presenter.setWeightUnit(MeasureSystemWeightUnit.KILOGRAMS)
        }
        btnLbs.setOnClickListener {
            presenter.setWeightUnit(MeasureSystemWeightUnit.POUNDS)
        }
    }

}
