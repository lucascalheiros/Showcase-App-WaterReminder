package com.github.lucascalheiros.waterreminder.feature.history.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BaseFragment
import com.github.lucascalheiros.waterreminder.feature.history.databinding.FragmentHistoryBinding
import com.github.lucascalheiros.waterreminder.feature.history.ui.history.adapters.historysections.HistorySectionsAdapter
import com.github.lucascalheiros.waterreminder.feature.history.ui.history.models.HistorySections
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : BaseFragment<HistoryPresenter, HistoryContract.View>(), HistoryContract.View {

    override val presenter: HistoryPresenter by viewModel()

    override val viewContract: HistoryContract.View = this

    private val historySectionsAdapter = HistorySectionsAdapter().apply {
        onDeleteConsumedWaterClick = {
            presenter.onDeleteAction(it)
        }
        onOptionClick = {
            presenter.onSelectChartOption(it)
        }
        onNextClick = {
            presenter.onNextChartPeriod()
        }
        onPrevClick = {
            presenter.onPreviousChartPeriod()
        }
    }

    private var binding: FragmentHistoryBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHistoryBinding.inflate(inflater, container, false).apply {
        binding = this
        setupUI()
    }.root

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun updateHistorySections(sectionsData: List<HistorySections>) {
        historySectionsAdapter.submitList(sectionsData)
    }

    private fun FragmentHistoryBinding.setupUI() {
        setupHistorySections()
    }

    private fun FragmentHistoryBinding.setupHistorySections() {
        rvSections.adapter = historySectionsAdapter
    }
}
