package com.github.lucascalheiros.waterremindermvp.feature.settings.ui.managenotifications

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.github.lucascalheiros.waterremindermvp.common.appcore.mvp.BaseFragment
import com.github.lucascalheiros.waterremindermvp.feature.settings.databinding.FragmentManageNotificationsBinding
import com.github.lucascalheiros.waterremindermvp.feature.settings.databinding.FragmentSettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManageNotificationsFragment :
    BaseFragment<ManageNotificationsPresenter, ManageNotificationsContract.View>(),
    ManageNotificationsContract.View {

    override val presenter: ManageNotificationsPresenter by viewModel()

    override val viewContract: ManageNotificationsContract.View = this

    private var binding: FragmentManageNotificationsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentManageNotificationsBinding.inflate(inflater, container, false).apply {
        binding = this
        setupListeners()
        setupContentInsets()
    }.root

    private fun FragmentManageNotificationsBinding.setupListeners() {
        ibBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun FragmentManageNotificationsBinding.setupContentInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updateLayoutParams<FrameLayout.LayoutParams> {
                topMargin = insets.top
            }
            windowInsets
        }
    }

}