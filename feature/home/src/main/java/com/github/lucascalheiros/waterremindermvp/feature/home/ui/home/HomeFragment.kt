package com.github.lucascalheiros.waterremindermvp.feature.home.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.lucascalheiros.waterremindermvp.common.appcore.mvp.BaseFragment
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.DailyWaterConsumptionSummary
import com.github.lucascalheiros.waterremindermvp.feature.home.R
import com.github.lucascalheiros.waterremindermvp.feature.home.databinding.FragmentHomeBinding
import com.github.lucascalheiros.waterremindermvp.feature.home.ui.shared.adapters.itemdecorations.GridSpaceBetweenItemDecoration
import com.github.lucascalheiros.waterremindermvp.feature.home.ui.shared.adapters.WaterSourceCard
import com.github.lucascalheiros.waterremindermvp.feature.home.ui.shared.adapters.WaterSourceCardAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomePresenter, HomeContract.View>(), HomeContract.View {

    override val presenter: HomePresenter by viewModel()

    override val viewContract: HomeContract.View = this

    private var binding: FragmentHomeBinding? = null

    private val waterSourceCardAdapter by lazy {
        WaterSourceCardAdapter().apply {
            listener = presenter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeBinding.inflate(inflater, container, false).also {
        binding = it
        it.setupWaterSourceCards()
    }.root

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun showTodayConsumptionSummary(summary: DailyWaterConsumptionSummary) {
        binding?.summarySection?.setDailyWaterConsumptionSummary(summary, true)
    }

    override fun showWaterSourceList(waterSource: List<WaterSourceCard>) {
        waterSourceCardAdapter.submitList(waterSource)
    }

    override fun showAddWaterSourceBottomSheet() {
    }

    private fun FragmentHomeBinding.setupWaterSourceCards() {
        rvWaterSourceCards.adapter = waterSourceCardAdapter
        rvWaterSourceCards.addItemDecoration(
            GridSpaceBetweenItemDecoration(
                2,
                resources.getDimension(R.dimen.water_source_card_horizontal_space),
                resources.getDimension(R.dimen.water_source_card_vertical_space)
            )
        )
    }

}