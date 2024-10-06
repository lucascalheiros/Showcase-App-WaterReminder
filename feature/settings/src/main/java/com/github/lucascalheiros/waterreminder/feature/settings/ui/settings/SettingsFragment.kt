package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }.root

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
