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
import androidx.recyclerview.widget.ItemTouchHelper
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BaseFragment
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumptionSummary
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSource
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.feature.home.R
import com.github.lucascalheiros.waterreminder.feature.home.databinding.FragmentHomeBinding
import com.github.lucascalheiros.waterreminder.feature.home.ui.adddrink.AddDrinkBottomSheetFragment
import com.github.lucascalheiros.waterreminder.feature.home.ui.addwatersource.AddWaterSourceBottomSheetFragment
import com.github.lucascalheiros.waterreminder.feature.home.ui.drinkshortcut.DrinkShortcutBottomSheetFragment
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.drinkchips.DrinkChipsAdapter
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.drinkchips.DrinkChipsListener
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.drinkchips.DrinkItems
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.itemdecorations.GridSpaceBetweenItemDecoration
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.itemtouchhelper.SortingItemTouchHelperCallback
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

                override fun onMoveToPosition(waterSource: WaterSource, position: Int) {
                    fixSnapAfterDrag()
                    presenter.onMoveWaterSourceToPosition(waterSource, position)
                }
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

                override fun onMoveToPosition(type: WaterSourceType, position: Int) {
                    presenter.onMoveDrinkToPosition(type, position)
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
        val layout =
            (binding?.rvWaterSourceCards?.layoutManager as? GridLayoutManager)?.onSaveInstanceState()
        waterSourceCardAdapter.submitList(waterSource) {
            (binding?.rvWaterSourceCards?.layoutManager as? GridLayoutManager)?.onRestoreInstanceState(
                layout
            )
        }
    }

    override fun setDrinkList(drinks: List<DrinkItems>) {
        drinkChipsAdapter.submitList(drinks)
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
        val callback: ItemTouchHelper.Callback =
            SortingItemTouchHelperCallback(waterSourceCardAdapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(rvWaterSourceCards)
        rvWaterSourceCards.adapter = waterSourceCardAdapter
        rvWaterSourceCards.addItemDecoration(
            GridSpaceBetweenItemDecoration(
                resources.getDimension(R.dimen.water_source_card_horizontal_space),
                resources.getDimension(R.dimen.water_source_card_vertical_space)
            )
        )
    }

    private fun FragmentHomeBinding.setupDrinkChips() {
        val callback: ItemTouchHelper.Callback =
            SortingItemTouchHelperCallback(drinkChipsAdapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(rvDrinkChips)
        rvDrinkChips.adapter = drinkChipsAdapter
        rvDrinkChips.addItemDecoration(
            GridSpaceBetweenItemDecoration(
                resources.getDimension(R.dimen.drink_chips_horizontal_space),
                0f
            )
        )
    }

    /**
     * After drag the snap may not be activated so a small smooth scroll will trigger it
     */
    private fun fixSnapAfterDrag() {
        with(binding?.rvWaterSourceCards ?: return) {
            (layoutManager as? GridLayoutManager)?.run {
                smoothScrollBy(1, 0)
            }
        }
    }
}
