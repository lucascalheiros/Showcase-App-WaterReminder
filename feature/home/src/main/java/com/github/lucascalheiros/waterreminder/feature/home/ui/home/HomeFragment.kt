package com.github.lucascalheiros.waterreminder.feature.home.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BaseFragment
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumptionSummary
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSource
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.feature.home.R
import com.github.lucascalheiros.waterreminder.feature.home.databinding.FragmentHomeBinding
import com.github.lucascalheiros.waterreminder.feature.home.ui.adddrink.AddDrinkBottomSheetFragment
import com.github.lucascalheiros.waterreminder.feature.home.ui.drinkshortcut.DrinkShortcutBottomSheetFragment
import com.github.lucascalheiros.waterreminder.feature.home.ui.addwatersource.AddWaterSourceBottomSheetFragment
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.drinkchips.DrinkChipsAdapter
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.drinkchips.DrinkChipsListener
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.drinkchips.DrinkItems
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.itemdecorations.GridSpaceBetweenItemDecoration
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.snaphelper.StartSnapHelper
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.watersource.WaterSourceCard
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.watersource.WaterSourceCardAdapter
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.watersource.WaterSourceCardsListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomePresenter, HomeContract.View>(), HomeContract.View {

    override val presenter: HomePresenter by viewModel()

    override val viewContract: HomeContract.View = this

    private var binding: FragmentHomeBinding? = null

    private val waterSourceCardAdapter by lazy {
        WaterSourceCardAdapter().apply {
            listener = object : WaterSourceCardsListener {
                override fun onWaterSourceClick(waterSource: WaterSource) =
                    presenter.onWaterSourceClick(waterSource)

                override fun onAddWaterSourceClick() =
                    presenter.onAddWaterSourceClick()

                override fun onDeleteWaterSourceCard(waterSource: WaterSource) =
                    presenter.onDeleteWaterSourceClick(waterSource)

            }
        }
    }

    private val drinkChipsAdapter by lazy {
        DrinkChipsAdapter().apply {
            listener = object : DrinkChipsListener {
                override fun onDrinkClick(type: WaterSourceType) {
                    presenter.onDrinkClick(type)
                }

                override fun onAddDrinkClick() {
                    presenter.onAddDrinkClick()
                }

                override fun onDeleteDrink(type: WaterSourceType) {
                    presenter.onDeleteDrink(type)
                }
            }
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
        val firstVisiblePosition = (binding?.rvWaterSourceCards?.layoutManager as? GridLayoutManager)?.findFirstVisibleItemPosition()

        waterSourceCardAdapter.submitList(waterSource) {
            firstVisiblePosition?.let {
                (binding?.rvWaterSourceCards?.layoutManager as? GridLayoutManager)?.scrollToPosition(firstVisiblePosition)
            }
        }
    }

    override fun setDrinkList(drinks: List<DrinkItems>) {
        val firstVisiblePosition = (binding?.rvWaterSourceCards?.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()

        drinkChipsAdapter.submitList(drinks) {
            firstVisiblePosition?.let {
                (binding?.rvWaterSourceCards?.layoutManager as? GridLayoutManager)?.scrollToPosition(firstVisiblePosition)
            }
        }
    }

    override fun sendUIEvent(event: HomeContract.ViewUIEvents) {
        when (event) {
            is HomeContract.ViewUIEvents.OpenDrinkShortcut -> {
                DrinkShortcutBottomSheetFragment.newInstance(
                    event.type.waterSourceTypeId,
                    event.type.name,
                    event.unit
                ).show(childFragmentManager, null)
            }

            HomeContract.ViewUIEvents.OpenAddWaterSource -> {
                AddWaterSourceBottomSheetFragment.newInstance().show(childFragmentManager, null)
            }

            HomeContract.ViewUIEvents.OpenAddDrink -> {
                AddDrinkBottomSheetFragment.newInstance().show(childFragmentManager, null)
            }
        }
    }

    private fun FragmentHomeBinding.setupUI() {
        setupContentInsets()
        setupWaterSourceCards()
        setupDrinkChips()
    }

    private fun FragmentHomeBinding.setupContentInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(llRoot) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updateLayoutParams<FrameLayout.LayoutParams> {
                topMargin = insets.top
            }
            windowInsets
        }
    }

    private fun FragmentHomeBinding.setupWaterSourceCards() {
        val snapHelper = StartSnapHelper()
        snapHelper.attachToRecyclerView(rvWaterSourceCards)
        rvWaterSourceCards.adapter = waterSourceCardAdapter
        rvWaterSourceCards.addItemDecoration(
            GridSpaceBetweenItemDecoration(
                resources.getDimension(R.dimen.water_source_card_horizontal_space),
                resources.getDimension(R.dimen.water_source_card_vertical_space)
            )
        )
    }

    private fun FragmentHomeBinding.setupDrinkChips() {
        rvDrinkChips.adapter = drinkChipsAdapter
        rvDrinkChips.addItemDecoration(
            GridSpaceBetweenItemDecoration(
                resources.getDimension(R.dimen.drink_chips_horizontal_space),
                0f
            )
        )
    }
}
