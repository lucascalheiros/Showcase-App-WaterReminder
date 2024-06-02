package com.github.lucascalheiros.waterreminder.feature.history.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BaseFragment
import com.github.lucascalheiros.waterreminder.feature.history.databinding.FragmentHistoryBinding
import com.github.lucascalheiros.waterreminder.feature.history.ui.history.adapters.historysections.HistorySections
import com.github.lucascalheiros.waterreminder.feature.history.ui.history.adapters.historysections.HistorySectionsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : BaseFragment<HistoryPresenter, HistoryContract.View>(), HistoryContract.View {

    override val presenter: HistoryPresenter by viewModel()

    override val viewContract: HistoryContract.View = this

    private val historySectionsAdapter = HistorySectionsAdapter().apply {
        onDeleteConsumedWaterClick = {
            presenter.onDeleteAction(it)
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
        setupContentInsets()
        setupHistorySections()
        setupTitleVisibilityWatcher()
    }

    private fun FragmentHistoryBinding.setupHistorySections() {
        rvSections.adapter = historySectionsAdapter
    }

    private fun FragmentHistoryBinding.setupTitleVisibilityWatcher() {
        rvSections.setOnScrollChangeListener { _, _, _, _, _ ->
            val firstVisibleItem = (rvSections.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition() ?: return@setOnScrollChangeListener
            val showFloatingTitle = firstVisibleItem != 0
            if (showFloatingTitle) {
                floatingTitleView.show()
            } else {
                floatingTitleView.hide()
            }
        }
    }

    private fun FragmentHistoryBinding.setupContentInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(rvSections) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updateLayoutParams<FrameLayout.LayoutParams> {
                topMargin = insets.top
            }
            floatingTitleView.updateLayoutParams<FrameLayout.LayoutParams> {
                topMargin = insets.top
            }
            windowInsets
        }
    }
}