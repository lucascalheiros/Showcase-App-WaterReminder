package com.github.lucascalheiros.waterremindermvp.feature.home.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.github.lucascalheiros.waterremindermvp.common.appcore.mvp.BaseFragment
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.DailyWaterConsumptionSummary
import com.github.lucascalheiros.waterremindermvp.feature.home.R
import com.github.lucascalheiros.waterremindermvp.feature.home.databinding.FragmentHomeBinding
import com.github.lucascalheiros.waterremindermvp.feature.home.ui.addwatersource.AddWaterSourceBottomSheetFragment
import com.github.lucascalheiros.waterremindermvp.feature.home.ui.home.adapters.WaterSourceCard
import com.github.lucascalheiros.waterremindermvp.feature.home.ui.home.adapters.WaterSourceCardAdapter
import com.github.lucascalheiros.waterremindermvp.feature.home.ui.home.adapters.itemdecorations.GridSpaceBetweenItemDecoration
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
    ): View = FragmentHomeBinding.inflate(inflater, container, false).apply {
        binding = this
        setupUI()
    }.root

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun setTodayConsumptionSummary(summary: DailyWaterConsumptionSummary) {
        binding?.summarySection?.setDailyWaterConsumptionSummary(summary, true)
    }

    override fun setWaterSourceList(waterSource: List<WaterSourceCard>) {
        waterSourceCardAdapter.submitList(waterSource)
    }

    override fun showAddWaterSourceBottomSheet() {
        AddWaterSourceBottomSheetFragment.newInstance().show(childFragmentManager, null)
    }

    private fun FragmentHomeBinding.setupUI() {
        setupContentInsets()
        setupWaterSourceCards()
    }

    private fun FragmentHomeBinding.setupContentInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(contentRoot) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updateLayoutParams<FrameLayout.LayoutParams> {
                leftMargin = insets.left
                topMargin = insets.top
                rightMargin = insets.right
            }
            windowInsets
        }
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