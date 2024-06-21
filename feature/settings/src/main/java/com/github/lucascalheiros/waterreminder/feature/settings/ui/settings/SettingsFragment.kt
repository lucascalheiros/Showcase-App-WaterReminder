package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BaseFragment
import com.github.lucascalheiros.waterreminder.feature.settings.databinding.FragmentSettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingsFragment : BaseFragment<SettingsPresenter, SettingsContract.View>(),
    SettingsContract.View {

    override val presenter: SettingsPresenter by viewModel()

    override val viewContract: SettingsContract.View = this

    private var binding: FragmentSettingsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSettingsBinding.inflate(inflater, container, false).apply {
        binding = this
        setupUI()
    }.root

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun FragmentSettingsBinding.setupUI() {
        setupContentInsets()
        setupTitleVisibilityWatcher()
    }

    private fun FragmentSettingsBinding.setupTitleVisibilityWatcher() {
        val scrollShowPoint =
            resources.getDimension(com.github.lucascalheiros.waterreminder.common.ui.R.dimen.floating_title_scroll_show_point)
        rootScroll.setOnScrollChangeListener { _, _, y, _, _ ->
            val showFloatingTitle = y > scrollShowPoint
            if (showFloatingTitle) {
                floatingTitleView.show()
            } else {
                floatingTitleView.hide()
            }
        }
    }

    private fun FragmentSettingsBinding.setupContentInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(rootScroll) { v, windowInsets ->
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
